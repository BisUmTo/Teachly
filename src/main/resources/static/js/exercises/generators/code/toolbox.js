function customBlocksToolbox() {
    return {
        "kind": "category",
        "name": "Exercise Generator",
        "category_style": "variable_dynamic_blocks",
        "expanded": "true",
        "contents": [
            {
                "kind": "category",
                "name": "Blocks",
                "contents": [
                    {
                        "kind": "block",
                        "type": "generator_procedure",
                    },
                    {
                        "kind": "block",
                        "type": "generate_question"
                    }
                ]
            },
            {
                "kind": "category",
                "name": "Examples",
                "contents": [
                    {
                        "kind": "label",
                        "text": "Multiples of number 3 exercises"
                    },
                    {
                        "kind": "block",
                        "type": "generator_procedure",
                        "inputs": {
                            "STATEMENT": {
                                "block": {
                                    "type": "controls_repeat_ext",
                                    "inputs": {
                                        "TIMES": {
                                            "shadow": {
                                                "type": "math_number",
                                                "fields": {
                                                    "NUM": 10
                                                }
                                            }
                                        },
                                        "DO": {
                                            "block": {
                                                "type": "variables_set",
                                                "fields": {
                                                    "VAR": {
                                                        "name": "a"
                                                    }
                                                },
                                                "inputs": {
                                                    "VALUE": {
                                                        "block": {
                                                            "type": "math_random_int",
                                                            "inputs": {
                                                                "FROM": {
                                                                    "shadow": {
                                                                        "type": "math_number",
                                                                        "fields": {
                                                                            "NUM": 1
                                                                        }
                                                                    }
                                                                },
                                                                "TO": {
                                                                    "shadow": {
                                                                        "type": "math_number",
                                                                        "fields": {
                                                                            "NUM": 98
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
                                                                    "type": "math_number_property",
                                                                    "extraState": "<mutation divisor_input=\"true\"></mutation>",
                                                                    "fields": {
                                                                        "PROPERTY": "DIVISIBLE_BY"
                                                                    },
                                                                    "inputs": {
                                                                        "NUMBER_TO_CHECK": {
                                                                            "shadow": {
                                                                                "type": "math_number",
                                                                                "fields": {
                                                                                    "NUM": 0
                                                                                }
                                                                            },
                                                                            "block": {
                                                                                "type": "variables_get",
                                                                                "fields": {
                                                                                    "VAR": {
                                                                                        "name": "a"
                                                                                    }
                                                                                }
                                                                            }
                                                                        },
                                                                        "DIVISOR": {
                                                                            "block": {
                                                                                "type": "math_number",
                                                                                "fields": {
                                                                                    "NUM": 3
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            },
                                                            "DO0": {
                                                                "block": {
                                                                    "type": "math_change",
                                                                    "fields": {
                                                                        "VAR": {
                                                                            "name": "a"
                                                                        }
                                                                    },
                                                                    "inputs": {
                                                                        "DELTA": {
                                                                            "shadow": {
                                                                                "type": "math_number",
                                                                                "fields": {
                                                                                    "NUM": 1
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        "next": {
                                                            "block": {
                                                                "type": "variables_set",
                                                                "fields": {
                                                                    "VAR": {
                                                                        "name": "b"
                                                                    }
                                                                },
                                                                "inputs": {
                                                                    "VALUE": {
                                                                        "block": {
                                                                            "type": "math_random_int",
                                                                            "inputs": {
                                                                                "FROM": {
                                                                                    "shadow": {
                                                                                        "type": "math_number",
                                                                                        "fields": {
                                                                                            "NUM": 1
                                                                                        }
                                                                                    }
                                                                                },
                                                                                "TO": {
                                                                                    "shadow": {
                                                                                        "type": "math_number",
                                                                                        "fields": {
                                                                                            "NUM": 98
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
                                                                                    "type": "math_number_property",
                                                                                    "extraState": "<mutation divisor_input=\"true\"></mutation>",
                                                                                    "fields": {
                                                                                        "PROPERTY": "DIVISIBLE_BY"
                                                                                    },
                                                                                    "inputs": {
                                                                                        "NUMBER_TO_CHECK": {
                                                                                            "shadow": {
                                                                                                "type": "math_number",
                                                                                                "fields": {
                                                                                                    "NUM": 0
                                                                                                }
                                                                                            },
                                                                                            "block": {
                                                                                                "type": "variables_get",
                                                                                                "fields": {
                                                                                                    "VAR": {
                                                                                                        "name": "b"
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        },
                                                                                        "DIVISOR": {
                                                                                            "block": {
                                                                                                "type": "math_number",
                                                                                                "fields": {
                                                                                                    "NUM": 3
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            },
                                                                            "DO0": {
                                                                                "block": {
                                                                                    "type": "math_change",
                                                                                    "fields": {
                                                                                        "VAR": {
                                                                                            "name": "b"
                                                                                        }
                                                                                    },
                                                                                    "inputs": {
                                                                                        "DELTA": {
                                                                                            "shadow": {
                                                                                                "type": "math_number",
                                                                                                "fields": {
                                                                                                    "NUM": -1
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        },
                                                                        "next": {
                                                                            "block": {
                                                                                "type": "variables_set",
                                                                                "fields": {
                                                                                    "VAR": {
                                                                                        "name": "c"
                                                                                    }
                                                                                },
                                                                                "inputs": {
                                                                                    "VALUE": {
                                                                                        "block": {
                                                                                            "type": "math_arithmetic",
                                                                                            "fields": {
                                                                                                "OP": "MULTIPLY"
                                                                                            },
                                                                                            "inputs": {
                                                                                                "A": {
                                                                                                    "shadow": {
                                                                                                        "type": "math_number",
                                                                                                        "fields": {
                                                                                                            "NUM": 3
                                                                                                        }
                                                                                                    }
                                                                                                },
                                                                                                "B": {
                                                                                                    "shadow": {
                                                                                                        "type": "math_number",
                                                                                                        "fields": {
                                                                                                            "NUM": 1
                                                                                                        }
                                                                                                    },
                                                                                                    "block": {
                                                                                                        "type": "math_random_int",
                                                                                                        "inputs": {
                                                                                                            "FROM": {
                                                                                                                "shadow": {
                                                                                                                    "type": "math_number",
                                                                                                                    "fields": {
                                                                                                                        "NUM": 1
                                                                                                                    }
                                                                                                                }
                                                                                                            },
                                                                                                            "TO": {
                                                                                                                "shadow": {
                                                                                                                    "type": "math_number",
                                                                                                                    "fields": {
                                                                                                                        "NUM": 33
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                },
                                                                                "next": {
                                                                                    "block": {
                                                                                        "type": "generate_question",
                                                                                        "fields": {
                                                                                            "TYPE": "MULTIPLE_CHOICE",
                                                                                            "DIFFICULTY": "EASY",
                                                                                            "HINTS_LABEL": "- Options:"
                                                                                        },
                                                                                        "inputs": {
                                                                                            "QUESTION": {
                                                                                                "block": {
                                                                                                    "type": "text",
                                                                                                    "fields": {
                                                                                                        "TEXT": "Quale dei seguenti numeri è un multiplo di 3?"
                                                                                                    }
                                                                                                }
                                                                                            },
                                                                                            "HINTS": {
                                                                                                "block": {
                                                                                                    "type": "lists_sort",
                                                                                                    "fields": {
                                                                                                        "TYPE": "NUMERIC",
                                                                                                        "DIRECTION": "1"
                                                                                                    },
                                                                                                    "inputs": {
                                                                                                        "LIST": {
                                                                                                            "block": {
                                                                                                                "type": "lists_create_with",
                                                                                                                "extraState": {
                                                                                                                    "itemCount": 3
                                                                                                                },
                                                                                                                "inputs": {
                                                                                                                    "ADD0": {
                                                                                                                        "block": {
                                                                                                                            "type": "variables_get",
                                                                                                                            "fields": {
                                                                                                                                "VAR": {
                                                                                                                                    "name": "a"
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    },
                                                                                                                    "ADD1": {
                                                                                                                        "block": {
                                                                                                                            "type": "variables_get",
                                                                                                                            "fields": {
                                                                                                                                "VAR": {
                                                                                                                                    "name": "b"
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    },
                                                                                                                    "ADD2": {
                                                                                                                        "block": {
                                                                                                                            "type": "variables_get",
                                                                                                                            "fields": {
                                                                                                                                "VAR": {
                                                                                                                                    "name": "c"
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            },
                                                                                            "SOLUTIONS": {
                                                                                                "block": {
                                                                                                    "type": "variables_get",
                                                                                                    "fields": {
                                                                                                        "VAR": {
                                                                                                            "name": "c"
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