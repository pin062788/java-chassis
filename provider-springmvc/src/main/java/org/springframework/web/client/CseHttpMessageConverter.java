/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.client;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.ReflectionUtils;

import com.huawei.paas.cse.provider.springmvc.reference.CseClientHttpRequest;
import com.huawei.paas.cse.provider.springmvc.reference.CseClientHttpResponse;

/**
 * 需要访问MessageBodyClientHttpResponseWrapper
 * 这是一个package级别的类，只好放在特殊的包内了
 * @author   
 * @version  [版本号, 2017年1月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CseHttpMessageConverter implements HttpMessageConverter<Object> {

    private static final List<MediaType> ALL_MEDIA_TYPE = Arrays.asList(MediaType.ALL);

    private static final Field RESPONSE_FIELD =
        ReflectionUtils.findField(MessageBodyClientHttpResponseWrapper.class, "response");

    static {
        RESPONSE_FIELD.setAccessible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return ALL_MEDIA_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object read(Class<? extends Object> clazz,
            HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        MessageBodyClientHttpResponseWrapper respWrapper = (MessageBodyClientHttpResponseWrapper) inputMessage;
        CseClientHttpResponse resp =
            (CseClientHttpResponse) ReflectionUtils.getField(RESPONSE_FIELD, respWrapper);
        return resp.getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Object t, MediaType contentType,
            HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        CseClientHttpRequest request = (CseClientHttpRequest) outputMessage;
        request.setRequestBody(t);
    }

}
