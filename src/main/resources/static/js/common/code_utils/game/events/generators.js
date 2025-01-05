function gameEventsGenerators(){
    const forBlock = Object.create(null);

    // Event block
    forBlock['event_procedure'] = function(block, generator) {
        const event = block.getFieldValue('EVENT');
        const statement = generator.statementToCode(block,'STATEMENT');
        // TOFIX: Codice intermedio
        const addEvent = generator.provideFunction_(
            'addEvent',
            `function ${generator.FUNCTION_NAME_PLACEHOLDER_}(event, statement) {\n` +
            `    const outputDiv = document.getElementById('output');\n` +
            `    outputDiv.addEventListener(event, statement);\n` +
            `}`);
        return `${addEvent}("${event.replace(/Event$/, '')}", (${event})=>{\n${indentCode(statement)}});\n`;
    }

    // Event getter block
    forBlock['event_getter'] = forBlock['event_boolean_getter'] = function(block, generator) {
        const event = block.getFieldValue('EVENT');
        const getter = block.getFieldValue('GETTER');

        const code = `${event}.${getter}()`;
        return [code, Order.NONE];
    }

    // Event setter block
    forBlock['event_setter'] = function(block, generator) {
        const event = block.getFieldValue('EVENT');
        const setter = block.getFieldValue('SETTER');
        const value = generator.valueToCode(block, 'VALUE', Order.NONE) || "''";
        return `${event}.${setter}(${value});\n`;
    }

    return forBlock
}

