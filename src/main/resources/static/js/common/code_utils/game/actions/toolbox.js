function gameActionsToolbox() {
    return {
        "kind": "category",
        "name": "Actions",
        "category_style": "mutex_category",
        "contents" : [
            {
                "kind": "block",
                "type": "action_getter",
                "fields": {
                    "ACTION": ""
                }
            },
            {
                "kind": "block",
                "type": "action_boolean_getter",
                "fields": {
                    "ACTION": ""
                }
            },
            {
                "kind": "block",
                "type": "action_setter",
                "fields": {
                    "ACTION": ""
                }
            }
        ]
    }
}