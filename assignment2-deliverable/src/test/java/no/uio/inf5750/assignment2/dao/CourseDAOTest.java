package no.uio.inf5750.assignment2.dao;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;


import no.uio.inf5750.assignment2.dao.hibernate.HibernateCourseDao;
import no.uio.inf5750.assignment2.model.Course;


@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@Transactional
public class CourseDAOTest {
	private Course course;
	

	@Autowired
	private CourseDAO courseDao;
	
	private SessionFactory sessionFactory;

		
		@Before
		public void init()
		{
		   courseDao= new HibernateCourseDao();
		  
		   System.out.println("ksdjfekldsjflksdjfklsdjfkldsjfklsdj" + courseDao);
		   course = new Course("INF5850","Informatics");
		}
	    @Test
	    public void addCourse()
	    {
	    	System.out.println("ksdjfekldsjflksdjfklsdjfkldsjfklsdj" + course);
	        int id = courseDao.saveCourse(course);
	        Course courseTest = courseDao.getCourse(id);
	        assertNotNull(id);
	        assertEquals(id,courseTest.getId());
	    }
	
}

