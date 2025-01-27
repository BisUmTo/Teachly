function customBlocksGenerators() {
    const forBlock = Object.create(null);
    forBlock['reward_variable'] = function(block, generator) {
        const variable = block.getFieldValue('VARIABLE');
        return [`${variable}`, Blockly.JavaScript.ORDER_ATOMIC];
    }
    forBlock['reward_procedure'] = function(block, generator) {
        return generator.statementToCode(block,'STATEMENT');
    }
    return forBlock;
}