
import * as Blockly from 'blockly';
import './index.css';

// Register the blocks from the JSON file
import json1 from './blocks/text.json';
const blocks1 = Blockly.common.createBlockDefinitionsFromJsonArray(json1);
Blockly.common.defineBlocks(blocks1);

// Register the blocks from the JS file
import {blocks as json2} from './blocks/event_blocks';
const blocks2 = Blockly.common.createBlockDefinitionsFromJsonArray(json2);
Blockly.common.defineBlocks(blocks2);

// Register the validators and generators
import {javascriptGenerator} from 'blockly/javascript';
import {forBlock, validators} from './generators/javascript';
Object.assign(javascriptGenerator.forBlock, forBlock);
for (const [name, validator] of Object.entries(validators)) {
  Blockly.Extensions.register(name, validator);
}

// INTERFACCIA GRAFICA // 
// Set up UI elements and inject Blockly
const blocklyDiv = document.getElementById('blocklyDiv');
import toolbox from './toolbox.json';
import Theme from '@blockly/theme-modern';
const ws = Blockly.inject(blocklyDiv, {
  toolbox,
  theme: Theme
});
ws.addChangeListener(Blockly.Events.disableOrphans);

// PLUGINS //
// Dropdown with dependent fields
import '@blockly/field-dependent-dropdown';

// Backpack
import {Backpack} from '@blockly/workspace-backpack';
const backpack = new Backpack(ws);
backpack.init();

// Shareable procedures
import {blocks as procedureBlocks, unregisterProcedureBlocks} from '@blockly/block-shareable-procedures';
unregisterProcedureBlocks();
Blockly.common.defineBlocks(procedureBlocks);

// CrossTabCopyPaste
import {CrossTabCopyPaste} from '@blockly/plugin-cross-tab-copy-paste';
new CrossTabCopyPaste().init({
  contextMenu: true,
  shortcut: true,
});






// SALVA LOCALE // 
import {save, load} from './serialization';
// Load the initial state from storage and run the code.
load(ws);
// Every time the workspace changes state, save the changes to storage.
ws.addChangeListener((e) => {
  // UI events are things like scrolling, zooming, etc.
  // No need to save after one of these.
  if (e.isUiEvent) return;
  save(ws);
});

// CODICE JAVA SCRIPT // 
// This function resets the code and output divs, shows the
// generated code from the workspace, and evals the code.
// In a real application, you probably shouldn't use `eval`.
const codeDiv = document.getElementById('generatedCode').firstChild;
const outputDiv = document.getElementById('output');
const runCode = () => {
  const code = javascriptGenerator.workspaceToCode(ws);
  codeDiv.innerText = code;

  outputDiv.innerHTML = '';

  eval(code);
};
runCode();
// Whenever the workspace changes meaningfully, run the code again.
ws.addChangeListener((e) => {
  // Don't run the code when the workspace finishes loading; we're
  // already running it once when the application starts.
  // Don't run the code during drags; we might have invalid state.
  if (
    e.isUiEvent ||
    e.type == Blockly.Events.FINISHED_LOADING ||
    ws.isDragging()
  ) {
    return;
  }
  runCode();
});
