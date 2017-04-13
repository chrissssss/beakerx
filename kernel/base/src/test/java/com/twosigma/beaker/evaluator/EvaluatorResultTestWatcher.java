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

package com.twosigma.beaker.evaluator;

import com.twosigma.beaker.KernelSocketsTest;
import com.twosigma.beaker.jupyter.msg.JupyterMessages;
import com.twosigma.beaker.jvm.object.SimpleEvaluationObject;
import com.twosigma.jupyter.message.Message;

import java.util.Optional;

import static com.twosigma.beaker.jvm.object.SimpleEvaluationObject.EvaluationStatus.QUEUED;
import static com.twosigma.beaker.jvm.object.SimpleEvaluationObject.EvaluationStatus.RUNNING;

public class EvaluatorResultTestWatcher {
  public static final int ATTEMPT = 40;
  public static final int SLEEP_IN_MILLIS = 500;

  public static void waitForResult(SimpleEvaluationObject seo) throws InterruptedException {
    int count = 0;
    while ((seo.getStatus().equals(QUEUED) || seo.getStatus().equals(RUNNING)) && count < ATTEMPT) {
      Thread.sleep(SLEEP_IN_MILLIS);
      count++;
    }
    if (count == ATTEMPT) {
      throw new RuntimeException("No result, code evaluation took too long.");
    }
  }


  public static Optional<Message> waitForResult(KernelSocketsTest socketsTest) throws InterruptedException {
    int count = 0;
    Optional<Message> result = getResult(socketsTest);
    while (!result.isPresent() && count < ATTEMPT) {
      Thread.sleep(SLEEP_IN_MILLIS);
      result = getResult(socketsTest);
      count++;
    }
    return result;
  }

  private static Optional<Message> getResult(KernelSocketsTest socketsTest) {
    return socketsTest.getPublishedMessages().stream().
            filter(x -> x.type().equals(JupyterMessages.EXECUTE_RESULT)).findFirst();
  }

}
