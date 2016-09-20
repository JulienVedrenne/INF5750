package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private StudentSystem studentSystem;

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	@ResponseBody
	public Collection student(HttpServletRequest request, HttpServletResponse response ,ModelMap model) {
		

	return studentSystem.getAllStudents();

	}
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	@ResponseBody
	public Collection course(HttpServletRequest request, HttpServletResponse response ,ModelMap model) {
		

	return studentSystem.getAllCourses();

	}

}
