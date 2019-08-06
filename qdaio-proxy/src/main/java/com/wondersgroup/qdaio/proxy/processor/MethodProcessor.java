package com.wondersgroup.qdaio.proxy.processor;

import com.wondersgroup.qdaio.proxy.ConnectionPropertis;
import com.wondersgroup.qdaio.proxy.ProxyContextUtils;
import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public abstract class MethodProcessor {

    @Autowired
    protected UriRoute uriRoute;

    @Autowired
    protected ConnectionPropertis connectionPropertis;
    /**
     * 根据ServletRequest生成HttpRequestBase
     * @param request
     * @return
     */
    public abstract HttpRequestBase doRequest(ServletRequest request);

    /**
     * 处理响应
     * @param servletRes
     * @param proxyRes
     */
    public void doResponse(HttpServletResponse servletRes, CloseableHttpResponse proxyRes) {
        // 处理消息头
        Header[] headers = proxyRes.getAllHeaders();
        if (headers != null) {
            for (Header header : headers) {
                if ("content-length".equals(header.getName())||"Content-Length".equals(header.getName())) continue;
                servletRes.addHeader(header.getName(), header.getValue());
            }
        }
    }

    /**
     * 处理请求协议
     * @param request
     * @return
     */
    protected ProtocolVersion doProtocol(HttpServletRequest request) {
        String protocol = request.getProtocol();
        char[] cProtocol = protocol.toCharArray();
        // https的第4个字符是'S'
        if (cProtocol[4] == 'S') {
            String p = new String(cProtocol, 0, 5); //协议
            int major = Integer.valueOf(new String(cProtocol, 6, 1));   //major
            int minor = Integer.valueOf(new String(cProtocol, 8, 1));   //minor
            return new ProtocolVersion(p, major, minor);
        } else {
            String p = new String(cProtocol, 0, 4); //协议
            int major = Integer.valueOf(new String(cProtocol, 5, 1));   //major
            int minor = Integer.valueOf(new String(cProtocol, 7, 1));   //minor
            return new ProtocolVersion(p, major, minor);
        }
    }

    /**
     * 处理请求头
     * @param request
     * @return
     */
    protected Header[] doHeaders(HttpServletRequest request) {
        List<Header> headers = new ArrayList<Header>(16);
        Enumeration<String> names = request.getHeaderNames();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            if ("content-length".equals(name)||"Content-Length".equals(name)) continue;
            String value = request.getHeader(name);
            //获取登陆人ID
            if ("logintoken".equals(name)) ProxyContextUtils.getRequestProxyDTO().setLogintoken(value);
            headers.add(new BasicHeader(name, value));
        }
        //添加验证token
        if (connectionPropertis.getApi_token()!=null)
        headers.add(new BasicHeader("API_ACCESS_TOKEN",connectionPropertis.getApi_token()));

        //添加访问内网用户名
        if (connectionPropertis.getApi_login_name()!=null)
        headers.add(new BasicHeader("API_LOGIN_NAME",connectionPropertis.getApi_login_name()));

        //添加访问内网密码
        if (connectionPropertis.getApi_login_password()!=null)
        headers.add(new BasicHeader("API_LOGIN_PASSWORD",connectionPropertis.getApi_login_password()));

        return headers.toArray(new Header[0]);
    }

    public String arrayToString(String[] arrays){
        if (arrays.length==0) return "";
        String value="";
        for (String val:arrays){
            if ("".equals(value)) value=val;
                    else
                   value+=","+val;
        }
        return value;
    }
    public UriRoute getUriRoute() {
        return uriRoute;
    }

    public void setUriRoute(UriRoute uriRoute) {
        this.uriRoute = uriRoute;
    }

    public ConnectionPropertis getConnectionPropertis() {
        return connectionPropertis;
    }

    public void setConnectionPropertis(ConnectionPropertis connectionPropertis) {
        this.connectionPropertis = connectionPropertis;
    }
}


