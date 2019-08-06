package com.wondersgroup.qdaio.proxy;

import com.wondersgroup.qdaio.proxy.dto.RequestProxyDTO;

public class ProxyContext extends ThreadLocal<RequestProxyDTO>{
    @Override
    public RequestProxyDTO get() {
        return super.get();
    }

    @Override
    public void set(RequestProxyDTO value) {
        super.set(value);
    }
}
