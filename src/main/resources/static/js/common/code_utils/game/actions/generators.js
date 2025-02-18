function gameActionsGenerators() {
    const forBlock = Object.create(null);

    // Action getter block
    forBlock['action_getter'] = forBlock['action_boolean_getter'] = function(block, generator) {
        const action = block.getFieldValue('ACTION');
        const getter = block.getFieldValue('GETTER');

        const code = `${action}.${getter}()`;
        return [code, Blockly.JavaScript.ORDER_MEMBER];
    }

    // Action setter block
    forBlock['action_setter'] = function(block, generator) {
        const action = block.getFieldValue('ACTION');
        const setter = block.getFieldValue('SETTER');
        const value = generator.valueToCode(block, 'VALUE', Blockly.JavaScript.ORDER_NONE) || "null";
        return `${action}.${setter}(${value});\n`;
    }

    return forBlock;
}