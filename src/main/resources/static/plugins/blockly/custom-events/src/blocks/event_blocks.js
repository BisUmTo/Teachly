import events from './events.json'

export const blocks = [
  {
    "type": "event_procedure",
    "tooltip": "",
    "helpUrl": "",
    "message0": "on %1 \n %2",
    "args0": [
      {
        "type": "field_dropdown",
        "name": "EVENT",
        "options": events['events']
      },
      {
        "type": "input_statement",
        "name": "STATEMENT"
      }
    ],
    "inputsInline": false,
    "style": 'hat_blocks'
  },
  {
    "type": "event_getter",
    "tooltip": "",
    "helpUrl": "",
    "message0": "from %1 get %2 %3",
    "args0": [
      {
        "type": "field_label_serializable",
        "text": "",
        "name": "EVENT"
      },
      {
        "type": "field_dependent_dropdown",
        "name": "GETTER",
        "parentName": 'EVENT',
        "optionMapping" : events['getters']
      },
      {
        "type": "input_end_row",
        "name": "END-ROW"
      }
    ],
    "output": null,
    "style":"variable_dynamic_blocks",
    "extensions": ['event_auto_field_extension']
  },
  {
    "type": "event_boolean_getter",
    "tooltip": "",
    "helpUrl": "",
    "message0": "is %1 %2 %3",
    "args0": [
      {
        "type": "field_label_serializable",
        "text": "",
        "name": "EVENT"
      },
      {
        "type": "field_dependent_dropdown",
        "name": "GETTER",
        "parentName": 'EVENT',
        "optionMapping" : events['boolean_getters']
      },
      {
        "type": "input_end_row",
        "name": "END-ROW"
      }
    ],
    "output": "Boolean",
    "style": "logic_blocks",
    "extensions": ['event_auto_field_extension']
  },
  {
    "type": "event_setter",
    "tooltip": "",
    "helpUrl": "",
    "message0": "set %1 's %2 to %3",
    "args0": [
      {
        "type": "field_label_serializable",
        "text": "click",
        "name": "EVENT"
      },
      {
        "type": "field_dependent_dropdown",
        "name": "SETTER",
        "parentName": 'EVENT',
        "optionMapping": events['setters'] 
      },
      {
        "type": "input_value",
        "name": "VALUE"
      }
    ],
    "previousStatement": null,
    "nextStatement": null,
    "style":"variable_dynamic_blocks",
    "extensions": ['event_auto_field_extension']
  }
]
