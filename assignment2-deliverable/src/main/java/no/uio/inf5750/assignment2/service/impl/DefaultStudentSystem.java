package no.uio.inf5750.assignment2.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

@Component
@Transactional
public class DefaultStudentSystem implements StudentSystem {

	@Autowired
    private CourseDAO courseDao;
	@Autowired
	 private StudentDAO studentDao;
	
	@Override
	public int addCourse(String courseCode, String name) {
		return (Integer) courseDao.saveCourse(new Course(courseCode,name));
		
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
		//Load the student that we want to update
		Course course = courseDao.getCourse(courseId);
		//Set the new value
		course.setCourseCode(courseCode);
		course.setName(name);
		//Save the new course
		courseDao.saveCourse(course);	
	}

	@Override
	public Course getCourse(int courseId) {
	 
		return courseDao.getCourse(courseId);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		return courseDao.getCourseByCourseCode(courseCode);
	}

	@Override
	public Course getCourseByName(String name) {
		return courseDao.getCourseByName(name);
	}

	@Override
	public Collection<Course> getAllCourses() {
		return courseDao.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		
		Course courseTodelete = getCourse(courseId);
		Set<Student> hs = courseTodelete.getAttendants();
		Iterator<Student> it = hs.iterator();
		while (it.hasNext()) {
			
					removeAttendantFromCourse(courseTodelete.getId(),(it.next()).getId());
				
				
			
		}
		
		courseDao.delCourse(getCourse(courseId));
		
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		
		//Load the course where we want to add a student
		Course course = getCourse(courseId);
		System.out.println("course EE" + course.getName());
		//Load the student who want to add to the course
		Student student = getStudent(studentId);
		System.out.println("Sudenf AAA" + student.getName());
		//Load the actual set of student for this course
		Set<Student >attendants = course.getAttendants();
		//Add the student to the actual set of student that attend to this course
		attendants.add(student);
		for (Student s : attendants) {
		    System.out.println("student" + s);
		}
		//course.setAttendants(attendants);
		//Load the actual set of course for this student
		Set<Course> courses = student.getCourses();
		//Add the course to the actual set of course 
		courses.add(course);
		for (Course s : courses) {
		    System.out.println("course" + s);
		}
		//Set the new set to the actual course
		//student.setCourses(courses);	
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		//Load the course where we want to delete a student
		Course course = getCourse(courseId);
		//Load the student who want to delete to the course
		Student student = getStudent(studentId);
		//Load the actual set of student for this course
		Set<Student >attendants = course.getAttendants();
		//delete the student to the actual set of student that attend to this course
		attendants.remove(student);
		//Load the actual set of course for this student
		Set<Course> courses = student.getCourses();
		//Delete the course to the actual set of course 
		courses.remove(course);
		//Set the new set to the actual course
		course.setAttendants(attendants);
		
		
	}

	@Override
	public int addStudent(String name) {
		return  studentDao.saveStudent(new Student(name));
	}

	@Override
	public void updateStudent(int studentId, String name) {
		//Load the student that we want to update
		Student student = studentDao.getStudent(studentId);
		//set the new value
		student.setName(name);
		//Save the new value
		studentDao.saveStudent(student);		
	}

	@Override
	public Student getStudent(int studentId) {
		return studentDao.getStudent(studentId);
	}

	@Override
	public Student getStudentByName(String name) {
		return studentDao.getStudentByName(name);
	}

	@Override
	public Collection<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {
		
		
		studentDao.delStudent(getStudent(studentId));
		
	}

}
