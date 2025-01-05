function customBlocksGenerators() {
    const forBlock = Object.create(null);
    forBlock['assign_random_exercise'] = function(block, generator) {
        // TOFIX: Codice intermedio
        const addEvent = generator.provideFunction_(
            'assignRandomExercise',
            `function ${generator.FUNCTION_NAME_PLACEHOLDER_}() {\n` +
            `    let i = Math.floor(Math.random() * EXERCISES.length);\n` +
            `    let random = EXERCISES[i];\n` +
            `    console.log(random);\n` +
            `}`);
        return `${addEvent}();\n`;
    }
    return forBlock;
}