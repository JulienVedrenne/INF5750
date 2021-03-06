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
		
	//save if all argument have a value 
		if(courseCode != "" && name != "")
		{
		return (Integer) courseDao.saveCourse(new Course(courseCode,name));
		}
		else{
			return 0;
		}
		
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
		
		//Find the course to delete
		Course courseTodelete = getCourse(courseId);
		//Start to collect all the student follow by a student
		Set<Student> setStudent = courseTodelete.getAttendants();
		
		Object[] set=  setStudent.toArray();
		//For each student present on the course
		for (Object s : set) {
			
			//Remove the course of the list of the student courses
			Student student= new Student();
			
			student=(Student) s;
			
			removeAttendantFromCourse(courseTodelete.getId(),student.getId());
		    
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
		//Save student if the name is not null 
		if( name != ""){
		return  studentDao.saveStudent(new Student(name));
		}
		else{
			return 0;
		}
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
		//Find the student to delete
		Student studentTodelete = getStudent(studentId);
		//Start to collect all the course follow by a student
		Set<Course> setCourse = studentTodelete.getCourses();
		
		Object[] set=  setCourse.toArray();
		//For each course follow by a student
		for (Object s : set) {
			//Remove the student of the list of the course
			Course course= new Course();
			
			course=(Course) s;
			
			removeAttendantFromCourse(course.getId(),studentId);
		    
		}

		studentDao.delStudent(getStudent(studentId));
		
	}

	@Override
	public void setStudentLocation(int studentId, String latitude, String longitude) {
		
		//Collect student
		Student student= getStudent(studentId);
		//Set latitude and longitude
		student.setLatitude(latitude);
		student.setLongitude(longitude);
			
	}

}
