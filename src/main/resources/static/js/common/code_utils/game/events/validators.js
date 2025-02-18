function gameEventsValidators() {
    return {
        'event_getter_extension' : function() {
            this.getField('EVENT').setVisible(false);
            this.setOnChange(function (event) {
                let invalid = checkEventBlock(this)

                // Disable invalid blocks (unless it's in a toolbox flyout,
                // since you can't drag disabled blocks to your workspace).
                if (!this.isInFlyout) {
                    const initialGroup = Blockly.Events.getGroup();
                    // Make it so the move and the disable event get undone together.
                    if (!event || !event.group) Blockly.Events.setGroup(event.group);
                    this.setDisabledReason(invalid, 'Event blocks must be nested inside an event procedure.');
                    Blockly.Events.setGroup(initialGroup);
                }

                const connection = this.outputConnection;
                const targetConnection = connection ? connection.targetConnection : null;
                if (targetConnection) {
                    const previousReturnTypes = targetConnection.check ? targetConnection.check : null;
                    const getterOptions = this.getField('GETTER').getOptions();
                    const currentOption = this.getFieldValue('GETTER');
                    let currentReturnTypes = currentOption ? (currentOption.split(';')[2]).split(',') : null;
                    const intersection = previousReturnTypes? previousReturnTypes.filter(type => currentReturnTypes.includes(type)) : [null];
                    if (intersection.length === 0) { // No common return types
                        const compatibleGetter = getterOptions.find(([optionText, optionValue]) => {
                            const [, , returnType] = optionValue.split(';');
                            const returnTypes = returnType ? returnType.split(',') : null;
                            return !returnTypes || !previousReturnTypes || returnTypes.filter(type => previousReturnTypes.includes(type)).length > 0;
                        });
                        if (compatibleGetter) {
                            this.getField('GETTER').setValue(compatibleGetter[1]);
                            const [methodName, argType, returnType] = compatibleGetter[1].split(';');
                            this.setOutput(true, returnType.split(','));
                            this.setDisabledReason(false);
                        } else {
                            this.setOutput(true);
                            this.setDisabledReason(true, `Incompatible return type. Expected: ${previousReturnType}`);
                        }
                    } else {
                        this.setOutput(true, currentReturnTypes);
                        this.setDisabledReason(false);
                    }
                } else {
                    this.setOutput(true);
                }
            });
        },
        'event_boolean_getter_extension' : function() {
            this.getField('EVENT').setVisible(false);
            this.setOnChange(function (event) {
                let invalid = checkEventBlock(this)

                // Disable invalid blocks (unless it's in a toolbox flyout,
                // since you can't drag disabled blocks to your workspace).
                if (!this.isInFlyout) {
                    const initialGroup = Blockly.Events.getGroup();
                    // Make it so the move and the disable event get undone together.
                    if (!event || !event.group) Blockly.Events.setGroup(event.group);
                    this.setDisabledReason(invalid, 'Event blocks must be nested inside an event procedure.');
                    Blockly.Events.setGroup(initialGroup);
                }

            });
        },
        'event_setter_extension' : function() {
            this.getField('EVENT').setVisible(false);
            this.setOnChange(function (event) {
                let invalid = checkEventBlock(this)

                // Disable invalid blocks (unless it's in a toolbox flyout,
                // since you can't drag disabled blocks to your workspace).
                if (!this.isInFlyout) {
                    const initialGroup = Blockly.Events.getGroup();
                    // Make it so the move and the disable event get undone together.
                    if (!event || !event.group) Blockly.Events.setGroup(event.group);
                    this.setDisabledReason(invalid, 'Event blocks must be nested inside an event procedure.');
                    Blockly.Events.setGroup(initialGroup);
                }

                const method = this.getFieldValue('SETTER');
                const [methodName, argType, returnType] = method ? method.split(';') : ['', '', ''];
                const argTypes = argType ? argType.split(',') : null;
                this.getInput('VALUE').setCheck(argTypes);
            });
        }
    }
}

function checkEventBlock(block) {
    let parent = block.parentBlock_;
    while (parent && parent.type !== 'event_procedure') {
        parent = parent.parentBlock_;
    }
    if (parent) {
        const parentEvent = parent.getFieldValue('EVENT');
        block.setFieldValue(parentEvent, 'EVENT');
        const eventName = parentEvent.split('.').pop();
    } else {
        block.setFieldValue('', 'EVENT');
    }
    block.setWarningText(
        parent
            ? null
            : `Event blocks must be nested inside an event procedure.`,
    );
    return !parent;
}