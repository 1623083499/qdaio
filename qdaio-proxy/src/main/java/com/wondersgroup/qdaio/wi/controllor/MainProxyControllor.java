package com.wondersgroup.qdaio.wi.controllor;

import com.wondersgroup.qdaio.proxy.DefaultHttpProxy;
import com.wondersgroup.qdaio.proxy.ProxyContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/proxy")
public class MainProxyControllor {
    @Autowired
    DefaultHttpProxy defaultHttpProxy;
    @RequestMapping("/*")
    public  void callProxyContrallor(@PathVariable  String realPath, HttpServletRequest request, HttpServletResponse response){
        //ProxyContextUtils.setRealPath(realPath);
        defaultHttpProxy.execute(request,response);
    }
}
