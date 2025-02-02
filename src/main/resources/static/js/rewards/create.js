document.addEventListener("DOMContentLoaded", () => {
    initializeTags();
    initializeCodeArea({
        includeGameActions: true
    })
});

function initializeJson() {
    let workspace = Blockly.getMainWorkspace();
    let code = Blockly.JavaScript.workspaceToCode(workspace);
    return {
        'blocklyGeneratedCode': code
    };
}

API_TAG_FETCH_URL = '/api/v1/rewards/tags';
VALIDATE_OPTIONS = {
    rules: {
        name: {
            required: true,
            minlength: 1,
            maxlength: 255,
        },
        description: {
            required: false,
            maxlength: 255,
        }
    }
};