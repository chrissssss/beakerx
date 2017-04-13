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
package com.twosigma.beaker.easyform.formitem.widgets;

import com.twosigma.beaker.easyform.formitem.ListComponent;
import com.twosigma.beaker.jupyter.comm.Comm;
import com.twosigma.beaker.widgets.CommFunctionality;
import com.twosigma.beaker.widgets.DOMWidget;
import com.twosigma.beaker.widgets.selections.SelectMultipleSingle;

import java.util.Collection;

public class SelectMultipleSingleWidget extends ListComponent implements CommFunctionality, EasyFormWidget {

  private SelectMultipleSingle widget;

  public SelectMultipleSingleWidget() {
    this.widget = new SelectMultipleSingle();
  }

  @Override
  public String getLabel() {
    return widget.getDescription();
  }

  @Override
  public Comm getComm() {
    return widget.getComm();
  }

  @Override
  public void setLabel(String label) {
    this.widget.setDescription(label);
  }

  @Override
  public String getValue() {
    return null;
  }

  @Override
  public void setValue(String value) {
    this.widget.setValue(value);
  }

  @Override
  public void setSize(Integer size) {
    this.widget.setSize(size);
  }

  @Override
  public Integer getSize() {
    return this.widget.getSize();
  }

  @Override
  public void setValues(Collection<String> values) {
    super.setValues(values);
    this.widget.setOptions(values.stream().toArray(String[]::new));
  }

  @Override
  public DOMWidget getWidget() {
    return widget;
  }
  
  @Override
  public void close() {
    getComm().close();
  }
  
}