package com.wondersgroup.qdaio.gett.context;

import com.wondersgroup.qdaio.gett.dto.ContextDto;

/**
 * 上下文工具
 */
public class ContextUtils {
    private static ContextThreadLocal contextThreadLocal = new ContextThreadLocal();

    public static ContextDto getContext() {
        return contextThreadLocal.get();
    }

    public static void setContext(ContextDto dto) {
        contextThreadLocal.set(dto);
    }

    public static String getAppid() {
        return getContext().getAppid();
    }

    public static String getLoginname() {
        return getContext().getLoginname();
    }

    public static String getPassword() {
        return getContext().getPassword();
    }

    public String getKey() {
        return getContext().getKey();
    }

    public static String getTime() {
        return getContext().getTime();
    }

    public static String getSign() {
        return getContext().getSign();
    }

    public static String getAccess_token() {
        return getContext().getAccess_token();
    }

    public static String getParams() {
        return getContext().getParams();
    }

    public static String getBusiid() {
        return getContext().getBusiid();
    }
}
