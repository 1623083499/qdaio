package com.wondersgroup.qdaio.servlet;

import com.wondersgroup.qdaio.proxy.DefaultHttpProxy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProxyServlet extends HttpServlet {
    DefaultHttpProxy defaultHttpProxy;
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)res;
        defaultHttpProxy.execute(request,response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        WebApplicationContext wc= WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        defaultHttpProxy=wc.getBean(DefaultHttpProxy.class);
    }
}
