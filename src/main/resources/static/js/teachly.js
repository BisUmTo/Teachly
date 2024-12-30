$(document).ready(function() {
    initializePage()

    barba.init({
        transitions: [{
            name: 'default-transition',
            leave(data) {
            },
            enter(data) {
            },
            after() {
                initializePage();
                window.dispatchEvent(new Event('resize'));
            }
        }]
    });
});

function initializePage(){
    updateBreadcrumb();
    updateNavLinks();
    updateDataTable();

    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    });
}

function updateBreadcrumb() {
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
}

function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

function updateNavLinks(){
    let path = window.location.pathname;
    $('.nav-link').each(function () {
        // Verifica se l'href del link corrisponde al percorso attuale
        if ($(this).attr('href').endsWith(path)) {
            $(this).addClass('active');
            $(this).closest('.nav-item').addClass('menu-open');
        } else {
            $(this).removeClass('active');
            $(this).closest('.nav-item').removeClass('menu-open');
        }
    });
}

function updateDataTable() {
    $("#list").DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
        "columnDefs": [
            { "orderable": false, "targets": -1 }
        ]
    });
}