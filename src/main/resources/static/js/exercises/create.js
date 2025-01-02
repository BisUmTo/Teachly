document.addEventListener("DOMContentLoaded", () => {

    // TAGS
    const tagsInput = document.querySelector('#tags');
    const tagsTagify = new Tagify(tagsInput, {
        delimiters: ",|\n|\r",
        whitelist: [],
        enforceWhitelist: false, // Permetti di aggiungere nuove tag
        dropdown: {
            enabled: 0,
            maxItems: 10,
            closeOnSelect: false
        }
    });
    (async () => {
        const existingTags = await fetchTags();
        tagsTagify.settings.whitelist.push(...existingTags);
        tagsTagify.dropdown.refilter();
    })();


    // QUESTION
    const questionDetails = $('.question-details');
    const typeInput = $('#type');
    typeInput.on('change',()=>{
        questionDetails.hide();
        questionDetails.filter(function() {
            return $(this).data("type-value") === typeInput.val()
        }).show();
    });
    typeInput.change();

    const tagifyOptions = {delimiters: "\n|\r"};

    // MUTLIPLE CHOICE
    const optionsInput = document.querySelector('#MULTIPLE_CHOICE_options');
    const solutionsInput = document.querySelector('#MULTIPLE_CHOICE_solutions');

    const optionsTagify = new Tagify(optionsInput, tagifyOptions);
    const solutionsTagify = new Tagify(solutionsInput, tagifyOptions);

    // Aggiorna le opzioni quando vengono aggiunte soluzioni
    solutionsTagify.on('add', (e) => {
        const addedSolution = e.detail.data.value;

        const options = optionsTagify.value.map(tag => tag.value);
        if (!options.includes(addedSolution)) {
            optionsTagify.addTags([addedSolution]);
        }
    });

    // Aggiorna le opzioni quando vengono rimosse opzioni
    optionsTagify.on('remove', (e) => {
        const removedOption = e.detail.data.value;

        // Rimuovi l'opzione solo se necessario (se non deve essere permanente)
        const isStillSolution = solutionsTagify.value.some(tag => tag.value === removedOption);
        if (isStillSolution) {
            solutionsTagify.removeTags(removedOption);
        }
    });

    // OPEN QUESTION
    const hintsInput = document.querySelector('#OPEN_QUESTION_hints');
    const solutions2Input = document.querySelector('#OPEN_QUESTION_solutions');

    const hintsTagify = new Tagify(hintsInput, {delimiters: "\n|\r"});
    const solutions2Tagify = new Tagify(solutions2Input, {delimiters: "\n|\r"});
});

async function fetchTags() {
    try {
        const response = await fetch('/api/v1/exercises/tags');
        if (!response.ok) {
            throw new Error("Error while getting tags");
        }
        return await response.json();
    } catch (error) {
        console.error("Error while loading tags:", error);
        return [];
    }
}