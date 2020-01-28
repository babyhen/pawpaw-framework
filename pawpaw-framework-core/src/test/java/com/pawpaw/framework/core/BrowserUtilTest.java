package com.pawpaw.framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.common.util.BrowserUtil;
import org.junit.Test;

public class BrowserUtilTest {


    @Test
    public void openBrownse() throws JsonProcessingException {
        BrowserUtil.openBrownse("http://www.baidu.com");
    }


}




