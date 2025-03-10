$(document).ready( function () {
	 var table = $('#bookTable').DataTable({
			"sAjaxSource": "/api/book/find",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "id"},
			    { "mData": "name" },
				 { "mData": "author" },
				 {
					 "mData" : "id",
					 "bSortable" : false,
					 "mRender": function(data, type, full) {
						    return '<a class="btn btn-info btn-sm me-2" href="/book/view/' + data + '">' + 'Edit' + '</a>'
						    +'<a class="btn btn-danger btn-sm" href="/book/delete/' + data + '">' + 'Delete' + '</a>';
						  }
				 }
			]
	 });
});