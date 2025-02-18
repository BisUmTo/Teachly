async function gameActionsLoader(json_path) {
    const actions = await fetchJson(json_path);
    const json = [
        {
            "type": "action_getter",
            "tooltip": "",
            "helpUrl": "",
            "message0": "from %1 %2 get %3 %4",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "ACTION",
                    "options": actions['actions']
                },
                {
                    "type": "input_value",
                    "name": "OBJECT"
                },
                {
                    "type": "field_dependent_dropdown",
                    "name": "GETTER",
                    "parentName": 'ACTION',
                    "optionMapping": actions['getters']
                },
                {
                    "type": "input_end_row",
                    "name": "END-ROW"
                }
            ],
            "output": null,
            "style":"variable_dynamic_blocks",
            "extensions": []
        },
        {
            "type": "action_boolean_getter",
            "tooltip": "",
            "helpUrl": "",
            "message0": "is %1 %2 %3 %4",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "ACTION",
                    "options": actions['actions']
                },
                {
                    "type": "input_value",
                    "name": "OBJECT"
                },
                {
                    "type": "field_dependent_dropdown",
                    "name": "GETTER",
                    "parentName": 'ACTION',
                    "optionMapping": actions['getters']
                },
                {
                    "type": "input_end_row",
                    "name": "END-ROW"
                }
            ],
            "output": null,
            "style":"variable_dynamic_blocks",
            "extensions": []
        },
        {
            "type": "action_setter",
            "tooltip": "",
            "helpUrl": "",
            "message0": "set %1 %2's %3 %4 to %5",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "ACTION",
                    "options": actions['actions']
                },
                {
                    "type": "input_value",
                    "name": "OBJECT"
                },
                {
                    "type": "input_end_row",
                    "name": "END-ROW"
                },
                {
                    "type": "field_dependent_dropdown",
                    "name": "SETTER",
                    "parentName": 'ACTION',
                    "optionMapping": actions['setters']
                },
                {
                    "type": "input_value",
                    "name": "VALUE"
                }
            ],
            "previousStatement": null,
            "nextStatement": null,
            "style":"variable_dynamic_blocks",
            "extensions": []
        }
    ]
    loadBlocksFromJson(json)
}