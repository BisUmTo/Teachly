function gameActionsGenerators() {
    const forBlock = Object.create(null);

    // Action getter block
    forBlock['action_getter'] = forBlock['action_boolean_getter'] = function(block, generator) {
        const action = block.getFieldValue('ACTION');
        const [getter, argType, returnType] = block.getFieldValue('GETTER').split(';');
        const object = generator.valueToCode(block, 'OBJECT', Blockly.JavaScript.ORDER_ATOMIC) || "null";

        const code = `${object}.${getter}()`;
        return [code, Blockly.JavaScript.ORDER_MEMBER];
    }

    // Action setter block
    forBlock['action_setter'] = function(block, generator) {
        const action = block.getFieldValue('ACTION');
        const [setter, argType, returnType] = block.getFieldValue('SETTER').split(';');
        const value = generator.valueToCode(block, 'VALUE', Blockly.JavaScript.ORDER_NONE) || "null";
        const object = generator.valueToCode(block, 'OBJECT', Blockly.JavaScript.ORDER_ATOMIC) || "null";

        return `${object}.${setter}(${value});\n`;
    }

    return forBlock;
}