package com.pawpaw.framework.core.exception;

/**
 * 业务类的异常，供上层业务逻辑调用
 *
 * @Auther: liujixin
 * @Date: 2019-02-12
 */
public class BusinessException extends PawpawFrameworkException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

}
