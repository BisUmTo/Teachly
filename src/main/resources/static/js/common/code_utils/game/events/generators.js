function gameEventsGenerators(){
    const forBlock = Object.create(null);

    // Event block
    forBlock['event_procedure'] = function(block, generator) {
        const event = block.getFieldValue('EVENT');
        const statement = generator.statementToCode(block,'STATEMENT');
        const addEvent = generator.provideFunction_(
            `on${event.replace(/Event$/, '')}`,
            `function ${generator.FUNCTION_NAME_PLACEHOLDER_}(event) {\n` +
            `${indentCode(statement)}` +
            `}`);
        return `$.subscribe("${event}", "${addEvent}");\n`;
    }

    // Event getter block
    forBlock['event_getter'] = forBlock['event_boolean_getter'] = function(block, generator) {
        const event = block.getFieldValue('EVENT');
        const [getter, argType, returnType] = block.getFieldValue('GETTER').split(';');

        const code = `${event}.${getter}()`;
        return [code, Blockly.JavaScript.ORDER_MEMBER];
    }

    // Event setter block
    forBlock['event_setter'] = function(block, generator) {
        const event = block.getFieldValue('EVENT');
        const [setter, argType, returnType] = block.getFieldValue('SETTER').split(';');
        const value = generator.valueToCode(block, 'VALUE', Blockly.JavaScript.ORDER_NONE) || "null";
        return `${event}.${setter}(${value});\n`;
    }

    return forBlock
}

