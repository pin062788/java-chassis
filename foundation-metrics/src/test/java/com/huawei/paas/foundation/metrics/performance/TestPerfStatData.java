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

package com.huawei.paas.foundation.metrics.performance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author  
 * @since Mar 14, 2017
 * @see 
 */
public class TestPerfStatData {

    PerfStatData oPerfStatData = null;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        oPerfStatData = new PerfStatData("testData");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        oPerfStatData = null;
    }

    /**
     * Test Un-Initialized Values
     */
    @Test
    public void testDefaultValues() {
        Assert.assertEquals("testData", oPerfStatData.getName());
        Assert.assertEquals(0, oPerfStatData.getCallCount());
        Assert.assertEquals(0, oPerfStatData.getMsLatency());
        Assert.assertNotNull(oPerfStatData.getMsLatencySegments());
        Assert.assertEquals(0, oPerfStatData.getMsgCount());
        Assert.assertNotNull(PerfStatData.getStrSegmentDef());

    }

    /**
     * Test the add
     */
    @Test
    public void testAdd() {
        oPerfStatData.add(10, 100);
        Assert.assertEquals(10, oPerfStatData.getMsgCount());
        Assert.assertEquals(100, oPerfStatData.getMsLatency());

        //Test Add function with PerfStatContext
        PerfStatContext oPerfStatContext = new PerfStatContext();
        oPerfStatContext.setMsgCount(30);
        oPerfStatData.add(oPerfStatContext);
        Assert.assertEquals(40, oPerfStatData.getMsgCount());
    }

    /**
     * Test MergeFrom
     */
    @Test
    public void testMergeFrom() {
        oPerfStatData.mergeFrom(new PerfStatData("anotherData"));
        Assert.assertEquals(0, oPerfStatData.getMsgCount());
        Assert.assertEquals(0, oPerfStatData.getCallCount());
        Assert.assertEquals(0, oPerfStatData.getMsLatency());
    }

    /**
     * Test calc
     */
    @Test
    public void testCalc() {
        PerfResult oPerfResult = oPerfStatData.calc(System.currentTimeMillis() + 18989);
        Assert.assertEquals("  all testData  :", oPerfResult.getName());
        Assert.assertEquals(0, oPerfResult.getCallCount());
        Assert.assertEquals(0, oPerfResult.getMsgCount());

        //test calc with another PerfStatData
        oPerfResult = oPerfStatData.calc(new PerfStatData("anotherData"), System.currentTimeMillis() + 18989);
        Assert.assertEquals("  cycle testData:", oPerfResult.getName());
        Assert.assertEquals(0, oPerfResult.getCallCount());
        Assert.assertEquals(0, oPerfResult.getMsgCount());
    }

}
