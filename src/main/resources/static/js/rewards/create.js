document.addEventListener("DOMContentLoaded", () => {
    initializeTags();
    initializeCodeArea({
        includeGameActions: true
    })
});

API_TAG_FETCH_URL = '/api/v1/rewards/tags';
