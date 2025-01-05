document.addEventListener("DOMContentLoaded", () => {
    const cancelButton = $('#cancel-button');
    cancelButton.on('click', () => {
        window.history.back();
    });

    const saveButton = $('#save-button');
    saveButton.on('click', sendForm);

    for (let key in clone) {
        $('[data-json-key="' + key + '"]').val(clone[key]);
    }

    jQuery.validator.setDefaults({
        debug: true,
        success: "valid"
    });

    $('form').validate(VALIDATE_OPTIONS);
});

function sendForm(){
    const form = document.querySelector('form');
    const formData = new FormData(form);
    const url = form.action;
    const method = form.method;

    // Converti i dati del form in URLSearchParams, data-json-key come chiave dei campi JSON, ma non quelli nascosti
    const json = {};
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

    console.warn(json);

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json' // Crucial: Tell the server it's JSON
        },
        body: JSON.stringify(json) // Crucial: Stringify the JSON object
    })
        .then(response => {
            if (!response.ok) {
                // Handle errors more gracefully.  Check the status code
                if (response.status === 400) { // Example: Bad Request
                    return response.json().then(errorData => {
                        // Display specific error messages from the server
                        alert("Bad Request: " + errorData.message || "Please check your input.");
                    });
                } else {
                    return response.text().then(text => {
                        alert("Error: " + response.status + " - " + text);
                    });
                }
            }
            return response.json(); // Parse the JSON response from the server
        })
        .then(data => {
            // Handle the successful response data
            console.log("Success:", data);
            let id = data.id;
            let currentUrl = window.location.href;
            window.location.href = currentUrl.substring(0, currentUrl.lastIndexOf('/')) + '/show/' + id;
        })
        .catch(error => {
            console.error("Error:", error); // Log the error to the console for debugging
            alert("An error occurred: " + error.message); // Show a user-friendly message
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

let API_TAG_FETCH_URL = '';
let VALIDATE_OPTIONS = {rules:{}}
