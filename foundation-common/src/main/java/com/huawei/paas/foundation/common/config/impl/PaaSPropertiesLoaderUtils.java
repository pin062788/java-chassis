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

package com.huawei.paas.foundation.common.config.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.huawei.paas.foundation.common.config.PaaSResourceUtils;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author   
 * @version  [版本号, 2016年11月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PaaSPropertiesLoaderUtils extends org.springframework.core.io.support.PropertiesLoaderUtils {

    /**
     * 使用内置规则
     *  输入：/a/b/abc.properties
     *  实际：
     *  1./a/b/abc.properties
     *   2./a/b/abc.[ext].properties
     * @param locationPattern locationPattern
     * @return Properties
     * @throws IOException
     * @throws Exception Exception
     */
    public static Properties loadMergedProperties(String locationPattern) throws IOException {
        Properties prop = new Properties();
        return fillMergedProperties(prop, locationPattern);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param prop prop
     * @param locationPattern locationPattern
     * @return Properties
     * @throws IOException
     * @throws Exception Exception
     */
    public static Properties fillMergedProperties(Properties prop, String locationPattern) throws IOException {
        if (StringUtils.isEmpty(locationPattern)) {
            throw new RuntimeException("Resource path must not be null or empty");
        }

        String suffix = PaaSResourceUtils.PROPERTIES_SUFFIX;
        if (!locationPattern.endsWith(suffix)) {
            throw new RuntimeException("Resource path must ends with " + suffix);
        }

        String prefix = locationPattern.substring(0, locationPattern.length() - suffix.length());

        List<Resource> resList = PaaSResourceUtils.getResources(locationPattern, prefix + ".*" + suffix);
        PaaSResourceUtils.sortProperties(resList);
        fillAllProperties(prop, resList);

        return prop;
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param prop prop
     * @param resList resList
     * @throws IOException
     * @throws Exception Exception
     */
    public static void fillAllProperties(Properties prop, List<Resource> resList) throws IOException {
        for (Resource res : resList) {
            PaaSPropertiesLoaderUtils.fillProperties(prop, res);
        }
    }
}
