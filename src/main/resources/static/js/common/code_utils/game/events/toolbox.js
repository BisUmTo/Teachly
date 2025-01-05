function gameEventsToolbox() {
    return {
        "kind": "category",
        "name": "Events",
        "category_style": "mutex_category",
        "contents" : [
            {
                "kind": "block",
                "type": "event_procedure"
            },
            {
                "kind": "block",
                "type": "event_getter",
                "fields": {
                    "EVENT": ""
                }
            },
            {
                "kind": "block",
                "type": "event_boolean_getter",
                "fields": {
                    "EVENT": ""
                }
            },
            {
                "kind": "block",
                "type": "event_setter",
                "fields": {
                    "EVENT": ""
                }
            }
        ]
    }
}