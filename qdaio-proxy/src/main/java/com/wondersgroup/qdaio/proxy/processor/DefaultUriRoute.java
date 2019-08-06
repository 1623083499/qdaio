package com.wondersgroup.qdaio.proxy.processor;



import com.wondersgroup.qdaio.proxy.ConnectionPropertis;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class DefaultUriRoute extends UriRoute {
    public ConnectionPropertis getConnectionPropertis() {
        return connectionPropertis;
    }

    public void setConnectionPropertis(ConnectionPropertis connectionPropertis) {
        this.connectionPropertis = connectionPropertis;
    }

    @Autowired
    private ConnectionPropertis connectionPropertis;
    public DefaultUriRoute(ConnectionPropertis connectionPropertis){

    }
    @Override
    public String routeUri(HttpServletRequest request) {
        String uri=request.getRequestURI();
        return connectionPropertis.getTarget()
                +uri.replace(connectionPropertis.getProject_name()+"/proxy/","");
    }
}
