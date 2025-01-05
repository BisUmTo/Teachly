function customBlocksGenerators() {
    const forBlock = Object.create(null);
    forBlock['assign_random_exercise'] = function(block, generator) {
        const variable = block.getFieldValue('VARIABLE');
        // TOFIX: Codice intermedio
        return [`${variable}`, Blockly.JavaScript.ORDER_ATOMIC];
    }
    return forBlock;
}