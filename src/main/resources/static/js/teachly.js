$(document).ready(function() {
    // Ottieni il path dalla URL corrente
    let path = window.location.pathname;

    if (path.startsWith("/")) {
        path = path.substring(1);
    }

    let segments = path.split("/");

    // Se l'ultimo segmento è un UUID, rimuovilo
    let uuidRegex = /^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$/i;
    if (uuidRegex.test(segments[segments.length - 1])) {
        segments.pop();
    }
    // segments.pop();

    let $breadcrumb = $("#breadcrumb");
    let url = "";
    $.each(segments, function(index, segment) {
        url += "/" + segment;
        let $li = $("<li>").addClass("breadcrumb-item");

        if (index === segments.length - 1) {
            // L'ultimo elemento è l'elemento attivo
            $li.addClass("active").attr("aria-current", "page").text(capitalize(segment));
        } else {
            // Aggiungi un link per gli altri segmenti
            let $a = $("<a>").attr("href", url).text(capitalize(segment));
            $li.append($a);
        }

        // Aggiungi l'elemento al breadcrumb
        $breadcrumb.append($li);
    });

    $('.nav-link').each(function () {
        // Verifica se l'href del link corrisponde al percorso attuale
        if ($(this).attr('href').endsWith(path)) {
            // Aggiungi la classe "active" al link
            $(this).addClass('active');

            // Aggiungi la classe "menu-open" al genitore se necessario
            $(this).closest('.nav-item').addClass('menu-open');
        }
    });

    // Funzione per capitalizzare la prima lettera di un segmento
    function capitalize(str) {
        return str.charAt(0).toUpperCase() + str.slice(1);
    }

    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    });

    /*$('.list-element[data-element-id]').on("click", function () {
        const id = $(this).data("element-id");
        window.location.href = window.location.pathname + `/show/${id}`;
    });*/
});