function getStudentData() {
	// This must be implemented by you. The json variable should be fetched
	// from the server, not initiated with a static value as below. 
	// You must first download the student json data from the server
	// then call populateStudentTable(json);
	// and then populateStudentLocationForm(json);

}

function populateStudentTable(json) {
	// for each student make a row in the student location table
	// and show the name, all courses and location.
	// if there is no location print "No location" in the <td> instead
	// tip: see populateStudentLocationForm(json) og google how to insert html from js with jquery. 
	// Also search how to make rows and columns in a table with html

	// the table can you see in index.jsp with id="studentTable"
	
}

function populateStudentLocationForm(json) {
	var formString = '<tr><td><select id="selectedStudent" name="students">';
	for (var s = 0; s < json.length; s++) {
		var student = json[s];
		student = explodeJSON(student);
		formString += '<option value="' + student.id + '">' + student.name
				+ '</option>';
	}
	formString += '</select></td></tr>';
	
	$('#studentLocationTable').append(formString);
	
}

$('#locationbtn').on('click', function(e) {
	e.preventDefault();
	get_location();
});

// This function gets called when you press the Set Location button
function get_location() {
}

// Call this function when you've succesfully obtained the location. 
function location_found(position) {
	// Extract latitude and longitude and save on the server using an AJAX call. 
	// When you've updated the location, call populateStudentTable(json); again
	// to put the new location next to the student on the page. .

}

var objectStorage = new Object();

function explodeJSON(object) {
	if (object instanceof Object == true) {
		objectStorage[object['@id']] = object;
		console.log('Object is object');
	} else {
		console.log('Object is not object');
		object = objectStorage[object];
		console.log(object);
	}
	console.log(object);
	return object;
}
