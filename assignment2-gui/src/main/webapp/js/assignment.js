function getStudentData() {
	// This must be implemented by you. The json variable should be fetched
	// from the server, not initiated with a static value as below. 
	// You must first download the student json data from the server
	// then call populateStudentTable(json);
	// and then populateStudentLocationForm(json);
	
	//Ajax call to the server to collect the student information with JSON format 
	$.ajax({
		url: 'http://localhost:8080/assignment2-gui/api/student',
		type: 'GET',
		dataType: 'json',
		success: function (json ){
		console.log(json)
		populateStudentTable(json);
		populateStudentLocationForm(json);
		}
		});
	
}

function populateStudentTable(json) {
	// for each student make a row in the student location table
	// and show the name, all courses and location.
	// if there is no location print "No location" in the <td> instead
	// tip: see populateStudentLocationForm(json) og google how to insert html from js with jquery. 
	// Also search how to make rows and columns in a table with html

	// the table can you see in index.jsp with id="studentTable"
	//We remove all the acutal student 
	$('.info').remove();
	
	//foreach student on the database
	for (var s = 0; s < json.length; s++) {
		//we create one ligne of table
		var formString = '<tr class= "info"><select id="studentTable" name="students">';	
		//We add one line with name course and location
		var student = json[s];
		var course = "";
		student = explodeJSON(student);
		formString += '<td>' + student.name + '</td>'
				;
	
		for (var i = 0, len = (student.courses).length; i < len; i++) {
			  course += (student.courses)[i].courseCode +"  ";
			}
		
		formString += '<td>' + course + '</td>'
		;
		
		//If we found a location for our student 
		if(student.latitude != null && student.longitude !=null){
			formString += '<td>' + "latitude " + student.latitude + " longitude " + student.longitude + '</td>'
			;
			var myLatlng = new google.maps.LatLng(student.latitude, student.longitude);                        
			var marker = new google.maps.Marker({
			   position: myLatlng,
			   map: map,
			   title: student.name
			});

		}
		else{
			formString += '<td>' + 'No location' + '</td>'
		}
		formString += '</select></tr>';
		
		//Add the html created on the table with the id studentTable
		$('#studentTable').append(formString);
		
	}
	
}

function populateStudentLocationForm(json) {
	
	//we create one ligne of table
	var formString = '<tr><td><select id="selectedStudent" name="students">';
	//foreach student on the database
	for (var s = 0; s < json.length; s++) {
		// We put the name of the student on the select ( add an option with value ) 
		var student = json[s];
		student = explodeJSON(student);
		formString += '<option value="' + student.id + '">' + student.name
				+ '</option>';
	}
	formString += '</select></td></tr>';
	
	//Add the html created on the table with the id studentLocationTable
	$('#studentLocationTable').append(formString);
	
}

//On click button set location
$('#locationbtn').on('click', function(e) {
	e.preventDefault();
	get_location();
});

// This function gets called when you press the Set Location button
function get_location() {
	
	    if (navigator.geolocation) {
	    	
	        navigator.geolocation.getCurrentPosition(location_found);
	    } else {
	        x.innerHTML = "Geolocation is not supported by this browser.";
	    }
	}

// Call this function when you've succesfully obtained the location. 
function location_found(position) {
	// Extract latitude and longitude and save on the server using an AJAX call. 
	// When you've updated the location, call populateStudentTable(json); again
	// to put the new location next to the student on the page. .
	
	//we collect the id of the student select on the combobox
	var liste = document.getElementById("selectedStudent");
	var id = liste.options[liste.selectedIndex].value;
	//Ajax  call with the id of student selected to update location
	$.ajax({
		url:" http://localhost:8080/assignment2-gui/api/student/" + id + "/location",
		type: 'GET',
		dataType: 'JSON',
		data:{ 
			latitude: position.coords.latitude,
			longitude: position.coords.longitude
		},
		success: function (json ){
		//Populate table on the success of the ajax call
		populateStudentTable(json);
		}
		});

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

var map;
function initialize_map() {
       var mapOptions = {
               zoom : 10,
               mapTypeId : google.maps.MapTypeId.ROADMAP
       };
       map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
       // Try HTML5 geolocation
       if (navigator.geolocation) {
               navigator.geolocation.getCurrentPosition(function(position) {
                       var pos = new google.maps.LatLng(position.coords.latitude,
                                       position.coords.longitude);
                       map.setCenter(pos);
               }, function() {
                       handleNoGeolocation(true);
               });
       } else {
               // Browser doesn't support Geolocation
               // Should really tell the user…
       }
}
