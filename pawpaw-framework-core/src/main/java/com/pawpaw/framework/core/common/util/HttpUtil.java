package com.pawpaw.framework.core.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 设置超时时间
     */
    private static HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom()
            .setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build()).build();

    public static final String DEFAULT_CHARSET = "UTF-8";

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    public static String postMultipartFormData(String url) {
        return postMultipartFormData(url, null);
    }

    public static String postMultipartFormData(String url, byte[] data) {
        return postMultipartFormData(url, data, null);
    }

    public static String postMultipartFormData(String url, byte[] data, HttpCallBack callBack) {
        logger.debug("http url is {}", url);
        HttpPost post = new HttpPost(url);
        ByteArrayEntity entity = new ByteArrayEntity(new byte[0]);
        if (data != null) {
            entity = new ByteArrayEntity(data, ContentType.MULTIPART_FORM_DATA);
        }
        post.setEntity(entity);
        try {
            if (callBack != null) {
                callBack.beforeHttp(client, post, entity);
            }

            HttpResponse response = client.execute(post);
            String resp = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            logger.debug(resp);
            if (callBack != null) {
                callBack.afterHttp(client, response, resp);
            }
            int status = response.getStatusLine().getStatusCode();
            logger.debug("http resp status is {}", status);
            if (status != HttpStatus.SC_OK) {
                logger.warn("http resp status is {}", status);
                throw new RuntimeException("http请求status异常,status=" + status);
            }
            return resp;
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onException(post, e);
            }
            throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
        }

    }
    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////

    public static String postFormData(String url) {
        return postFormData(url, null);
    }

    public static String postFormData(String url, Map<String, String> data) {
        return postFormData(url, data, null);
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param data
     * @param callBack
     * @return
     * @throws Exception
     */
    public static String postFormData(String url, Map<String, String> data, HttpCallBack callBack) {
        logger.debug("http url is {}", url);
        HttpPost post = new HttpPost(url);

        List<NameValuePair> pairs = new LinkedList<>();
        if (data != null && data.size() > 0) {
            for (Entry<String, String> e : data.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();
                pairs.add(new BasicNameValuePair(key, value));
            }

        }
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
            post.setEntity(entity);
            if (callBack != null) {
                callBack.beforeHttp(client, post, entity);
            }

            HttpResponse response = client.execute(post);
            String resp = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            logger.debug(resp);
            if (callBack != null) {
                callBack.afterHttp(client, response, resp);
            }
            int status = response.getStatusLine().getStatusCode();
            logger.debug("http resp status is {}", status);
            if (status != HttpStatus.SC_OK) {
                logger.warn("http resp status is {}", status);
                throw new RuntimeException("http请求status异常,status=" + status);
            }
            return resp;
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onException(post, e);
            }
            throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
        }

    }

    //////////////////////////////////////////////////////////

    public static String postStringData(String url) {
        return postStringData(url, null);
    }

    public static String postStringData(String url, String data) {
        return postStringData(url, data, null);
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param data
     * @param callBack
     * @return
     * @throws Exception
     */
    public static String postStringData(String url, String data, HttpCallBack callBack) {
        logger.debug("http url is {}", url);
        HttpPost post = new HttpPost(url);
        StringEntity se = new StringEntity(data, DEFAULT_CHARSET);
        post.setEntity(se);
        try {
            if (callBack != null) {
                callBack.beforeHttp(client, post, se);
            }

            HttpResponse response = client.execute(post);
            String resp = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            logger.debug(resp);
            if (callBack != null) {
                callBack.afterHttp(client, response, resp);
            }
            int status = response.getStatusLine().getStatusCode();
            logger.debug("http resp status is {},data is {}", status, resp);
            if (status != HttpStatus.SC_OK) {
                logger.warn("http resp status is {},data is {}", status, resp);
                throw new RuntimeException("http请求status异常,status=" + status + ",数据=" + resp);
            }
            return resp;
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onException(post, e);
            }
            throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
        }

    }

    /////////////////////////////////////////////////////////////
    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, HttpCallBack callBack) {
        return get(url, null, callBack);
    }

    public static String get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param callBack
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, String> params, HttpCallBack callBack) {
        logger.debug("http url is {}", url);
        if (params != null) {
            List<NameValuePair> paramItem = new ArrayList<>();
            for (Entry<String, String> entry : params.entrySet()) {
                paramItem.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String paramStr = URLEncodedUtils.format(paramItem, DEFAULT_CHARSET);
            if (!url.endsWith("?")) {
                url += "?";
            }
            url += paramStr;
            logger.debug("processed http url is {}", url);
        }
        HttpGet get = new HttpGet(url);
        try {
            if (callBack != null) {
                callBack.beforeHttp(client, get, null);
            }
            HttpResponse response = client.execute(get);
            String resp = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            logger.debug(resp);
            if (callBack != null) {
                callBack.afterHttp(client, response, resp);
            }
            int status = response.getStatusLine().getStatusCode();
            logger.debug("http resp status is {},data is {}", status, resp);
            if (status != HttpStatus.SC_OK) {
                logger.warn("http resp status is {},data is {}", status, resp);
                throw new RuntimeException("http请求status异常,status=" + status + ",数据=" + resp);
            }
            return resp;
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onException(get, e);
            }
            throw new RuntimeException(e);
        } finally {
            get.releaseConnection();
        }

    }

    ///////////////////////////////////////////////////////////////////////////////

    public static void download(String url, String data, boolean checkStatus, OutputStream out) {
        HttpPost post = new HttpPost(url);
        StringEntity se = null;
        if (data != null) {
            se = new StringEntity(data, DEFAULT_CHARSET);
            post.setEntity(se);
        }
        try {

            HttpResponse response = client.execute(post);

            int status = response.getStatusLine().getStatusCode();
            logger.debug("http resp status is {}", status);
            if (checkStatus) {
                if (status != HttpStatus.SC_OK) {
                    throw new Exception("http resp code is not SC_OK");
                }
            }
            IOUtils.copy(response.getEntity().getContent(), out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
            try {
                out.close();
            } catch (IOException e) {
                // noop
            }
        }

    }

    /**
     * http请求的回调
     *
     * @author liujixin
     */
    public static interface HttpCallBack {

        public void beforeHttp(HttpClient client, HttpRequestBase httpMethod, HttpEntity reqEntity) throws Exception;

        /**
         * 由于已经把返回的数据从response中读出来了，所以如果子类再次从response中读数据的话，会抛异常
         *
         * @param client
         * @param response
         * @param respData
         * @throws Exception
         */
        public void afterHttp(HttpClient client, HttpResponse response, String respData) throws Exception;

        default void onException(HttpRequestBase httpMethod, Exception e) {
            logger.error("发送http请求失败，{}，{}", e.getMessage());
        }
    }

    public static class ChangeHeaderCallBack implements HttpCallBack {
        Map<String, String> headers;

        public ChangeHeaderCallBack(Map<String, String> headers) {
            this.headers = headers;
        }


        @Override
        public void beforeHttp(HttpClient client, HttpRequestBase httpMethod, HttpEntity reqEntity) throws Exception {
            if (this.headers != null) {
                headers.keySet().forEach(key -> {
                    httpMethod.setHeader(key, headers.get(key));
                });
            }
        }

        @Override
        public void afterHttp(HttpClient client, HttpResponse response, String respData) throws Exception {
            //noop
        }
    }
}
