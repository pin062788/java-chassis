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

package com.huawei.paas.cse.provider.rest.common.schema;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.huawei.paas.cse.common.rest.RestConst;
import com.huawei.paas.cse.common.rest.definition.RestOperationMeta;
import com.huawei.paas.cse.core.Invocation;
import com.huawei.paas.cse.core.definition.OperationMeta;
import com.huawei.paas.cse.provider.rest.common.ProducerHttpRequestArgMapper;

public class TestHttpRequestParamCreator {

    /**
     * Test createMockParam
     * @throws Exception 
     */
    @Test
    public void testCreateMockParam() throws Exception {
        ProducerHttpRequestArgMapper lHttpRequestParamCreator = new ProducerHttpRequestArgMapper(0);
        Invocation invocation = Mockito.mock(Invocation.class);
        OperationMeta operationMeta = Mockito.mock(OperationMeta.class);
        Mockito.when(invocation.getOperationMeta()).thenReturn(operationMeta);
        RestOperationMeta swaggerOperation = Mockito.mock(RestOperationMeta.class);
        Mockito.when(operationMeta.getExtData(RestConst.SWAGGER_REST_OPERATION)).thenReturn(swaggerOperation);
        HttpServletRequest lHttpServletRequest =
            (HttpServletRequest) lHttpRequestParamCreator.createContextArg(invocation);
        Assert.assertNull(lHttpServletRequest.getMethod());
    }

}
