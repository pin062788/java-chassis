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
package com.huawei.paas.cse.core.provider.producer;

import org.junit.Assert;
import org.junit.Test;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author
 * @version  [版本号, 2017年5月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestProducerMeta {
    @Test
    public void test1() {
        Object instance = new Object();
        ProducerMeta meta = new ProducerMeta("id", instance, instance.getClass());
        Assert.assertEquals("id", meta.getSchemaId());
        Assert.assertEquals(instance, meta.getInstance());
        Assert.assertEquals(Object.class, meta.getInstanceClass());
    }

    @Test
    public void test2() {
        ProducerMeta meta = new ProducerMeta();
        meta.setSchemaId("id1");
        Assert.assertEquals("id1", meta.getSchemaId());

        meta.setInstance(1);
        Assert.assertEquals(1, meta.getInstance());

        meta.setInstanceClass(Integer.class);
        Assert.assertEquals(Integer.class, meta.getInstanceClass());
    }
}
