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
    // TOFIX: UUID replacement

    console.info('Creation page loaded');
});

API_TAG_FETCH_URL = '/api/v1/lessons/tags';
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
        },
        triggers: {
            required: true,
        },
        exercises: {
            required: true,
        },
        correctReward: {
            required: true,
        },
        wrongReward: {
            required: true,
        },
        explanation: {
            required: false,
            maxlength: 255,
        }
    }
};

async function fetchTriggers() {
    return await fetchJson('/api/v1/triggers')
}

async function fetchExercises() {
    return await fetchJson('/api/v1/exercises')
}

async function fetchRewards() {
    return await fetchJson('/api/v1/rewards')
}