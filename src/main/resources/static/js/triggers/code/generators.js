function customBlocksGenerators() {
    const forBlock = Object.create(null);
    forBlock['assign_random_exercise'] = function(block, generator) {
        const player = generator.valueToCode(block, 'PLAYER', Blockly.JavaScript.ORDER_ATOMIC);
        const addEvent = generator.provideFunction_(
            'assignRandomExercise',
            `function ${generator.FUNCTION_NAME_PLACEHOLDER_}() {\n` +
            `    let i = Math.floor(Math.random() * EXERCISES.length);\n` +
            `    let exercise = EXERCISES[i];\n` +
            `    $.exercise(${player},exercise);\n` +
            `}`);
        return `${addEvent}();\n`;
    }
    return forBlock;
}