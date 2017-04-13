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
package com.twosigma.beaker;

import com.twosigma.beaker.jupyter.msg.JupyterMessages;
import com.twosigma.jupyter.message.Header;
import com.twosigma.jupyter.message.Message;

import java.io.Serializable;
import java.util.HashMap;

public class MessageFactoryTest {

  public static Message getExecuteRequestMessage(String code) {
    Message message = new Message();
    Header header = new Header();
    header.setTypeEnum(JupyterMessages.EXECUTE_REQUEST);
    message.setHeader(header);
    HashMap<String, Serializable> content = new HashMap<>();
    content.put("code", code);
    message.setContent(content);
    return message;
  }

}
