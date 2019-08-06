package com.wondersgroup.qdaio.proxy;

import com.wondersgroup.qdaio.proxy.dto.RequestProxyDTO;

public class ProxyContextUtils {

   private static ProxyContext proxyContext=new ProxyContext();
   public static RequestProxyDTO getRequestProxyDTO(){
       RequestProxyDTO requestProxyDTO= proxyContext.get();
       if (requestProxyDTO==null){
            setRequestProxyDTO(new RequestProxyDTO());
            return proxyContext.get();
       }else{
           return requestProxyDTO;
       }
   }
   public static void setRequestProxyDTO(RequestProxyDTO requestProxyDTO){
       proxyContext.set(requestProxyDTO);
   }
    public static void setRealPath(String realPath){
        RequestProxyDTO requestProxyDTO =proxyContext.get();
        if(requestProxyDTO== null) requestProxyDTO=new RequestProxyDTO();

        requestProxyDTO.setRealPath(realPath);
        proxyContext.set(requestProxyDTO);
    }
}
