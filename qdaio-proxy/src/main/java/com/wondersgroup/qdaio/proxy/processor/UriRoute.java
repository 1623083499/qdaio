package com.wondersgroup.qdaio.proxy.processor;

import javax.servlet.http.HttpServletRequest;

abstract class UriRoute  {
   /**
    * 路由到目标地址
    * @param request
    * @return
    */
   public abstract String routeUri(HttpServletRequest request);
}