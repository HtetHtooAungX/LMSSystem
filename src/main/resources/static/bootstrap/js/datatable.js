$(document).ready( function () {
	 var table = $('#bookTable').DataTable({
			ajax: "/api/book/find/ss",
			serverSide: true,
			processing: true,
			columns: [
                {
                    data: 'id'
                },
                {
                    data: 'name'
                },
                {
                    data: 'author'
                },
                {
                	name: 'Action',
                    data: 'id',
                    orderable: false,
                    render: function (data) { 
                    	return '<a class="btn btn-info btn-sm me-2" href="/book/view/' + data + '">' + 'Edit' + '</a>'; 
                    	}
                },
                {
                	name: 'Delete',
                    data: 'id',
                    orderable: false,
                    render: function (data) {
                    	return `<a class="btn btn-danger btn-sm" href="/book/delete/${data}">Delete</a>`; 
                    	}
                }
            ]
	 });
});