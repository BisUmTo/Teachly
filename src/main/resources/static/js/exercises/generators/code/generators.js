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

        const stringifyFunction = generator.provideFunction_(
            'stringify',
            `function ${generator.FUNCTION_NAME_PLACEHOLDER_}(value) {\n` +
            `    if (Array.isArray(value)) {\n` +
            `        return value.map(stringify);\n` +
            `    }\n` +
            '    return `${value}`;\n' +
            `}\n`
        )

        const stringifiedArrayFunction = generator.provideFunction_(
            'stringifiedArray',
            `function ${generator.FUNCTION_NAME_PLACEHOLDER_}(value) {\n` +
            `    if (!Array.isArray(value)) {\n` +
            `        return ${stringifyFunction}([value]);\n` +
            `    }\n` +
            `    return ${stringifyFunction}(value);\n` +
            `}\n`
        )

        const generateQuestion = generator.provideFunction_(
            'generateQuestion',
            `async function ${generator.FUNCTION_NAME_PLACEHOLDER_}(type, difficulty, question_, hints_, solutions_) {\n` +
            // FETCH POST /api/v1/exercises
            `    const generatorId = window.location.pathname.split('/').pop();\n` +
            `    const question = ${stringifyFunction}(question_);\n` +
            `    const hints = ${stringifiedArrayFunction}(hints_);\n` +
            `    const solutions = ${stringifiedArrayFunction}(solutions_);\n` +
            `    try {\n` +
            `        let response = await fetch('/api/v1/exercises/generators/'+generatorId+'/generate', {\n` +
            `            method: 'POST',\n` +
            `            headers: {\n` +
            `                'Content-Type': 'application/json'\n` +
            `            },\n` +
            `                 body: JSON.stringify({\n` +
            `                 type,\n` +
            `                 difficulty,\n` +
            `                 question,\n` +
            `                 hints,\n` +
            `                 solutions\n` +
            `            })\n` +
            `        });\n` +
            `        let data = await response.json()\n` +
            `        console.log(data);\n` +
            `    } catch (error) {\n` +
            `        console.error('Error:', error);\n` +
            `    }\n` +
            `}\n`
        );
        return `await ${generateQuestion}(\n` +
               `    "${type}",\n` +
               `    "${difficulty}",\n` +
               `    ${question},\n`+
               `    ${hints},\n` +
               `    ${solutions}\n` +
               `);\n`;
    }
    return forBlock;
}