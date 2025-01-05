async function customBlocksLoader(json_path) {
    const json = [
        {
            "type": "assign_random_exercise",
            "tooltip": "",
            "helpUrl": "",
            "message0": "assign random exercise",
            "previousStatement": null,
            "nextStatement": null,
            "colour": 345
        }
    ]
    loadBlocksFromJson(json)
}