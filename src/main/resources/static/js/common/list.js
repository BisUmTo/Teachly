document.addEventListener("DOMContentLoaded", () => {
    let datatable = $("#list");
    if (datatable.length && $.fn.DataTable) {
        if ($.fn.DataTable.isDataTable(datatable)) {
            datatable.DataTable().destroy();
        }
        datatable.DataTable({
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": true,
            "info": true,
            "autoWidth": false,
            "responsive": true,
            "columnDefs": [
                {
                    "orderable": false,
                    "targets": -1,
                    "autoWidth": false,
                    "width": "1px"
                }
            ]
        });
        console.info("DataTables inizializzato.");
    }
});
