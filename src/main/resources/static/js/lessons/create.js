document.addEventListener("DOMContentLoaded", () => {
    initializeTags();

    let tagifyOptions = {
        delimiters: ",|\n|\r",
        tagTextProp: 'name',
        skipInvalid: true,
        enforceWhitelist: true,
        whitelist: [],
        dropdown: {
            enabled: 0,
            maxItems: 10,
            searchKeys: ['name', 'tags'],
            mapValueTo: 'name',
        },
    };

    // TRIGGERS
    const triggersInput = document.getElementById('triggers');
    const triggersTagify = new Tagify(triggersInput, tagifyOptions);
    (async () => {
        const existingTriggers = (await fetchTriggers()).map(e => {
            return {value: e.id, id: e.id, name: e.name, tags: e.tags.join(' ')};
        });
        triggersTagify.settings.whitelist.push(...existingTriggers);
        triggersTagify.dropdown.refilter();
    })();

    // EXERCISES
    const exercisesInput = document.getElementById('exercises');
    const exercisesTagify = new Tagify(exercisesInput, tagifyOptions);
    (async () => {
        const existingExercises = (await fetchExercises()).map(e => {
            return {value: e.id, id: e.id, name: e.name, tags: e.tags.join(' ')};
        });
        exercisesTagify.settings.whitelist.push(...existingExercises);
        exercisesTagify.dropdown.refilter();
    })();


    // REWARDS
    const correctRewardInput = document.getElementById('correct-reward');
    const wrongRewardInput = document.getElementById('wrong-reward');
    tagifyOptions.mode = "select";

    const correctRewardTagify = new Tagify(correctRewardInput, tagifyOptions);
    const wrongRewardTagify = new Tagify(wrongRewardInput, tagifyOptions);
    (async () => {
        const existingRewards = (await fetchRewards()).map(e => {
            return {value: e.id, id: e.id, name: e.name, tags: e.tags.join(' ')};
        });
        correctRewardTagify.settings.whitelist.push(...existingRewards);
        correctRewardTagify.dropdown.refilter();
        wrongRewardTagify.settings.whitelist.push(...existingRewards);
        wrongRewardTagify.dropdown.refilter();
    })();

    console.info('Creation page loaded');
});

API_TAG_FETCH_URL = '/api/v1/lessons/tags';

async function fetchTriggers() {
    try {
        const response = await fetch('/api/v1/triggers');
        if (!response.ok) {
            throw new Error("Error while getting triggers");
        }
        return await response.json();
    } catch (error) {
        console.error("Error while loading triggers:", error);
        return [];
    }
}

async function fetchExercises() {
    try {
        const response = await fetch('/api/v1/exercises');
        if (!response.ok) {
            throw new Error("Error while getting exercises");
        }
        return await response.json();
    } catch (error) {
        console.error("Error while loading exercises:", error);
        return [];
    }
}

async function fetchRewards() {
    try {
        const response = await fetch('/api/v1/rewards');
        if (!response.ok) {
            throw new Error("Error while getting rewards");
        }
        return await response.json();
    } catch (error) {
        console.error("Error while loading rewards:", error);
        return [];
    }
}