document.addEventListener("DOMContentLoaded", () => {
    initializeTags();
    initializeCodeArea({
        includeGameActions: true,
        includeGameEvents: true
    })
});

API_TAG_FETCH_URL = '/api/v1/triggers/tags';
