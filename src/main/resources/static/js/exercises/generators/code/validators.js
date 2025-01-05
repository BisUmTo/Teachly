function customBlocksValidators() {
    return {
        "generator_auto_field_extension": function () {
            this.setOnChange(function (event) {
                let type = this.getFieldValue('TYPE');
                switch (type) {
                    case 'MULTIPLE_CHOICE':
                        hintsInput(this, '- Options:');
                        break;
                    case 'OPEN_QUESTION':
                        hintsInput(this, '- Hints:');
                        break;
                    case 'TRUE_FALSE':
                        hintsInput(this, null);
                }
            });
        }
    }
}

function hintsInput(block, name) {
    let hints_label = block.getFieldValue('HINTS_LABEL');
    if (name !== hints_label) {
        block.removeInput('HINTS', true);
        block.removeInput('HINTS_END_ROW', true)

        if (name != null) {
            block.appendValueInput('HINTS')
                .setCheck('Array')
                .appendField(name, 'HINTS_LABEL');
            block.moveInputBefore('HINTS', 'SOLUTIONS');
            block.appendEndRowInput('HINTS_END_ROW');
            block.moveInputBefore('HINTS_END_ROW', 'SOLUTIONS');
        }
    }
}
