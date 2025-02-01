document.addEventListener("DOMContentLoaded", () => {
    const button = $('#delete-button');
    button.on('click', (event) => {
         if(confirm('Are you sure you want to delete this trigger?')) {
              $.ajax({
                url: button.data('delete-url'),
                method: 'delete',
                success: () => {
                    window.location.href = window.location.href.split('/').slice(0, -2).join('/');
                }
              });
         }
         event.preventDefault();
   });

});