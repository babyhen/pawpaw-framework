package com.pawpaw.framework.core.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.exception.PawpawFrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这里把返回的对象加上公共的{@link RemoteResult}
 */

public class ResultEncoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultEncoder.class);


    public RemoteResult<? extends Object> encode(Object raw) {
        RemoteResult rr = new RemoteResult(raw);
        return rr;
    }


    public RemoteResult<? extends Object> encode(Exception ex) throws JsonProcessingException {
        RemoteResult br = null;
        if (ex == null) {
            //通常这类异常基本没有
            br = new RemoteResult(RemoteResultCode.FAIL, null);
        } else if (ex instanceof PawpawFrameworkException) {
            PawpawFrameworkException pe = (PawpawFrameworkException) ex;
            //这类异常是基本的业务上的异常，打印info级别即可个errormsg即可
            br = new RemoteResult(RemoteResultCode.FAIL, pe.getMessage(), null);
        } else {
            //这类异常是开发时期没有预料到的异常，需要把线程栈打印出来
            LOGGER.error("Exception-> {}", ex);
            br = new RemoteResult(RemoteResultCode.FAIL, ex.getMessage(), null);
        }
        return br;
    }
}
