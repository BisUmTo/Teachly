function customBlocksToolbox() {
    return {
        "kind": "category",
        "name": "Triggers",
        "category_style": "variable_dynamic_blocks",
        "expanded": "true",
        "contents" : [
            {
                "kind": "category",
                "name": "Blocks",
                "category_style": "variable_dynamic_blocks",
                "contents": [
                    {
                        "kind": "block",
                        "type": "assign_random_exercise"
                    }
                ]
            },
            {
                "kind": "category",
                "name": "Examples",
                "category_style": "variable_dynamic_blocks",
                "contents" : [
                    {
                        "kind": "label",
                        "text": "When a player jumps ..."
                    },
                    {
                        "kind": "block",
                        "type": "event_procedure",
                        "fields": {
                            "EVENT": "PlayerJumpEvent"
                        },
                        "inputs": {
                            "STATEMENT": {
                                "block": {
                                    "type": "assign_random_exercise",
                                    "inputs": {
                                        "PLAYER": {
                                            "block": {
                                                "type": "event_getter",
                                                "fields": {
                                                    "EVENT": "PlayerJumpEvent",
                                                    "GETTER": "getPlayer"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    {
                        "kind": "label",
                        "text": "When a player picks up the entire stack of items ..."
                    },
                    {
                        "kind": "block",
                        "type": "event_procedure",
                        "fields": {
                            "EVENT": "PlayerPickupItemEvent"
                        },
                        "inputs": {
                            "STATEMENT": {
                                "block": {
                                    "type": "controls_if",
                                    "inputs": {
                                        "IF0": {
                                            "block": {
                                                "type": "logic_compare",
                                                "fields": {
                                                    "OP": "EQ"
                                                },
                                                "inputs": {
                                                    "A": {
                                                        "block": {
                                                            "type": "event_getter",
                                                            "fields": {
                                                                "EVENT": "PlayerPickupItemEvent",
                                                                "GETTER": "getRemaining"
                                                            }
                                                        }
                                                    },
                                                    "B": {
                                                        "block": {
                                                            "type": "math_number",
                                                            "fields": {
                                                                "NUM": 0
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        "DO0": {
                                            "block": {
                                                "type": "assign_random_exercise",
                                                "inputs": {
                                                    "PLAYER": {
                                                        "block": {
                                                            "type": "event_getter",
                                                            "fields": {
                                                                "EVENT": "PlayerPickupItemEvent",
                                                                "GETTER": "getPlayer"
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                ]
            }
        ]
    }
}