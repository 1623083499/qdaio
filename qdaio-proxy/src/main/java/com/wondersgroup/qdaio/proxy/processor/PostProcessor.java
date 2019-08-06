package com.wondersgroup.qdaio.proxy.processor;

import com.wondersgroup.qdaio.proxy.ProxyContextUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class PostProcessor extends MethodProcessor {


    public PostProcessor() {
        super();
    }

    /**
     * 根据ServletRequest生成HttpRequestBase
     * @param request
     * @return
     */
    public HttpRequestBase doRequest(ServletRequest request) {
        if (request == null || !(request instanceof HttpServletRequest)) {
            return null;
        }

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        if (!servletRequest.getMethod().equals("POST")) {
            return null;
        }

        // 获得目标url
        String url = uriRoute.routeUri(servletRequest);
        // 目标路径为空，表示不路由
        if (!StringUtils.isNotBlank(url)) {
            return null;
        }
        // 创建POST请求
        HttpPost httpPost = new HttpPost(url);

        // 处理请求协议
        httpPost.setProtocolVersion(doProtocol(servletRequest));
        // 处理请求头
        httpPost.setHeaders(doHeaders(servletRequest));
        // 处理请求体
            Map<String,String[]> parameterMap=servletRequest.getParameterMap();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            StringBuffer stringBuffer=new StringBuffer();
            Set<String> set=parameterMap.keySet();
            for (String key:set){
                String value=arrayToString(parameterMap.get(key));
                params.add(new BasicNameValuePair(key, value));
                if(stringBuffer.length()==0)
                    stringBuffer.append(key+"="+value);
                    else
                stringBuffer.append("&"+key+"="+value);
            }
            //记录日志请求参数
             ProxyContextUtils.getRequestProxyDTO().setParams(stringBuffer.toString());

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));

            httpPost.setEntity(entity);


        return httpPost;
    }
}
