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

package com.huawei.paas.cse.serviceregistry.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.paas.cse.serviceregistry.api.response.MicroserviceInstanceChangedEvent;
import com.huawei.paas.cse.serviceregistry.client.IpPortManager;
import com.huawei.paas.cse.serviceregistry.notify.AbstractRegistryListener;

/**
 * Created by   on 2017/3/13.
 */
public class CacheRegistryListener extends AbstractRegistryListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheRegistryListener.class);

    @Override
    public void onMicroserviceInstanceChanged(MicroserviceInstanceChangedEvent changedEvent) {
        switch (changedEvent.getAction()) {
            case CREATE:
                LOGGER.info("microservice {}/{} REGISTERED an instance {}, {}.",
                        changedEvent.getKey().getAppId(),
                        changedEvent.getKey().getServiceName(),
                        changedEvent.getInstance().getInstanceId(),
                        changedEvent.getInstance().getEndpoints());
                break;
            case DELETE:
                LOGGER.info("microservice {}/{} UNREGISTERED an instance {}, {}.",
                        changedEvent.getKey().getAppId(),
                        changedEvent.getKey().getServiceName(),
                        changedEvent.getInstance().getInstanceId(),
                        changedEvent.getInstance().getEndpoints());
                break;
            case UPDATE:
                LOGGER.info("microservice {}/{} UPDATE an instance {} status or metadata, {}.",
                        changedEvent.getKey().getAppId(),
                        changedEvent.getKey().getServiceName(),
                        changedEvent.getInstance().getInstanceId(),
                        changedEvent.getInstance().getEndpoints());
                break;
            default:
                break;
        }
    }

    @Override
    public void onRecovered() {
        cleanUpCache();
    }

    private void cleanUpCache() {
        InstanceCacheManager.INSTANCE.cleanUp();
        IpPortManager.INSTANCE.clearInstanceCache();
        LOGGER.info(
                "Reconnected to service center, clean up the provider's microservice instances cache.");
    }
}
