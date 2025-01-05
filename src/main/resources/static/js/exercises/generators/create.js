document.addEventListener("DOMContentLoaded", () => {
    initializeTags();
    initializeCodeArea({
        includeGeneratorBlocks: true
    })
});

API_TAG_FETCH_URL = '/api/v1/exercises/generators/tags';
