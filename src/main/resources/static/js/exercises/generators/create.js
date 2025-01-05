document.addEventListener("DOMContentLoaded", () => {
    initializeTags();
    initializeCodeArea({
        includeGeneratorBlocks: true
    })
});

API_TAG_FETCH_URL = '/api/v1/exercises/generators/tags';
VALIDATE_OPTIONS = {
    rules: {
        name: {
            required: true,
            minlength: 5,
            maxlength: 255,
        },
        description: {
            required: false,
            maxlength: 255,
        }
    }
};
