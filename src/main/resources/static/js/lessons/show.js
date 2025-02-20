var GENERATED = null;

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
                GENERATED = data;
                $('#download').removeClass('disabled');
            }
        });
        event.preventDefault();
    });

    $('#downloadModal').on('shown.bs.modal', function () {
        codeMirrorEditor.refresh();
    });

    $('#download,#download1').on('click', (event) => {
        save(GENERATED.name + '.js', GENERATED.blocklyGeneratedCode);
        $('#downloadModal').modal('hide');
        event.preventDefault();
    });
});

function save(filename, data) {
    const blob = new Blob([data], {type: 'text/csv'});
    if(window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveBlob(blob, filename);
    }
    else{
        const elem = window.document.createElement('a');
        elem.href = window.URL.createObjectURL(blob);
        elem.download = filename;
        document.body.appendChild(elem);
        elem.click();
        document.body.removeChild(elem);
    }
}