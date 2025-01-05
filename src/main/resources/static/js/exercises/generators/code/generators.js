function customBlocksGenerators() {
    const forBlock = Object.create(null);
    forBlock['generator_procedure'] = function(block, generator) {
        return generator.statementToCode(block,'STATEMENT');
    }
    forBlock['generate_question'] = function(block, generator) {
        const type = block.getFieldValue('TYPE');
        const difficulty = block.getFieldValue('DIFFICULTY');
        const question = generator.valueToCode(block, 'QUESTION', Blockly.JavaScript.ORDER_ATOMIC);
        const hintsInput = block.getInput('HINTS');
        const hints = hintsInput?generator.valueToCode(block, 'HINTS', Blockly.JavaScript.ORDER_ATOMIC):'[]';
        const solutions = generator.valueToCode(block, 'SOLUTIONS', Blockly.JavaScript.ORDER_ATOMIC);

        // TOFIX: Generator and name
        const generateQuestion = generator.provideFunction_(
            'generateQuestion',
            `function ${generator.FUNCTION_NAME_PLACEHOLDER_}(type, difficulty, question, hints, solutions) {\n` +
            // FETCH POST /api/v1/exercises
            `    fetch('/api/v1/exercises', {\n` +
            `        method: 'POST',\n` +
            `        headers: {\n` +
            `            'Content-Type': 'application/json'\n` +
            `        },\n` +
            `        body: JSON.stringify({\n` +
            `            type,\n` +
            `            difficulty,\n` +
            `            question,\n` +
            `            hints,\n` +
            `            solutions\n` +
            `        })\n` +
            `    })\n` +
            `    .then(response => response.json())\n` +
            `    .then(data => console.log(data))\n` +
            `    .catch(error => console.error('Error:', error));\n` +
            `}\n`
        );
        return `${generateQuestion}("${type}", "${difficulty}", ${question}, ${hints}, ${solutions});\n`;
    }
    return forBlock;
}