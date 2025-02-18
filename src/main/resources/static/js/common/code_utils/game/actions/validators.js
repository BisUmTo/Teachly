function gameActionsValidators() {
    return {
        'action_getter_extension' : function() {
            this.getField('ACTION').setVisible(false);
            this.setOnChange(function (event) {
                // Type check
                const object = this.getInputTargetBlock('OBJECT');
                const connectionType = object ? object.outputConnection.check : '';
                const mainConnectionType = connectionType ? connectionType[0] :'';
                this.setFieldValue(mainConnectionType, 'ACTION');

                // Output type
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
        'action_boolean_getter_extension' : function() {
            this.getField('ACTION').setVisible(false);
            this.setOnChange(function (event) {
                // Type check
                const object = this.getInputTargetBlock('OBJECT');
                const connectionType = object ? object.outputConnection.check : '';
                const mainConnectionType = connectionType ? connectionType[0] : null;
                this.setFieldValue(mainConnectionType, 'ACTION');
            });
        },
        'action_setter_extension' : function() {
            this.getField('ACTION').setVisible(false);
            this.setOnChange(function (event) {
                // Type check
                const object = this.getInputTargetBlock('OBJECT');
                const connectionType = object ? object.outputConnection.check : '';
                const mainConnectionType = connectionType ? connectionType[0] : null;
                this.setFieldValue(mainConnectionType, 'ACTION');

                const method = this.getFieldValue('SETTER');
                const [methodName, argType, returnType] = method ? method.split(';') : ['', '', ''];
                const argTypes = argType ? argType.split(',') : null;
                this.getInput('VALUE').setCheck(argTypes);
            });
        },
    }
}