document.addEventListener("DOMContentLoaded", () => {
    const cancelButton = $('#cancel-button');
    cancelButton.on('click', (event) => {
        window.history.back();
        event.preventDefault();
    });

    jQuery.validator.setDefaults({
        //debug: true,
        success: "valid",
        submitHandler: (form, e)=> {
            e.preventDefault();
            sendForm();
        },
        errorElement: 'span',
        errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
            element.closest('.form-group').append(error);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass('is-invalid');
        },
        messages: {
            name: {
                required: "An unique name is required",
                minlength: "A longer name is required",
                maxlength: "Name must be at most 255 characters long"
            },
            description: {
                maxlength: "Description must be at most 255 characters long"
            },
            triggers: {
                required: "At least one trigger is required"
            },
            exercises: {
                required: "At least one exercise is required"
            },
            correctReward: {
                required: "A correct reward is required"
            },
            wrongReward: {
                required: "A wrong reward is required"
            },
            explanation: {
                maxlength: "Explanation must be at most 255 characters long, use external links to store more information"
            },
        },
        ignore: '.tagify__tag, .tagify__input'
    });

    $('#create-form').validate(VALIDATE_OPTIONS);
    initializeClone();
    // TOFIX: schedule
    setTimeout(() => {
        initializeClone();
    }, 1000);
});

function sendForm() {
    const form = document.querySelector('form');

    // Converti i dati del form in URLSearchParams, data-json-key come chiave dei campi JSON, ma non quelli nascosti
    const json = initializeJson();
    $(form).find('[data-json-key]:not(:hidden)').each((index, element) => {
        const key = $(element).data('json-key');
        if ($(element).hasClass('tagify-input')) {
            json[key] = JSON.parse($(element).val() || '[]').map(tag => tag.value);
        } else {
            json[key] = $(element).val();
        }
    });

    // se c'è solo una soluzione, la trasformo in array
    if (json['solutions'] && !Array.isArray(json['solutions'])) {
        json['solutions'] = [json['solutions']];
    }


    // Blockly JavaScript
    const blocklyDiv = document.getElementById('blocklyDiv');
    if (blocklyDiv) {
        const workspace = Blockly.getMainWorkspace();
        const blockly_json = Blockly.serialization.workspaces.save(workspace)
        json['blocklyJsonCode'] = JSON.stringify(blockly_json);
    }

    console.info(json);

    fetch(form.action, {
        method: $(form).attr('method'),
        headers: {
            'Content-Type': 'application/json' // Crucial: Tell the server it's JSON
        },
        body: JSON.stringify(json) // Crucial: Stringify the JSON object
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    let body = 'Unknown error';
                    // ERROR: duplicate key value violates unique constraint [...] Key (name)
                    if (error.message.includes('ERROR: duplicate key value violates unique constraint')) {
                        let element = error.message.match(/Key \(([^)]+)\)/)[1];
                        body = `An element with the same ${element} already exists`;

                        // Highlight the field with the error and show the error message
                        $('[data-json-key="' + element + '"]').addClass('is-invalid');
                        $(`#${element}-error`).html(`Already in use`);
                    }

                    $(document).Toasts('create', {
                        class: 'bg-danger',
                        title: error.error,
                        subtitle: error.status,
                        body: body
                    })

                    console.error("Error:", error);
                    throw new Error("Login required");
                });
            }
            return response.json(); // Parse the JSON response from the server
        })
        .then(data => {
            // Handle the successful response data
            console.log("Success:", data);
            let id = data.id;
            let currentUrl = window.location.href;
            currentUrl = currentUrl.replace(/\/[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}/, '');
            window.location.href = currentUrl.substring(0, currentUrl.lastIndexOf('/')) + '/show/' + id;
        })
        .catch(error => {
            if (error.message === "Login required") {
                window.open('/login', '_blank');
            }
        });
}

function initializeTags() {
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
        const existingTags = await fetchJson(API_TAG_FETCH_URL);
        tagsTagify.settings.whitelist.push(...existingTags);
        tagsTagify.dropdown.refilter();
    })();
}

function initializeClone(){
    for (let key in clone) {
        let value = clone[key];
        if (Array.isArray(clone[key])) {
            if (typeof clone[key][0] === 'object' && 'id' in clone[key][0]) {
                value = clone[key].map(obj => obj.id);
            }
        } else if (typeof clone[key] === 'object' && 'id' in clone[key]) {
            value = clone[key].id;
        }
        $(`[data-json-key="${key}"]`).val(value);
    }
}

function initializeJson() {
    return {}
}

let API_TAG_FETCH_URL = '';
let VALIDATE_OPTIONS = {rules:{}}
