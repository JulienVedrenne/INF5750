package no.uio.inf5750.assignment2.service;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import no.uio.inf5750.assignment2.dao.hibernate.HibernateCourseDao;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.impl.DefaultStudentSystem;


@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentSystemTest {

		
		private Course course;
		
		private Student student;
		
		
		@Autowired
		StudentSystem studentSystem = new DefaultStudentSystem();
		
		@Before
		public void init()
		{

		   course = new Course("INF5850","Informatics");
		   student = new Student("julien");
		  
		}
		
		//Add course complete
	    @Test
	    public void addCourseTest()
	    {
	    	System.out.println("ksdjfekldsjflksdjfklsdjfkldsjfklsdj" + course);
	    	int course_id = studentSystem.addCourse("INF5850","Informatics");
	        Course courseTest = studentSystem.getCourse(course_id);
	        assertNotNull(course_id);
	        assertEquals(course_id,courseTest.getId());
	    }
	    
		//Add course failed
	    @Test
	    public void addCourseTestFailed()
	    {
	    	
	    	int course_id = studentSystem.addCourse("INF5850","");
	        Course courseTest = studentSystem.getCourse(course_id);
	        assertNull(courseTest);
	    }
	    
	  //Add course failed2
	    @Test
	    public void addCourseTestFailed2()
	    {
	    	
	    	int course_id = studentSystem.addCourse("","");
	        Course courseTest = studentSystem.getCourse(course_id);
	        assertNull(courseTest);
	    }
	    
	    @Test
	    public void getCourseTest()
	    {
	    	int course_id = studentSystem.addCourse("INF5850","Informatics");
	        Course courseTest = studentSystem.getCourse(course_id);
	        assertEquals(course_id,courseTest.getId());
	        assertEquals(course.getCourseCode(),courseTest.getCourseCode());
	        assertEquals(course.getName(),courseTest.getName());
	    }
	    
	    @Test
	    public void getCourseByCourseCodeTest()
	    {
	    	int course_id = studentSystem.addCourse("INF5850","Informatics");
	        Course courseTest = studentSystem.getCourseByCourseCode("INF5850");
	        assertEquals(course_id,courseTest.getId());
	        assertEquals(course.getCourseCode(),courseTest.getCourseCode());
	        assertEquals(course.getName(),courseTest.getName());
	    }
	    
	    @Test
	    public void getCourseByNameTest()
	    {
	    	int course_id = studentSystem.addCourse("INF5850","Informatics");
	        Course courseTest = studentSystem.getCourseByName("Informatics");
	        assertEquals(course_id,courseTest.getId());
	        assertEquals(course.getCourseCode(),courseTest.getCourseCode());
	        assertEquals(course.getName(),courseTest.getName());
	    }
	    
	    
	    
	    @Test
	    public void updateCourseTest()
	    {
	    	int course_id = studentSystem.addCourse("INF5850","Informatics");
	    	studentSystem.updateCourse(course_id, "INF5860","Informatics");
	        Course courseTest = studentSystem.getCourse(course_id);
	        assertNotEquals(course.getCourseCode(),courseTest.getCourseCode());
	        assertEquals(course.getName(),courseTest.getName());
	    }
	    
	    @Test
	    public void deleteCourseTest()
	    {
	    	int course_id = studentSystem.addCourse("INF5850","Informatics");
	        studentSystem.delCourse(course_id);
	        Course courseTest = studentSystem.getCourse(course_id);
	        assertNull(courseTest);
	       
	    }
	    
	    @Test
	    public void addStudentTest()
	    {
	    	
	    	int student_id = studentSystem.addStudent("julien");
	        Student studentTest = studentSystem.getStudent(student_id);
	        assertNotNull(student_id);
	        assertEquals(student_id,studentTest.getId());
	    }
	    
	    @Test
	    public void getStudentByName()
	    {
	    	int student_id = studentSystem.addStudent("julien");
	        Student studentTest = studentSystem.getStudentByName("julien");
	        assertNotNull(student_id);
	        assertEquals(student_id,studentTest.getId());
	    }
	    
	    @Test
	    public void addStudentTestFailed()
	    {
	    	
	    	int student_id = studentSystem.addStudent("");
	        Student studentTest = studentSystem.getStudent(student_id);
	        assertNull(studentTest);
	    }
	    
	    @Test
	    public void getStudentTest()
	    {
	    	int student_id = studentSystem.addStudent("julien");
	        Student studentTest = studentSystem.getStudent(student_id);
	        assertEquals(student_id,studentTest.getId());
	        assertEquals(student.getName(),studentTest.getName());
	    }
	    
	    @Test
	    public void updateStudentTest()
	    {
	    	int student_id = studentSystem.addStudent("julien");
	    	studentSystem.updateStudent(student_id,"moi");
	        Student studentTest = studentSystem.getStudent(student_id);
	        assertNotEquals(student.getName(),studentTest.getName());
	    }
	    
	    @Test
	    public void deleteStudentTest()
	    {
	    	int Student_id = studentSystem.addStudent("julien");
	        studentSystem.delStudent(Student_id);
	        Student StudentTest = studentSystem.getStudent(Student_id);
	        assertNull(StudentTest);
	    }
	    
	    @Test
	    public void addAttendantToCourseTest()
	    {
	    	
	    	int student_id = studentSystem.addStudent("julien");
	    	int course_id = studentSystem.addCourse("INF5850","Informatics");
	    	
	    	studentSystem.addAttendantToCourse(course_id, student_id);
	        
	    	Student student = studentSystem.getStudent(student_id);
	    	Course course = studentSystem.getCourse(course_id);
			//Load the actual set of student for this course
			Set<Student >attendants = course.getAttendants();
			//Add the student to the actual set of student that attend to this course
			for (Student s : attendants) {
				assertEquals(s.getName(),student.getName());
			}
					
			Set<Course> courses = student.getCourses();
			//Add the course to the actual set of course 
			
			for (Course c : courses) {
				assertEquals(c.getName(),course.getName());
			}    	
	        
	    }
	    
	    @Test
	    public void removeAttendantToCourseTest()
	    {
	    	
	    	int student_id = studentSystem.addStudent("julien");
	    	int course_id = studentSystem.addCourse("INF5850","Informatics");
	    	
	    	studentSystem.addAttendantToCourse(course_id, student_id);
	    	studentSystem.removeAttendantFromCourse(course_id, student_id);
	        
	    	Student student = studentSystem.getStudent(student_id);
	    	Course course = studentSystem.getCourse(course_id);
			//Load the actual set of student for this course
			Set<Student >attendants = studentSystem.getCourse(course_id).getAttendants();
			assertEquals(true, attendants.isEmpty());		
					
			Set<Course> courses = student.getCourses();
	
			assertEquals(true, courses.isEmpty());
	        
	    }
	    
	    @Test
	    public void setStudentLocationTest()
	    {
	    	int student_id = studentSystem.addStudent("julien");
	        Student studentTest = studentSystem.getStudent(student_id);
	        studentSystem.setStudentLocation(student_id, "20,00", "30,545");
	        
	        assertEquals(studentTest.getLatitude(),"20,00");
	        assertEquals(studentTest.getLongitude(),"30,545");
	    }    
	    	
	}

