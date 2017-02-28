package com.kpit.main;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.kpit.constants.Constants;
import com.kpit.display.StudentDataCalculations;
import com.kpit.pojo.Fees;
import com.kpit.pojo.MasterStudent;
import com.kpit.util.HibernateSessionFactory;
import com.kpit.util.PropertyReader;

import readExcel.StudentDataOperations;

/**
 * @author sachinc3 this is the main class from where all calls given to the
 *         methods.
 */
public class Launcher {
	/**
	 * @param args
	 * @throws Exception
	 *             main method where other class objects are created and and
	 *             give call to their method through object
	 */

	public static void main(String[] args) {
		//TODO: Session should not be in main
		/**
		 * main should have call to only two function
		 * 1. Excel to database data writing
		 * 2. Processing of the data
		 * 
		 * if required pass property file path to some function and do rest of operation in respective function
		 * 
		 * object creation should not be required for using properties in different classes
		 * 
		 * Naming convention
		 */
		//Session hSession = HibernateSessionFactory.openSession();
//		PropertyReader prop = new PropertyReader();
		Session session = HibernateSessionFactory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			getAllStudents(session);
			System.out.println("======");
			getStudentById(session, 1001);
			System.out.println("======");
			orderAndLimitExample(session);
			System.out.println("======");
			likeCriteria(session);
			System.out.println("======");
			projectionsExample(session);
			System.out.println("======");
			sumExample(session);
			System.out.println("======");
			joinExample(session);
			tx.commit();
//			PropertyReader.readProperties(Constants.CONFIGFILEPATH);

//			StudentDataOperations dataOperations = new StudentDataOperations();
//			dataOperations.readData();
			//dataOperations.printDetails(StudentDataOperations.getStudentBeanList());
//			StudentDataCalculations studentdatacal = new StudentDataCalculations();
//			studentdatacal.insertDataIntoDatabase();
			//studentdatacal.studentDataCalculate(hSession, studentdatacal);
			
		} catch (Exception e) {

		}
	}
	
	private static void getAllStudents(Session session) {
		
		
		Criteria criteria = session.createCriteria(MasterStudent.class, "student");
		List<MasterStudent> list = criteria.list();
		for (MasterStudent masterStudent : list) {
			System.out.println(masterStudent.toString());
		}
		
	}
	
	private static void getStudentById(Session session, Integer id){
		
		Criteria criteria = session.createCriteria(MasterStudent.class).add(Restrictions.eq("studId", id));
		MasterStudent masterStudent = (MasterStudent) criteria.uniqueResult();
		System.out.println(masterStudent.toString());
	}
	
	private static void orderAndLimitExample(Session session){
		@SuppressWarnings("unchecked")
		List<MasterStudent> list = session.createCriteria(MasterStudent.class, "student")
									.addOrder(Order.desc("studId"))
									.setMaxResults(2)
									.list();
		
		for (MasterStudent masterStudent : list) {
			System.out.println(masterStudent.toString());
		}
									
	}
	
	private static void likeCriteria(Session session) {
		@SuppressWarnings("unchecked")
		List<MasterStudent> list = session.createCriteria(MasterStudent.class, "student")
									.add(Restrictions.like("studName", "%i%")).list();
		System.out.println(list.toString());
				
	}
	
	private static void projectionsExample(Session session){
		Integer count =  (Integer) session.createCriteria(MasterStudent.class)
									.setProjection(Projections.count("studName"))
									.add(Restrictions.like("studName", "%i%"))
									.uniqueResult();
		
		System.out.println(count);
		
	}
	
	private static void sumExample(Session session){
		Double sum = (Double) session.createCriteria(Fees.class)
				.setProjection(Projections.sum("otherCost"))
				.uniqueResult();
		
		System.out.println(sum);
	}

	@SuppressWarnings("unchecked")
	private static void joinExample(Session session){
		List<Object[]> list = session.createCriteria(MasterStudent.class, "student")
							.createAlias("student.deptName", "deptName")
							.createAlias("student.examResults", "examResults")
							.createAlias("student.feeses", "fees")
							.createAlias("student.studEventParticipations", "studEventParticipations")
							.createAlias("deptName.staffs", "staff")
							.setProjection(Projections.projectionList()
											.add(Projections.property("student.studName"))
											.add(Projections.property("deptName.deptName"))
											.add(Projections.property("examResults.result"))
											.add(Projections.property("fees.fee"))
											.add(Projections.property("studEventParticipations.eventParticipation"))
											.add(Projections.property("staff.hodName")))
											.list();
							
		
		for(Object[] arr : list){
			System.out.println(Arrays.toString(arr));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void criteriaExample(Session session){
		
		Transaction tx = session.beginTransaction();

		//Get All Students
		Criteria criteria = session.createCriteria(MasterStudent.class);
		List<MasterStudent> empList = criteria.list();
		for(MasterStudent emp : empList){
			System.out.println("ID="+emp.getStudName()+", Address="+emp.getStudAddress());
		}
		
		// Get with ID, creating new Criteria to remove all the settings
		criteria = session.createCriteria(MasterStudent.class)
					.add(Restrictions.eq("studId", 1001));
		MasterStudent emp = (MasterStudent) criteria.uniqueResult();
		System.out.println("Name=" + emp.getStudName() + ", City="
				+ emp.getStudAddress());

		//Pagination Example
		empList = session.createCriteria(MasterStudent.class)
					.addOrder(Order.desc("studId"))
					.setFirstResult(0)
					.setMaxResults(4)
					.list();
		for(MasterStudent emp4 : empList){
			System.out.println("Paginated Employees::"+emp4.getStudId()+","+emp4.getStudName());
		}

		//Like example
		empList = session.createCriteria(MasterStudent.class)
				.add(Restrictions.like("studName", "%i%"))
				.list();
		for(MasterStudent emp4 : empList){
			System.out.println("Students having 'i' in name::"+emp4.getStudName());
		}
		
		//Projections example
		Integer count = (Integer) session.createCriteria(MasterStudent.class)
				.setProjection(Projections.rowCount())
				.add(Restrictions.like("studName", "%i%"))
				.uniqueResult();
		System.out.println("Number of Students with 'i' in name="+count);

		//using Projections for sum, min, max aggregation functions
		double sumSalary = (Double) session.createCriteria(Fees.class)
			.setProjection(Projections.sum("otherCost"))
			.uniqueResult();
		System.out.println("Sum of Salaries="+sumSalary);
		
		//Join example for selecting few columns
		criteria = session.createCriteria(MasterStudent.class, "employee");
//		criteria.setFetchMode("employee.deptName", FetchMode.JOIN);
		criteria.createAlias("employee.deptName", "deptName"); // inner join by default
//		criteria.setFetchMode("deptName.staff", FetchMode.JOIN);
		criteria.createAlias("deptName.staffs", "staff");
		criteria.createAlias("employee.examResults", "examResults");
		criteria.createAlias("employee.feeses", "fees");
		criteria.createAlias("employee.studEventParticipations", "studEventParticipations");
		
		ProjectionList columns = Projections.projectionList()
						.add(Projections.property("employee.studName"))
						.add(Projections.property("deptName.deptName"))
						.add(Projections.property("staff.hodName"))
						.add(Projections.property("examResults.result"))
						.add(Projections.property("fees.fee"))
						.add(Projections.property("studEventParticipations.eventParticipation"));
		criteria.setProjection(columns);

		List<Object[]> list = criteria.list();
		for(Object[] arr : list){
			System.out.println(Arrays.toString(arr));
		}
		
		
		
		
		// Rollback transaction to avoid messing test data
		tx.commit();
		// closing hibernate resources
		

	}

	
	
}
