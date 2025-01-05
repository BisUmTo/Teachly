function gameEventsValidators() {
    return {
        'event_auto_field_extension' : function() {
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