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
package com.huawei.cse.springboot.starter.configuration;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author
 */
public class TestConfigurableEnvironmentConfiguration{

	private ConfigurableEnvironmentConfiguration configurableEnvironmentConfiguration;

    @Before
    public void setUp() throws Exception {
    	configurableEnvironmentConfiguration = new ConfigurableEnvironmentConfiguration(null);
    }

    @After
    public void tearDown() throws Exception {
    	configurableEnvironmentConfiguration = null;
    }

    @Test
    public void testIsEmpty()
    {
    	Assert.assertFalse(configurableEnvironmentConfiguration.isEmpty());
    }
    
    @Test
    public void testContainsKey()
    {
    	configurableEnvironmentConfiguration = new ConfigurableEnvironmentConfiguration(Mockito.mock(ConfigurableEnvironment.class));
    	Assert.assertFalse(configurableEnvironmentConfiguration.containsKey("path.separator"));
    }
    
    @Test
    public void testGetProperty()
    {
    	configurableEnvironmentConfiguration = new ConfigurableEnvironmentConfiguration(Mockito.mock(ConfigurableEnvironment.class));
    	Assert.assertEquals(null,configurableEnvironmentConfiguration.getProperty("path.separator"));
    }
    
}