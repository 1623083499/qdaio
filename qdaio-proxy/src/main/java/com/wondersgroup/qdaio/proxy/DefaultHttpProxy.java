package com.wondersgroup.qdaio.proxy;

import com.wondersgroup.qdaio.proxy.bo.NetProxyLog;
import com.wondersgroup.qdaio.proxy.processor.GetProcessor;
import com.wondersgroup.qdaio.proxy.processor.MethodProcessor;
import com.wondersgroup.qdaio.proxy.processor.PostProcessor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Service
public class DefaultHttpProxy {
    private static final Logger log =Logger.getLogger(DefaultHttpProxy.class);
    @Autowired
    private  ConnectionManager connectionFactory ;
    @Autowired
    private  GetProcessor getProcessor ;
    @Autowired
    private PostProcessor postProcessor ;

    private Long connectionMonitorStep;
    /**
     * 执行url请求
     */
    public boolean execute(ServletRequest request, ServletResponse response) {
        long beforeTime = System.currentTimeMillis();
        // 根据request创建请求
        HttpRequestBase httpRequest ;

        MethodProcessor processor;
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        if (servletRequest.getMethod().equals("GET")){
            processor=getProcessor;
        }else{
            processor=postProcessor;
        }

        httpRequest=processor.doRequest(request);
        if (httpRequest == null) {
            return false;
        }


        // 从连接池中获得HttpClient
        CloseableHttpClient httpClient = connectionFactory.getHttpClient();
        CloseableHttpResponse proxyRes = null;
        //记录日志
        NetProxyLog netProxyLog=new NetProxyLog();
        netProxyLog.setCtime(new Date());
        netProxyLog.setUrl(httpRequest.getURI().toString());
        netProxyLog.setLogintoken(ProxyContextUtils.getRequestProxyDTO().getLogintoken());
        netProxyLog.setParams(ProxyContextUtils.getRequestProxyDTO().getParams());

        try {
            HttpClientContext httpContext = HttpClientContext.create();
            proxyRes = httpClient.execute(httpRequest, httpContext);
            HttpServletResponse servletRes = (HttpServletResponse) response;
            processor.doResponse(servletRes, proxyRes);

            HttpEntity entity = proxyRes.getEntity();
            if (proxyRes.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                OutputStream outputStream = response.getOutputStream();
                entity.writeTo(outputStream);
                outputStream.flush();
            } else {
                servletRes.sendError(proxyRes.getStatusLine().getStatusCode());

            }

        } catch (IOException e) {
            netProxyLog.setErrormsg(e.getMessage());
            e.printStackTrace();
            /**
             * 清空过期连接
             */
            clearExpirConnection();
            return false;
        }finally {
            long usetime=System.currentTimeMillis()-beforeTime;
            netProxyLog.setUsetime(usetime);
            log.info(httpRequest.getURI()+"-----代理耗时["+usetime+"ms]-----");
            try {
                //断开连接
                httpRequest.abort();
                //记录日志
               ProxyLogUtils.add(netProxyLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }
    public void clearExpirConnection(){
        if (getConnectionMonitorStep()==null) {
            setConnectionMonitorStep(System.currentTimeMillis());
            return;
        }
        /**
         * 超过5分钟执行
         */
        if (System.currentTimeMillis()-getConnectionMonitorStep()>=5*60*1000){
            new HttpClientConnectionMonitorThread(connectionFactory.getManager()).run();
            setConnectionMonitorStep(System.currentTimeMillis());
        }

    }

    public Long getConnectionMonitorStep() {
        return connectionMonitorStep;
    }

    public void setConnectionMonitorStep(Long connectionMonitorStep) {
        this.connectionMonitorStep = connectionMonitorStep;
    }
}