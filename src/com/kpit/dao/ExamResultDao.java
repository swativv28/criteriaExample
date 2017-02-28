package com.kpit.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kpit.pojo.ExamResult;
import com.kpit.pojo.MasterStudent;
import com.kpit.util.HibernateSessionFactory;
import com.kpit.vo.StudentDataVo;

/**
 * get the student details of exam result
 * 
 * @author sachinc3
 *
 */
public class ExamResultDao {

	private static final Logger LOGGER = Logger.getLogger(ExamResultDao.class);

	/**
	 * @author sachinc3 get student id value
	 * @param studId
	 * @return studId
	 * @exception HibernateException
	 */
	public MasterStudent getStudId(int studId) {

		Session session = HibernateSessionFactory.openSession();
		MasterStudent studData = null;

		try {
			String hql = " FROM MasterStudent A WHERE A.studId =:Id ";
			Query querry = session.createQuery(hql);
			querry.setInteger("Id", studId);
			studData = (MasterStudent) querry.uniqueResult();
		} catch (HibernateException e) {
			LOGGER.error("Hibernate error in getting stud id", e);
		} 
		catch (NullPointerException e) {
			LOGGER.error("null pointer error in getting stud id", e);
		}finally {
			session.close();
		}

		return studData;
	}

	/**
	 * @author sachinc3 get the student exam details by mapping with student id
	 *         and store them into exam result table
	 * @param studentDataVos
	 * @exception HibernateException,
	 *                Exception
	 */
	public void getExamResultDetails(List<StudentDataVo> studentDataVos) {

		Session hSession = HibernateSessionFactory.openSession();

		try {

			Transaction t1 = hSession.beginTransaction();
			for (StudentDataVo s1 : studentDataVos) {
				ExamResult data = new ExamResult();
				data.setMasterStudent(getStudId(s1.getStudent_id()));
				data.setSub1(s1.getSub1());
				data.setSub2(s1.getSub2());
				data.setSub3(s1.getSub3());
				data.setResultStatus(s1.getResult_status());
				data.setResult(s1.getResult());
				hSession.save(data);
			}
			t1.commit();
		} catch (HibernateException e) {

			LOGGER.error("Hibernate error in inserting exam details with stud id to the data base ", e);
		} catch (NullPointerException e) {
			LOGGER.error(" null pointer error in inserting exam details to the data base ", e);
		} finally {
			hSession.close();
		}
	}
}
