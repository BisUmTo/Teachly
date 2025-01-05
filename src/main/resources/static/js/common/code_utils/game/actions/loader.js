async function gameActionsLoader(json_path) {
    const actions = await fetchJson(json_path);
    const json = [
        // TODO: Implement the game actions loader
    ]
    loadBlocksFromJson(json)
}