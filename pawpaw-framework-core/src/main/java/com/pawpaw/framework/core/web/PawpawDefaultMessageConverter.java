package com.pawpaw.framework.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author liujixin
 * @date 2019-01-17
 * @description
 */
public class PawpawDefaultMessageConverter extends MappingJackson2HttpMessageConverter {

    public PawpawDefaultMessageConverter() {
        super();
        ObjectMapper om = this.getObjectMapper();
        //TODO
    }


}
