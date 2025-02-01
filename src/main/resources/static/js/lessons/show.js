document.addEventListener("DOMContentLoaded", () => {
    let codeMirrorEditor = CodeMirror.fromTextArea(document.getElementById("generatedCode"), {
        //lineNumbers: true,
        mode: "javascript",
        theme: "monokai",
        readOnly: true
    });

    $('#generate-button').on('click', (event) => {
        $.ajax({
            url: $('#generate-button').data('generate-url'),
            method: 'post',
            success: (data) => {
                codeMirrorEditor.setValue(data.blocklyGeneratedCode);
                $('#downloadModal').modal('show');
            }
        });
        event.preventDefault();
    });

    $('#downloadModal').on('shown.bs.modal', function () {
        codeMirrorEditor.refresh();
    });
});
