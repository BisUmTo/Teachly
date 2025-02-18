function customBlocksToolbox() {
    return {
        "kind": "category",
        "name": "Rewards",
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
                        "type": "reward_procedure",
                    },
                    {
                        "kind": "block",
                        "type": "reward_variable",
                        "fields": {
                            "VARIABLE": "event.getPlayer();;org.bukkit.entity.Player"
                        }
                    },
                    {
                        "kind": "block",
                        "type": "reward_variable",
                        "fields": {
                            "VARIABLE": "event.getExercise().getSolutions();;Array"
                        }
                    },
                    {
                        "kind": "block",
                        "type": "reward_variable",
                        "fields": {
                            "VARIABLE": "event.getAnswer();;String"
                        }
                    },
                    {
                        "kind": "block",
                        "type": "reward_variable",
                        "fields": {
                            "VARIABLE": "event.getExercise().getSolutions()[0];;String"
                        }
                    },
                    {
                        "kind": "block",
                        "type": "reward_variable",
                        "fields": {
                            "VARIABLE": "event.getExercise().getHints();;Array"
                        }
                    }
               ]
            },
            {
                "kind": "category",
                "name": "Examples",
                "category_style": "variable_dynamic_blocks",
                "contents": [
                    {
                        "kind": "label",
                        "text": "... print the correct answer"
                    },
                    {
                        "kind": "block",
                        "type": "reward_procedure",
                        "inputs": {
                            "STATEMENT": {
                                "block": {
                                    "type": "variables_set",
                                    "fields": {
                                        "VAR": {
                                            "name": "correct"
                                        }
                                    },
                                    "inputs": {
                                        "VALUE": {
                                            "block": {
                                                "type": "logic_compare",
                                                "fields": {
                                                    "OP": "NEQ"
                                                },
                                                "inputs": {
                                                    "A": {
                                                        "block": {
                                                            "type": "lists_indexOf",
                                                            "fields": {
                                                                "END": "FIRST"
                                                            },
                                                            "inputs": {
                                                                "VALUE": {
                                                                    "block": {
                                                                        "type": "reward_variable",
                                                                        "fields": {
                                                                            "VARIABLE": "solutions"
                                                                        }
                                                                    }
                                                                },
                                                                "FIND": {
                                                                    "block": {
                                                                        "type": "reward_variable",
                                                                        "fields": {
                                                                            "VARIABLE": "response"
                                                                        }
                                                                    }
                                                                }
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
                                        }
                                    },
                                    "next": {
                                        "block": {
                                            "type": "controls_if",
                                            "inputs": {
                                                "IF0": {
                                                    "block": {
                                                        "type": "logic_negate",
                                                        "inputs": {
                                                            "BOOL": {
                                                                "block": {
                                                                    "type": "variables_get",
                                                                    "fields": {
                                                                        "VAR": {
                                                                            "name": "correct"
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                },
                                                "DO0": {
                                                    "block": {
                                                        "type": "text_print",
                                                        "inputs": {
                                                            "TEXT": {
                                                                "shadow": {
                                                                    "type": "text",
                                                                    "fields": {
                                                                        "TEXT": "abc"
                                                                    }
                                                                },
                                                                "block": {
                                                                    "type": "text_join",
                                                                    "extraState": {
                                                                        "itemCount": 2
                                                                    },
                                                                    "inputs": {
                                                                        "ADD0": {
                                                                            "block": {
                                                                                "type": "text",
                                                                                "fields": {
                                                                                    "TEXT": "Correct answer was: "
                                                                                }
                                                                            }
                                                                        },
                                                                        "ADD1": {
                                                                            "block": {
                                                                                "type": "reward_variable",
                                                                                "fields": {
                                                                                    "VARIABLE": "solutions[0]"
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