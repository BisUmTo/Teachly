async function customBlocksLoader(json_path) {
    const json = [
        {
            "type": "reward_procedure",
            "tooltip": "",
            "helpUrl": "",
            "message0": "when answer is given \n %1",
            "args0": [
                {
                    "type": "input_statement",
                    "name": "STATEMENT"
                }
            ],
            "inputsInline": false,
            "style": {
                "hat": "cap"
            },
            "colour": 345
        },
        {
            "type": "reward_variable",
            "tooltip": "",
            "helpUrl": "",
            "message0": "%1 %2",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "VARIABLE",
                    "options": [
                        [
                            "response",
                            "response"
                        ],
                        [
                            "solutions",
                            "solutions"
                        ],
                        [
                            "first solution",
                            "solutions[0]"
                        ],
                        [
                            "hints",
                            "hints"
                        ]
                    ]
                },
                {
                    "type": "input_dummy"
                }
            ],
            "output": null,
            "colour": 345,
            "extensions": [
                "reward_auto_field_extension"
            ]
        }
    ]
    loadBlocksFromJson(json)
}