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



// Event getter block
forBlock['event_getter'] = forBlock['event_boolean_getter'] = function(block, generator) {
  const event = block.getFieldValue('EVENT');
  const getter = block.getFieldValue('GETTER');
  
  const code = `${event}.${getter}()`;
  return [code, Order.NONE];
}

// Event setter block
forBlock['event_setter'] = function(block, generator) {
  const event = block.getFieldValue('EVENT');
  const setter = block.getFieldValue('SETTER');
  const value = generator.valueToCode(block, 'VALUE', Order.NONE) || "''";
  const code = `${event}.${setter}(${value});\n`;
  return code;
}


// VALIDATORS - EXTENSION //


export const validators = {
  'event_auto_field_extension' : function() {
    this.setOnChange(function (event) {
      let invalid = !updateEventName(this)
  
      // Disable invalid blocks (unless it's in a toolbox flyout,
      // since you can't drag disabled blocks to your workspace).
      if (!this.isInFlyout) {
        const initialGroup = Events.getGroup();
        // Make it so the move and the disable event get undone together.
        if (!event || !event.group) Events.setGroup(event.group);
        this.setDisabledReason(invalid, 'Event blocks must be nested inside an event procedure.');
        Events.setGroup(initialGroup);
      }
    });
  }
}
