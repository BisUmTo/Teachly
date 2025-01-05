async function customBlocksLoader(json_path) {
    const json = [
        {
            "type": "generator_procedure",
            "tooltip": "",
            "helpUrl": "",
            "message0": "When this generator is runned \n %1",
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
            "type": "generate_question",
            "tooltip": "",
            "helpUrl": "",
            "message0": "generate a  %1 exercise %2 - Difficulty: %3 %4 - Question: %5 %6 %7 %8 %9 - Solution(s): %10",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "TYPE",
                    "options": [
                        [
                            "multiple choice",
                            "MULTIPLE_CHOICE"
                        ],
                        [
                            "open question",
                            "OPEN_QUESTION"
                        ],
                        [
                            "true or false",
                            "TRUE_FALSE"
                        ]
                    ]
                },
                {
                    "type": "input_end_row",
                    "name": ""
                },
                {
                    "type": "field_dropdown",
                    "name": "DIFFICULTY",
                    "options": [
                        [
                            "easy",
                            "EASY"
                        ],
                        [
                            "medium",
                            "MEDIUM"
                        ],
                        [
                            "hard",
                            "HARD"
                        ]
                    ]
                },
                {
                    "type": "input_end_row",
                    "name": ""
                },
                {
                    "type": "input_value",
                    "name": "QUESTION",
                    "check": "String"
                },
                {
                    "type": "input_end_row",
                    "name": ""
                },
                {
                    "type": "field_label_serializable",
                    "text": "- Options:",
                    "name": "HINTS_LABEL"
                },
                {
                    "type": "input_value",
                    "name": "HINTS",
                    "check": "Array"
                },
                {
                    "type": "input_end_row",
                    "name": "HINTS_END_ROW"
                },
                {
                    "type": "input_value",
                    "name": "SOLUTIONS",
                    "check": [
                        "String",
                        "Array"
                    ]
                }
            ],
            "previousStatement": null,
            "nextStatement": null,
            "colour": 360,
            "inputsInline": true,
            "extensions": [
                "generator_auto_field_extension"
            ]
        }
    ]
    loadBlocksFromJson(json)
}