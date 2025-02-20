document.addEventListener("DOMContentLoaded", () => {
    initializeCodeArea({
        includeGeneratorBlocks: true,
        readOnly: true
    })

    const generateButton = $("#generate-button");
    generateButton.click(() => {
        $(this).prop('disabled', true);

        const workspace = Blockly.getMainWorkspace();
        const code = Blockly.JavaScript.workspaceToCode(workspace);
        console.log(code);

        const result = eval("(async () => {" + code + "})()");
        result.then(() => {
            $(this).prop('disabled', false);
            window.location.reload();
        });
    });

    const deleteGeneratedButton = $("#delete-generated-button");
    deleteGeneratedButton.click(() => {
        $(this).prop('disabled', true);

        const generatorId = window.location.pathname.split('/').pop();
        fetch(`/api/v1/exercises/generators/${generatorId}/generated`, {
            method: 'DELETE'
        })
        .then(response => {
            $(this).prop('disabled', false);
            return response.json();
        })
        .then(data => {
            console.log(data);
            window.location.reload();
        })
        .catch(error => console.error('Error:', error));
    });
});