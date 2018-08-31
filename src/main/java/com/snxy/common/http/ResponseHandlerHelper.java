package com.snxy.common.http;

import com.alibaba.fastjson.JSON;
import com.snxy.common.constant.CommonConstant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by zhuchangbin on 2017/9/15.
 */
public class ResponseHandlerHelper {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandlerHelper.class);

    public static <T> ResponseHandler<T> createDefault(Class<T> clazz) {
        return new DefaultResponseHandler<>(clazz);
    }

    private static class DefaultResponseHandler<T> implements ResponseHandler<T> {

        private Class<T> clazz;

        private DefaultResponseHandler(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

            HttpEntity entity = response.getEntity();
            String str = EntityUtils.toString(entity, CommonConstant.DEFAULT_CHARSET);
            logger.info("ResponseHandlerHelper parse str:[{}], object:[{}]", str, clazz);
            return JSON.parseObject(str, clazz);
        }
    }
}
