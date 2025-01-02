document.addEventListener("DOMContentLoaded", () => {
    const codeMirrorEditor = CodeMirror.fromTextArea(document.getElementById("generatedCode"), {
        lineNumbers: true,
        mode: "javascript",
        theme: "default",
        readOnly: true
    });

    $('#downloadModal').on('shown.bs.modal', function () {
        codeMirrorEditor.refresh();
    });
});