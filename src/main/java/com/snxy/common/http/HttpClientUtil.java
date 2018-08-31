package com.snxy.common.http;

import com.google.common.collect.Lists;
import com.snxy.common.constant.CommonConstant;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;


public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final HttpClient client;

    static {
        client = HttpClientFactory.createHttpClient();
    }

    /**
     * http get请求
     *
     * @param url    请求url
     * @param params 请求参数集合
     * @return HttpResponse
     */
    public static HttpResponse get(String url, Map<String, String> params) {

        String encodedParams = HttpClientUtil.encodeParameters(params);
        if (url.contains("?")) {
            url += "&" + encodedParams;
        } else {
            url += "?" + encodedParams;
        }
        HttpGet httpGet = new HttpGet(url);
        return httpRequest(httpGet);
    }

    /**
     * http post请求：表单方式
     *
     * @param url    请求URL
     * @param params 请求参数集合
     * @return HttpResponse
     */
    public static HttpResponse post(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> list = Lists.newArrayListWithCapacity(params == null ? 0 : params.size());
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(list, Charset.forName(CommonConstant.DEFAULT_CHARSET)));
        return httpRequest(httpPost);
    }

    /**
     * http post请求：json方式
     *
     * @param url  请求URL
     * @param json 请求 json 数据
     * @return HttpResponse
     */
    public static HttpResponse postJson(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Accept", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(json, Charset.forName(CommonConstant.DEFAULT_CHARSET)));
        return httpRequest(httpPost);
    }

    /**
     * http post请求：表单方式，根据传入 class 类型，自动转换为相应的 bean 对象
     *
     * @param url    请求URL
     * @param params 请求参数集合
     * @param clazz  转换的 class 类型
     * @return 传入类型的 bean 对象
     */
    public static <T> T post2Object(String url, Map<String, String> params, Class<T> clazz) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> list = Lists.newArrayListWithCapacity(params == null ? 0 : params.size());
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(list, Charset.forName(CommonConstant.DEFAULT_CHARSET)));
        return httpRequest(httpPost, clazz);
    }


    /**
     * http 请求最终执行方法
     *
     * @param request HttpUriRequest
     * @return String
     */
    protected static HttpResponse httpRequest(HttpUriRequest request) {
        if (null != client) {
            try {
                return client.execute(request);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * http 请求最终执行方法，使用自定义的 ResponseHandler， 转换为相应的 bean 对象
     *
     * @param request HttpUriRequest
     * @param clazz   需要转换的类型
     * @return 传入类型的 bean 对象
     */
    protected static <T> T httpRequest(HttpUriRequest request, Class<T> clazz) {
        if (null != client) {
            try {
                return client.execute(request, ResponseHandlerHelper.createDefault(clazz));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 对请求参数进行 utf-8 编码
     *
     * @param params 请求参数集合
     * @return 重新编码后的 url。例如：a=1&b=2
     */
    protected static String encodeParameters(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        if (null != params && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    builder.append(URLEncoder.encode(entry.getKey(), CommonConstant.DEFAULT_CHARSET))
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), CommonConstant.DEFAULT_CHARSET))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            logger.info(builder.toString());
        }
        return builder.substring(0, builder.length() - 1);
    }

}
