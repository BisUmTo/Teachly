async function customBlocksLoader(json_path) {
    const json = [
        {
            "type": "assign_random_exercise",
            "tooltip": "",
            "helpUrl": "",
            "message0": "assign random exercise to %1",
            "args0": [
                {
                    "type": "input_value",
                    "name": "PLAYER",
                    "check": "org.bukkit.entity.Player"
                }
            ],
            "previousStatement": null,
            "nextStatement": null,
            "colour": 345,
        }
    ]
    loadBlocksFromJson(json)
}