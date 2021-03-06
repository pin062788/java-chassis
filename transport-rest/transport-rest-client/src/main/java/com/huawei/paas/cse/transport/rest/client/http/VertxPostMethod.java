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

package com.huawei.paas.cse.transport.rest.client.http;

import com.huawei.paas.cse.common.rest.definition.RestOperationMeta;
import com.huawei.paas.cse.core.AsyncResponse;
import com.huawei.paas.cse.core.Invocation;
import com.huawei.paas.foundation.common.net.IpPort;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;

public final class VertxPostMethod extends VertxHttpMethod {
    public static final VertxPostMethod INSTANCE = new VertxPostMethod();

    private VertxPostMethod() {
    }

    @Override
    protected HttpClientRequest createRequest(HttpClient client, Invocation invocation, IpPort ipPort, String path,
            RestOperationMeta operation,
            AsyncResponse asyncResp) {
        HttpClientRequest clientRequest =
            client.post(ipPort.getPort(), ipPort.getHostOrIp(), path, response -> {
                handleResponse(invocation, response, operation, asyncResp);
            });
        return clientRequest;
    }
}
