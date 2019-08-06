package com.wondersgroup.qdaio.gett.context;

/**
 * 通用异常管理
 */
public class BusinessException extends  RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
