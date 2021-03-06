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

package com.huawei.paas.cse.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Assert;
import org.junit.Test;

import com.huawei.paas.cse.core.config.ConfigurationSpringInitializer;
import com.huawei.paas.cse.core.context.ContextUtils;
import com.huawei.paas.cse.core.context.HttpStatus;
import com.huawei.paas.cse.core.context.InvocationContext;
import com.huawei.paas.cse.core.exception.InvocationException;

public class TestConfig {
    class MyConfigurationSpringInitializer extends ConfigurationSpringInitializer {
        Properties p;

        MyConfigurationSpringInitializer(Properties p) {
            this.p = p;
        }

        public void test() throws Exception {
            this.loadProperties(p);
        }
    }

    /**
     * Test Constants
     */
    @Test
    public void testConstants() {
        Assert.assertEquals("x-cse-context", Const.CSE_CONTEXT);
        Assert.assertEquals("rest", Const.RESTFUL);
        Assert.assertEquals("", Const.ANY_TRANSPORT);
        Assert.assertEquals("latest", Const.VERSION_RULE_LATEST);
        Assert.assertEquals("latest", Const.DEFAULT_VERSION_RULE);
    }

    /**
     * Test HttpResonse
     */
    @Test
    public void testHttpResponse() {
        String objectString = new String("Unit Testing");
        Response oResponse = Response.success(objectString, Status.OK);

        Assert.assertEquals(true, oResponse.isSuccessed());

        oResponse = Response.succResp(objectString);
        Assert.assertEquals(true, oResponse.isSuccessed());
        Assert.assertEquals(200, oResponse.getStatusCode());

        Throwable oThrowable = new Throwable("Error");

        oResponse = Response.consumerFailResp(oThrowable);
        Assert.assertEquals(true, oResponse.isFailed());

        oResponse = Response.providerFailResp(oThrowable);
        Assert.assertEquals(true, oResponse.isFailed());
    }

    /**
     * Test HttpStatus
     */
    @Test
    public void testHttpStatus() {
        StatusType oStatus = new HttpStatus(204, "InternalServerError");
        Assert.assertEquals("InternalServerError", oStatus.getReasonPhrase());
    }

    /**
     * Test ContextUtils
     */
    @Test
    public void testContextUtils() {
        ThreadLocal<InvocationContext> contextMgr = new ThreadLocal<>();
        Assert.assertEquals(contextMgr.get(), ContextUtils.getInvocationContext());

        Map<String, String> oContext = new HashMap<>();
        oContext.put("test1", new String("testObject"));

        InvocationContext oInnovation = new InvocationContext(oContext);
        Assert.assertEquals(oContext, oInnovation.getContext());

        oContext.put("test2", new String("testObject"));
        oInnovation.setContext(oContext);
        Assert.assertEquals(oContext, oInnovation.getContext());

        oInnovation.setStatus(Status.OK);
        Assert.assertEquals(200, oInnovation.getStatus().getStatusCode());

        oInnovation.setStatus(204);
        Assert.assertEquals(204, oInnovation.getStatus().getStatusCode());

        oInnovation.setStatus(Status.OK);
        Assert.assertEquals((Status.OK).getStatusCode(), oInnovation.getStatus().getStatusCode());

        oInnovation.setStatus(203, "Done");
        Assert.assertEquals(203, oInnovation.getStatus().getStatusCode());

        ContextUtils.setInvocationContext(oInnovation);
        Assert.assertEquals(oInnovation, ContextUtils.getInvocationContext());

        ContextUtils.removeInvocationContext();
        Assert.assertNotEquals(oContext, oInnovation);
    }

    /**
     * Test Response
     */
    @Test
    public void testResponse() {
        Response response = Response.create(400, "test", null);
        InvocationException exception = response.getResult();
        Assert.assertEquals(null, exception.getErrorData());

        response = Response.create(400, "test", "errorData");
        exception = response.getResult();
        Assert.assertEquals("errorData", exception.getErrorData());
    }

    /**
     * Test ConfigurationSpringInitializer
     */
    @Test
    public void testConfigurationSpringInitializer() throws Exception {
        Properties p = new Properties();
        MyConfigurationSpringInitializer oConf = new MyConfigurationSpringInitializer(p);
        oConf.setConfigId("testkey:testvalue,testkey2:testvalue2");
        boolean failed = false;
        try {
            oConf.test();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(e.getMessage().contains("can not find config for testkey:testvalue"), true);
            failed = true;
        }
        Assert.assertEquals(failed, true);
    }
}
