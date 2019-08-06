package com.wondersgroup.qdaio.proxy.processor;


import com.wondersgroup.qdaio.proxy.ProxyContextUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Component
public class GetProcessor extends MethodProcessor {

    public GetProcessor() {
        super();
    }

    /**
     * 根据ServletRequest生成HttpRequestBase
     * @param request
     * @return
     */
    @Override
    public HttpRequestBase doRequest(ServletRequest request) {
        if (request == null || !(request instanceof HttpServletRequest)) {
            return null;
        }

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        // 获得目标url
        String url = uriRoute.routeUri(servletRequest);
        // 目标路径为空，表示不路由
        if (!StringUtils.isNotBlank(url)) {
            return null;
        }
        String queryString=servletRequest.getQueryString();
        //日志记录请求参数
        ProxyContextUtils.getRequestProxyDTO().setParams(queryString);
        // 创建get请求
        HttpGet httpGet = new HttpGet(url+"?"+queryString);
        // 处理请求协议
        httpGet.setProtocolVersion(doProtocol(servletRequest));
        // 处理请求头
        httpGet.setHeaders(doHeaders(servletRequest));

        return httpGet;
    }
}
