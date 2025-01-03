import {Order} from 'blockly/javascript';
import {Events} from 'blockly/core';

// BLOCKS //
export const forBlock = Object.create(null);

// Add text block
forBlock['add_text'] = function (block, generator) {
  const text = generator.valueToCode(block, 'TEXT', Order.NONE) || "''";
  // TOFIX: Codice intermedio 
  const addText = generator.provideFunction_(
    'addText',
    `function ${generator.FUNCTION_NAME_PLACEHOLDER_}(text) {
  // Add text to the output area.
  const outputDiv = document.getElementById('output');
  const textEl = document.createElement('p');
  textEl.innerText = text;
  outputDiv.appendChild(textEl);
}`);
  const code = `${addText}(${text});\n`;
  return code;
};

// Event block
forBlock['event_procedure'] = function(block, generator) {
  const event = block.getFieldValue('EVENT');
  const statement = generator.statementToCode(block,'STATEMENT');
  // TOFIX: Codice intermedio
  const addEvent = generator.provideFunction_(
    'addEvent',
    `function ${generator.FUNCTION_NAME_PLACEHOLDER_}(event, statement) {
    // Add text to the output area.
    const outputDiv = document.getElementById('output');
    outputDiv.addEventListener(event, statement);
}`);
  function indent(code) {
    if (!code) return '';
    return code.split('\n').map(line => `  ${line}`.trimEnd()).join('\n');
  }
  const code = `${addEvent}("${event}", (${event}Event)=>{\n${indent(statement)}});\n`;
  return code;
}

// Event getter block
forBlock['event_getter'] = forBlock['event_boolean_getter'] = function(block, generator) {
  const event = block.getFieldValue('EVENT');
  const getter = block.getFieldValue('GETTER');
  
  const code = `${event}Event.${getter}`;
  return [code, Order.NONE];
}

// Event setter block
forBlock['event_setter'] = function(block, generator) {
  const event = block.getFieldValue('EVENT');
  const setter = block.getFieldValue('SETTER');
  const value = generator.valueToCode(block, 'VALUE', Order.NONE) || "''";
  const code = `${event}Event.${setter} = ${value};\n`;
  return code;
}


// VALIDATORS - EXTENSION //
function updateEventName(block) {
  let parent = block.parentBlock_;
  while (parent && parent.type !== 'event_procedure') {
    parent = parent.parentBlock_;
  }
  if (parent) {
    const parentEvent = parent.getFieldValue('EVENT');
    block.setFieldValue(parentEvent, 'EVENT');
  } else {
    block.setFieldValue('', 'EVENT');
  }
  block.setWarningText(
    parent
      ? null
      : `Event blocks must be nested inside an event procedure.`,
  );
  return !!parent;
}

export const validators = {
  'event_auto_field_extension' : function() {
    this.setOnChange(function (event) {
      let valid = updateEventName(this)
  
      // Disable invalid blocks (unless it's in a toolbox flyout,
      // since you can't drag disabled blocks to your workspace).
      if (!this.isInFlyout) {
        const initialGroup = Events.getGroup();
        // Make it so the move and the disable event get undone together.
        if (!event || !event.group) Events.setGroup(event.group);
        this.setEnabled(valid);
        Events.setGroup(initialGroup);
      }
    });
  }
}
