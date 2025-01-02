document.addEventListener("DOMContentLoaded", () => {
    const cancelButton = $('#cancel-button');
    cancelButton.on('click', () => {
        window.history.back();
    });

    const saveButton = $('#save-button');
    saveButton.on('click', sendForm);
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
            json[key] = JSON.parse($(element).val()).map(tag => tag.value);
        } else {
            json[key] = $(element).val();
        }
    });
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
            window.location.href = "../show/" + id;
        })
        .catch(error => {
            console.error("Error:", error); // Log the error to the console for debugging
            alert("An error occurred: " + error.message); // Show a user-friendly message
        });
}