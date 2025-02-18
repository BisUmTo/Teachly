async function gameActionsLoader(json_path) {
    const actions = await fetchJson(json_path);
    const json = [
        {
            "type": "action_getter",
            "tooltip": "",
            "helpUrl": "",
            "message0": "%1from %2 get %3 %4",
            "args0": [
                {
                    "type": "field_label_serializable",
                    "name": "ACTION",
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
            "style":"colour_blocks",
            "extensions": ['action_getter_extension']
        },
        {
            "type": "action_boolean_getter",
            "tooltip": "",
            "helpUrl": "",
            "message0": "%1is %2 %3 %4",
            "args0": [
                {
                    "type": "field_label_serializable",
                    "name": "ACTION",
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
            "style": "logic_blocks",
            "extensions": ['action_boolean_getter_extension']
        },
        {
            "type": "action_setter",
            "tooltip": "",
            "helpUrl": "",
            "message0": "%1set %2's %3 %4 to %5",
            "args0": [
                {
                    "type": "field_label_serializable",
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
            "style":"colour_blocks",
            "extensions": ['action_setter_extension']
        }
    ]
    loadBlocksFromJson(json)
}