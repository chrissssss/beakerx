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

var widgets = require('jupyter-js-widgets');
var _ = require('underscore');

var ButtonModel = widgets.ButtonModel.extend({
  defaults: _.extend({}, widgets.ButtonModel.prototype.defaults, {
    _view_name: "ButtonView1",
    _model_name: "ButtonModel1"
  })
});

var ButtonView = widgets.ButtonView.extend({
  _handle_click: function() {
    ButtonView.__super__._handle_click.apply(this, arguments);

    var tagName = this.model.get('tag');
    if (tagName) {
      this.runByTag(tagName);
    }
  },
  runByTag: function(tagName) {
    var notebook = this.options.cell.notebook;
    var cells = notebook.get_cells();
    var indexList = cells.reduce(function(acc, cell, index) {
      if (cell._metadata.tags && cell._metadata.tags.includes(tagName)) {
        acc.push(index);
      }
      return acc;
    }, []);

    notebook.execute_cells(indexList);
  }
});

module.exports = {
  ButtonModel: ButtonModel,
  ButtonView: ButtonView
};
