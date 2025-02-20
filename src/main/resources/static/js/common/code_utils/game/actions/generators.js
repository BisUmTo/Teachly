function gameActionsGenerators() {
    const forBlock = Object.create(null);

    // Action getter block
    forBlock['action_getter'] = forBlock['action_boolean_getter'] = function(block, generator) {
        const action = block.getFieldValue('ACTION');
        const [getter, argType, returnType] = block.getFieldValue('GETTER').split(';');
        let object = generator.valueToCode(block, 'OBJECT', Blockly.JavaScript.ORDER_ATOMIC) || "null";
        // TOFIX: This is a workaround
        object = object.split(';')[0];

        const code = `${object}.${getter}();\n`;
        return [code, Blockly.JavaScript.ORDER_MEMBER];
    }

    // Action setter block
    forBlock['action_setter'] = function(block, generator) {
        const action = block.getFieldValue('ACTION');
        const [setter, argType, returnType] = block.getFieldValue('SETTER').split(';');
        const value = generator.valueToCode(block, 'VALUE', Blockly.JavaScript.ORDER_NONE) || "null";
        let object = generator.valueToCode(block, 'OBJECT', Blockly.JavaScript.ORDER_ATOMIC) || "null";
        // TOFIX: This is a workaround
        object = object.split(';')[0];

        return `${object}.${setter}(${value});\n`;
    }

    return forBlock;
}