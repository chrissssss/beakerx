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
package com.twosigma.beaker.widgets.selections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class MultipleSelectionWidget extends SelectionWidget{

  protected String[] value = new String[0];

  @Override
  protected HashMap<String, Serializable> content(HashMap<String, Serializable> content) {
    super.content(content);
    content.put(VALUE, this.value);
    return content;
  }

  @Override
  public void updateValue(Object value) {
    this.value = ((ArrayList<String>) value).stream().toArray(String[]::new);
  }

  public void setValue(String[] value) {
    this.value = value;
    sendUpdate(VALUE, value);
  }


}
