document.addEventListener("DOMContentLoaded", () => {
    initializeTags();
    initializeCodeArea({
        includeGameActions: true,
        includeGameEvents: true
    })
});

API_TAG_FETCH_URL = '/api/v1/triggers/tags';
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