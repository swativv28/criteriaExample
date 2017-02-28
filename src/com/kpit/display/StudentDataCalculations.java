package com.kpit.display;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kpit.constants.Constants;
import com.kpit.dao.DeptDao;
import com.kpit.dao.ExamResultDao;
import com.kpit.dao.FeeDao;
import com.kpit.dao.MasterStudentDao;
import com.kpit.dao.StaffDao;
import com.kpit.dao.StudEventParticipateDao;
import com.kpit.pojo.ExamResult;
import com.kpit.pojo.Fees;
import com.kpit.util.StringHelper;
import com.kpit.vo.StudentDataVo;

import readExcel.StudentDataOperations;

public class StudentDataCalculations {

	private static final Logger LOGGER = Logger.getLogger(StudentDataCalculations.class);

	/**
	 * @author sachinc3 This method displays all the details of student from IT
	 *         dept. which is retrieved from DB
	 */
	public void printDeptWiseStudentDetails(String Stud_Department, Session hSession) {
		//TODO: handle all the possible exception
		try {
			//Session hSession = HibernateSessionFactory.openSession();

			String hql = "from Staff s,MasterStudent m,DeptName d, ExamResult e, Fees f, StudEventParticipation se"
					+ " WHERE m.deptName.deptId = d.deptId and s.deptName.deptId = d.deptId "
					+ " and e.masterStudent.studId = m.studId  and f.masterStudent.studId = m.studId"
					+ "  and se.masterStudent.studId = m.studId" + " and d.deptName =:name";

			Query querry = hSession.createQuery(hql);
			querry.setString("name", Stud_Department);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Object[]> result = querry.list();
			for (Object[] list : result) {
				
				LOGGER.info((Arrays.toString(list)));
			}
		} 
		catch (HibernateException e) {
			LOGGER.error("Error in getting student details if query gets failed to retrive data from DB", e);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.error("Error in getting student details if values goes out of bound", e);
		}
		catch (NullPointerException e) {
			LOGGER.error("Error in getting student details if values are null", e);
		}
	}

	/**
	 * @author sachinc3
	 * @return calculate department wise passed student percentage
	 * 
	 */
	public double calculateDepartmentWisePassStud(String Stud_Department, Session hSession) {

		//Session hSession = HibernateSessionFactory.openSession();
		DecimalFormat df = new DecimalFormat("#.##");
		int noOfStudPassed = 0;
		int totalNoOfStud = 0;
		double result = 0.0;
		try {
			String hql = "from ExamResult";
			Query querry = hSession.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<ExamResult> res = querry.list();
			for (ExamResult list : res) {
				if (list.getMasterStudent().getDeptName().getDeptName().equalsIgnoreCase(Stud_Department)) {
					totalNoOfStud++;

					if (list.getResultStatus().equalsIgnoreCase(Constants.RESULT_STATUS)) {
						noOfStudPassed++;
					}
				}
			}

			result = calculatePercentage(noOfStudPassed, totalNoOfStud);
		} catch (HibernateException e) {
			LOGGER.error("Hibernate exception error if query gets failed to retrive data from DB", e);
		}

		catch (NullPointerException e) {
			LOGGER.error("null values retrived error", e);
		}
		LOGGER.info("Number of student passed from " + Stud_Department + ": " + df.format(result) + " %" + "\n");
		//System.out.println("Number of student passed from " + Stud_Department + ": " + df.format(result) + " %" + "\n");
		return result;
	}
	
	//TODO: Exception handling
	private static double calculatePercentage(int noOfStudPassed, int totalNoOfStud) {
		double result = 0;
		try {
			double noOfStudPassedInDouble = (double) noOfStudPassed;
			double totalNoOfStudInDouble = (double) totalNoOfStud;
			result = (double) ((noOfStudPassedInDouble) / (totalNoOfStudInDouble) * 100);
		} 
		catch (NullPointerException e) {
			LOGGER.error("Error in getting values if variable contains null value", e);
		}
		return result;
	}

	/**
	 * 
	 * @return college revenue
	 */
	public double getCollegeRevenue(Session hSession) {

		//Session hSession = HibernateSessionFactory.openSession();
		double collegeRevenue = 0.0;
		double otherCostTemp;
		String feeTempstr;
		double feeTemp;
		String hodSalTempstr;
		double hodSalTemp;
		DecimalFormat df = new DecimalFormat("#.###");

		try {
			String feehql = "select SUM(fee) from Fees";
			Query feequerry = hSession.createQuery(feehql);
			feeTempstr = feequerry.uniqueResult().toString().trim();
			feeTemp = (Double.parseDouble(feeTempstr));
			//System.out.println("Total Fee: " + df.format(feeTemp) + " in lac \n");
			LOGGER.info("Total Fee: " + df.format(feeTemp) + " in lac \n");

			String hodSalql = "select SUM(hodSalary) from Staff";
			Query hodSalquerry = hSession.createQuery(hodSalql);
			hodSalTempstr = hodSalquerry.uniqueResult().toString();
			hodSalTemp = (Double.parseDouble(hodSalTempstr));
			//System.out.println("Total HOD Salary: " + df.format(hodSalTemp) + " in lac \n");
			LOGGER.info("Total HOD Salary: " + df.format(hodSalTemp) + " in lac \n");

			String otherCosthql = "select SUM(otherCost) from Fees";
			Query otherCostquerry = hSession.createQuery(otherCosthql);
			otherCostTemp = (Double) otherCostquerry.uniqueResult();
			//System.out.println("Total other cost: " + df.format(otherCostTemp) + " in lac \n");
			LOGGER.info("Total other cost: " + df.format(otherCostTemp) + " in lac \n");

			collegeRevenue = feeTemp - (hodSalTemp + otherCostTemp);
			//System.out.println("Total revenue of College: " + df.format(collegeRevenue) + " in lac \n");
			LOGGER.info("Total revenue of College: " + df.format(collegeRevenue) + " in lac \n");
		} catch (HibernateException e) {
			LOGGER.error("Hibernate exception error if query gets failed to retrive data from DB", e);
		} catch (NumberFormatException e) {
			LOGGER.error("number format error if values are not in proper format", e);
		}
		catch (NullPointerException e) {
			LOGGER.error("null pointer exception if variables contains null values", e);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.error("array index out of bound exception if variables value goes out of bound", e);
		}
		return collegeRevenue;

	}

	/**
	 * calculate dept wise revenue
	 * 
	 * @param Stud_Department
	 * @return
	 */
	public double getDeptWiseRevenue(String Stud_Department, Session hSession) {

		//Session hSession = HibernateSessionFactory.openSession();
		DecimalFormat df = new DecimalFormat("#.###");
		double deptWiseRevenue = 0.0;
		String feeTempstr;
		double feeTemp;
		String hodSalTempstr;
		double hodSalTemp;
		double otherCostTemp;

		try {
			String feehql = "select SUM(f.fee) FROM Fees f, MasterStudent m , DeptName d"
					+ " WHERE f.masterStudent.studId = m.studId" + " AND m.deptName.deptId = d.deptId"
					+ " AND d.deptName =:name";
			Query feequerry = hSession.createQuery(feehql);
			feequerry.setString("name", Stud_Department);
			feeTempstr = feequerry.uniqueResult().toString();
			feeTemp = (Double.parseDouble(feeTempstr));
			//System.out.println("Total Fee of " + Stud_Department + " Dept: " + feeTemp + " in lac \n");
			LOGGER.info("Total Fee of: "+ Stud_Department+" Dept: "+feeTemp + " in lac \n");

			String hodSalql = "select SUM(s.hodSalary) from Staff s WHERE s.deptName.deptName =:name";
			Query hodSalquerry = hSession.createQuery(hodSalql);
			hodSalquerry.setString("name", Stud_Department);
			hodSalTempstr = hodSalquerry.uniqueResult().toString();
			hodSalTemp = (Double.parseDouble(hodSalTempstr));
			//System.out.println("Total HOD Salary of:" + Stud_Department + " dept" + hodSalTemp + " in lac \n");
			LOGGER.info("Total HOD Salary of: "+ Stud_Department + " dept" + hodSalTemp + " in lac \n");

			String otherCosthql = "SELECT SUM(f.otherCost) FROM Fees f, MasterStudent m , DeptName d "
					+ " WHERE f.masterStudent.studId = m.studId" + " AND m.deptName.deptId = d.deptId"
					+ " AND d.deptName =:name ";
			Query otherCostquerry = hSession.createQuery(otherCosthql);
			otherCostquerry.setString("name", Stud_Department);
			otherCostTemp = (Double) otherCostquerry.uniqueResult();
			//System.out.println("Total other cost: " + Stud_Department + " dept " + df.format(otherCostTemp) + " in lac \n");
			LOGGER.info("Total other cost: " + Stud_Department + " dept " + df.format(otherCostTemp)+ " in lac \n");

			deptWiseRevenue = feeTemp - (hodSalTemp + otherCostTemp);
			//System.out.println("Total revenue of " + Stud_Department + " dept" + deptWiseRevenue + " in lac \n");
			LOGGER.info("Total revenue of " + Stud_Department + " dept" + deptWiseRevenue + " in lac \n");
		} catch (HibernateException e) {

			LOGGER.error("Hibernate exception error if query gets failed to retrive data from DB", e);
		} catch (NumberFormatException e) {

			LOGGER.error("number format error if number is not in proper format", e);
		}

		catch (NullPointerException e) {

			LOGGER.error("null pointer exception error if variables contains null value", e);
		}
		return deptWiseRevenue;
	}

	/**
	 * @author sachinc3
	 * @param Stud_Department
	 *            -department name
	 * @return calculate average and total fee department wise and total and
	 *         display it
	 */
	public double getAverageAndTotalFee(String Stud_Department, Session hSession) {

		//Session hSession = HibernateSessionFactory.openSession();
		double avrageFee = 0.0;
		double totalFee = 0.0;
		int totalNumOfStud = 0;
		try {
			String hql = "from Fees";
			Query querry = hSession.createQuery(hql);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Fees> res = querry.list();
			for (Fees list : res) {

				if (list.getMasterStudent().getDeptName().getDeptName().equalsIgnoreCase(Stud_Department)) {
					totalNumOfStud++;
					totalFee += Double.parseDouble(StringHelper.getDigit(list.getFee()));
				}

			}
			avrageFee = totalFee / totalNumOfStud;
		} catch (HibernateException e) {

			LOGGER.error("Hibernate exception error if query gets failed to retrive data from DB", e);

		} catch (NumberFormatException e) {

			LOGGER.error("number format error if number is not in proper format", e);
		}
		catch (NullPointerException e) {

			LOGGER.error("null pointer exception error if variables contains null value", e);
		}
		LOGGER.info("Average Fee of " + Stud_Department + "department:" + avrageFee + " in lac" + "\n");
		//System.out.println("Average Fee of " + Stud_Department + "department:" + avrageFee + " in lac" + "\n");
		LOGGER.info("Total Fee of " + Stud_Department + " department:" + totalFee + " in lac" + "\n");
		//System.out.println("Total Fee of " + Stud_Department + " department:" + totalFee + " in lac" + "\n");
		return avrageFee;
	}

	/**
	 * 
	 * @return percentage of student those who are passed and participated in
	 *         the event
	 */
	public double getPercentOfPassingStudParticipatingInEvent(Session hSession) {
		//Session hSession = HibernateSessionFactory.openSession();

		double result = 0.0;
		DecimalFormat df = new DecimalFormat("#.##");
		try {
			String hql = " SELECT COUNT(resultId) FROM ExamResult WHERE resultStatus='Pass' ";
			Query querry = hSession.createQuery(hql);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Long totalNoOfStud = (Long) querry.uniqueResult();
			// System.out.println(totalNoOfStud);

			String hql1 = " SELECT COUNT(p.studEventId) FROM ExamResult r, StudEventParticipation p "
					+ " WHERE p.masterStudent.studId=r.masterStudent.studId and r.resultStatus=:pass and p.eventParticipation=:yes ";
			Query querry1 = hSession.createQuery(hql1);
			querry1.setString("pass", "Pass");
			querry1.setString("yes", "Yes");
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Long noOfStudPassed = (Long) querry1.uniqueResult();

			result = calPercentage(noOfStudPassed, totalNoOfStud);
			LOGGER.info(
					"% of student participating in college event, if only student passing from the dept. can contribute: "
							+ df.format(result) + "\n");
			//System.out.println("% of student participating in college event, if only student passing from the dept. can contribute: "	+ df.format(result) + "\n");
		} catch (HibernateException e) {
			LOGGER.error("Hibernate exception error if query gets failed to retrive data from DB", e);
		}
		catch (NumberFormatException e) {
			LOGGER.error("number format error if number is not in proper formatr", e);
		}
		catch (NullPointerException e) {
			LOGGER.error("null pointer exception error if variables contains null value", e);
		}
		return result;
	}

	private static double calPercentage(Long noOfStudPassed, Long totalNoOfStud) {
		double noOfStudPassedInDouble = (double) noOfStudPassed;
		double totalNoOfStudInDouble = (double) totalNoOfStud;
		double result = (double) ((noOfStudPassedInDouble) / (totalNoOfStudInDouble) * 100);
		return result;
	}

	/**
	 * get count of student passed with different passing percentage
	 * 
	 * @param result
	 * @return count
	 */
	public int getCountOfStudentPassed(double result, Session hSession) {
		//Session hSession = HibernateSessionFactory.openSession();
		int count = 0;

		try {
			String hql = "from ExamResult";
			Query querry = hSession.createQuery(hql);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<ExamResult> res = querry.list();
			for (ExamResult list : res) {

				if (list.getResult() >= result) {
					count++;
				}
			}
		} catch (HibernateException e) {

			LOGGER.error("Hibernate exception error if query gets failed to retrive data from DB", e);
		}
		catch (NullPointerException e) {

			LOGGER.error("null pointer exception error if variables contains null value", e);
		}

		LOGGER.info("Number of student passed with passing criteria is " + result + "%: " + count + "\n");
		//System.out.println("Number of student passed with passing criteria is " + result + "%: " + count + "\n");

		return count;

	}
	
	public void insertDataIntoDatabase() {
		  List<StudentDataVo> studentDataVo = StudentDataOperations.getStudentBeanList();

		  DeptDao dept = new DeptDao();
		  LOGGER.info("Insert data into dept table started");
		  dept.insertIntoDeptName();
		  LOGGER.info("insert data into dept table completely");
		  
		  MasterStudentDao m1 = new MasterStudentDao();
		  LOGGER.info("Insert data into MasterStudent table started");
		  m1.getMasterStudDetails(studentDataVo);
		  LOGGER.info("insert data into MasterStudent table completely");
		  
		  StaffDao staff = new StaffDao();
		  LOGGER.info("Insert data into Staff table started");
		  staff.insertIntoStaff();
		  LOGGER.info("insert data into Staff table completely");
		  
		  FeeDao fees = new FeeDao();
		  LOGGER.info("Insert data into Fee table started");
		  fees.getFeeDetails(studentDataVo);
		  LOGGER.info("Insert data into Fee table completely");
		  
		  StudEventParticipateDao studEventParticipateDao = new StudEventParticipateDao();
		  LOGGER.info("Insert data into StudEventParticipate table started"); 
		   studEventParticipateDao.getStudEventParticipateDetails(studentDataVo);
		   LOGGER.info("Insert data into StudEventParticipate table completely");
		  
		  ExamResultDao examResultDao = new ExamResultDao();
		  LOGGER.info("Insert data into ExamResult table started");
		  examResultDao.getExamResultDetails(studentDataVo);
		  LOGGER.info("Insert data into ExamResult table completely");
	}

	
	
	public void studentDataCalculate(Session hSession, StudentDataCalculations studentdatacal) {
		/**
		 * print only IT department student details
		 */
		LOGGER.info("**********Displaying IT department Data started********** \n");	
		studentdatacal.printDeptWiseStudentDetails(Constants.IT_DEPT, hSession);
		LOGGER.info("**********Displayed IT department Data successfully********** \n\n\n");
		/**
		 * call to calculate percentage department wise of how many student passed
		 */
		LOGGER.info("**********Displaying department wise percentage of how many student passed********** \n");
		//TODO: Single function should give all expected output
		studentdatacal.calculateDepartmentWisePassStud(Constants.IT_DEPT, hSession);
		studentdatacal.calculateDepartmentWisePassStud(Constants.CS_DEPT, hSession);
		studentdatacal.calculateDepartmentWisePassStud(Constants.ExTc_DEPT, hSession);
		LOGGER.info("**********Displayed department wise percentage successfully********** \n\n\n");

		/**
		 * call to calculate average and total fee department wise 
		 */
		LOGGER.info("**********Displaying department wise total and average fee********** \n");
		studentdatacal.getAverageAndTotalFee(Constants.IT_DEPT, hSession);
		studentdatacal.getAverageAndTotalFee(Constants.CS_DEPT, hSession);
		studentdatacal.getAverageAndTotalFee(Constants.ExTc_DEPT, hSession);
		LOGGER.info("**********Displayed department wise total and average successfully********** \n\n\n");
		/**
		 * get count of how many students passed
		 */
		LOGGER.info("**********Displaying count of how many number of students passed********** \n");
		studentdatacal.getCountOfStudentPassed(Constants.RESULT, hSession);
		LOGGER.info("**********Displayed count of how many number of students passed successfully**********\n\n\n");
		/**
		 * get percentage when student is passed and participated in the
		 * event
		 */
		LOGGER.info("**********Displaying percentage of those students whos are passed and participated in the event********** \n");
		studentdatacal.getPercentOfPassingStudParticipatingInEvent(hSession);
		LOGGER.info("**********Displayed percentage of those students whos are passed and participated in the event successfully********** \n\n\n");
		/**
		 * get total revenue of college
		 */
		LOGGER.info("**********Displaying total college revenue********** \n");
		studentdatacal.getCollegeRevenue(hSession);
		LOGGER.info("**********Displayed total college revenue successfully********** \n\n\n");
		/**
		 * get total revenue department wise
		 * 
		 */
		LOGGER.info("**********Displaying department wise collge revenue********** \n");
		studentdatacal.getDeptWiseRevenue(Constants.IT_DEPT, hSession);
		studentdatacal.getDeptWiseRevenue(Constants.CS_DEPT, hSession);
		studentdatacal.getDeptWiseRevenue(Constants.ExTc_DEPT, hSession);
		LOGGER.info("**********Displayed department wise collge revenue successfully**********\n\n\n");
	}
}
