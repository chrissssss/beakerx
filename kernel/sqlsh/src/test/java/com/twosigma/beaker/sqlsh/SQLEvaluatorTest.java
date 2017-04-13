/*
 *  Copyright 2017 TWO SIGMA OPEN SOURCE, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.twosigma.beaker.sqlsh;

import com.twosigma.beaker.KernelTest;
import com.twosigma.beaker.jupyter.KernelManager;
import com.twosigma.beaker.jvm.object.SimpleEvaluationObject;
import com.twosigma.beaker.table.TableDisplay;
import com.twosigma.jupyter.KernelParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.twosigma.beaker.evaluator.EvaluatorResultTestWatcher.waitForResult;
import static com.twosigma.beaker.jvm.object.SimpleEvaluationObject.EvaluationStatus.FINISHED;
import static com.twosigma.beaker.sqlsh.SqlForColorTable.CREATE_AND_SELECT_ALL;
import static com.twosigma.beaker.sqlsh.SqlKernelParameters.DATASOURCES;
import static com.twosigma.beaker.sqlsh.SqlKernelParameters.DEFAULT_DATASOURCE;
import static org.assertj.core.api.Assertions.assertThat;

public class SQLEvaluatorTest {

  private SQLEvaluator sqlEvaluator;
  private KernelTest kernelTest;

  @Before
  public void setUp() throws Exception {
    kernelTest = new KernelTest();
    KernelManager.register(kernelTest);
    sqlEvaluator = new SQLEvaluator("shellId1", "sessionId1");
    sqlEvaluator.startWorker();
    sqlEvaluator.setShellOptions(kernelParameters());
  }

  @After
  public void tearDown() throws Exception {
    sqlEvaluator.exit();
    KernelManager.register(null);
  }

  @Test
  public void evaluateSql() throws Exception {
    //given
    SimpleEvaluationObject seo = new SimpleEvaluationObject(CREATE_AND_SELECT_ALL);
    //when
    sqlEvaluator.evaluate(seo, CREATE_AND_SELECT_ALL);
    waitForResult(seo);
    //then
    verifyResult(seo);
  }

  private void verifyResult(SimpleEvaluationObject seo) {
    assertThat(seo.getStatus()).isEqualTo(FINISHED);
    assertThat(seo.getPayload() instanceof TableDisplay).isTrue();
    TableDisplay result = (TableDisplay) seo.getPayload();
    assertThat(result.getValues().size()).isEqualTo(3);
  }

  private KernelParameters kernelParameters() {
    Map<String, Object> params = new HashMap<>();
    params.put(DATASOURCES, "chemistry=jdbc:h2:mem:chemistry");
    params.put(DEFAULT_DATASOURCE, "jdbc:h2:mem:db1");
    return new KernelParameters(params);
  }
}