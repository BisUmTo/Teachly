function customBlocksValidators() {
    return {
        'reward_auto_field_extension' : function() {
            this.setOnChange(function (event) {
                let invalid = checkRewardBlock(this);

                const variable = this.getFieldValue('VARIABLE');
                switch (variable) {
                    case 'solutions':
                    case 'hints':
                        this.outputConnection.setCheck('Array');
                        this.setStyle('list_blocks');
                        break;
                    case 'response':
                    case 'solutions[0]':
                        this.outputConnection.setCheck('String');
                        this.setStyle('text_blocks');
                        break;
                    case 'player':
                        this.outputConnection.setCheck('Player');
                        this.setStyle('variable_dynamic_blocks');
                }

                // Disable invalid blocks (unless it's in a toolbox flyout,
                // since you can't drag disabled blocks to your workspace).
                if (!this.isInFlyout) {
                    const initialGroup = Blockly.Events.getGroup();
                    // Make it so the move and the disable event get undone together.
                    if (!event || !event.group) Blockly.Events.setGroup(event.group);
                    this.setDisabledReason(invalid, 'Reward variable blocks must be nested inside a reward procedure.');
                    Blockly.Events.setGroup(initialGroup);
                }
            });
        }
    }
}

function checkRewardBlock(block) {
    let parent = block.parentBlock_;
    while (parent && parent.type !== 'reward_procedure') {
        parent = parent.parentBlock_;
    }
    block.setWarningText(
        parent
            ? null
            : `Reward variable blocks must be nested inside a reward procedure.`,
    );
    return !parent;
}

