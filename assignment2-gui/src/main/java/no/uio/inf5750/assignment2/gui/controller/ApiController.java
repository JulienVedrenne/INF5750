package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private StudentSystem studentSystem;

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> student(HttpServletRequest request, HttpServletResponse response ,ModelMap model) {
		
		//Return all student 
	return studentSystem.getAllStudents();

	}
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Course> course(ModelMap model) {
		
		//Return all courses 
	return studentSystem.getAllCourses();

	}
	
	@RequestMapping(value = "/student/{student_id}/location", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> setLocation(@PathVariable int student_id,HttpServletRequest request, HttpServletResponse response ,ModelMap model) {
		   // 1. Receive JSON from parameter
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		//Set longitude and latitude for the student given
		studentSystem.setStudentLocation(student_id, latitude, longitude);

	return studentSystem.getAllStudents();

	}


}
