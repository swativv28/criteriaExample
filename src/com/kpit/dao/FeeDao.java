package com.kpit.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.kpit.pojo.Fees;
import com.kpit.pojo.MasterStudent;
import com.kpit.util.HibernateSessionFactory;
import com.kpit.vo.StudentDataVo;

/**
 * get the student fee details
 * 
 * @author sachinc3
 *
 */
public class FeeDao {

	private static final Logger LOGGER = Logger.getLogger(FeeDao.class);

	/**
	 * @author sachinc3 get the student id from HQL query
	 * @param studId
	 * @return studId
	 * @exception HibernateException
	 */
	public MasterStudent getStudId(int studId) {

		Session session = HibernateSessionFactory.openSession();
		MasterStudent studData = null;

		try {
			String hql = " FROM MasterStudent WHERE studId =:Id ";
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
	 * @author sachinc3 get the student fee details by mapping with student id
	 *         and store them into fee table
	 * @param studentDataVos
	 * @exception HibernateException,
	 *                Exception
	 */
	public void getFeeDetails(List<StudentDataVo> studentDataVos) {

		Session hSession = HibernateSessionFactory.openSession();

		try {

			Transaction t1 = hSession.beginTransaction();
			for (StudentDataVo s1 : studentDataVos) {
				Fees data = new Fees();
				data.setMasterStudent(getStudId(s1.getStudent_id()));
				data.setFee(s1.getFee());
				data.setOtherCost(s1.getOther_cost());
				hSession.save(data);
			}
			t1.commit();
		} catch (HibernateException e) {

			LOGGER.error("Hibernate error in inserting fee details with stud id to the data base ", e);
		} catch (NullPointerException e) {
			LOGGER.error(" error in inserting fee details to the data base ", e);
		} finally {
			hSession.close();
		}
	}

}
