package com.kpit.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.kpit.pojo.MasterStudent;
import com.kpit.pojo.StudEventParticipation;
import com.kpit.util.HibernateSessionFactory;
import com.kpit.vo.StudentDataVo;

/**
 * check whether stud participate in the event or not
 * 
 * @author sachinc3
 *
 */
public class StudEventParticipateDao {

	private static final Logger LOGGER = Logger.getLogger(StudEventParticipateDao.class);

	/**
	 * @author sachinc3 get stud id through HQL
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
			LOGGER.error("Null Pointer error in getting stud id", e);
		}finally {
			session.close();
		}

		return studData;
	}

	/**
	 * get the value of student participate or not in the event and store it
	 * into stud_event_participation
	 * 
	 * @param studentDataVos
	 * @exception HibernateException,
	 *                Exception
	 */
	public void getStudEventParticipateDetails(List<StudentDataVo> studentDataVos) {

		Session hSession = HibernateSessionFactory.openSession();

		try {

			Transaction t1 = hSession.beginTransaction();
			for (StudentDataVo s1 : studentDataVos) {
				StudEventParticipation data = new StudEventParticipation();
				data.setMasterStudent(getStudId(s1.getStudent_id()));
				data.setEventParticipation(s1.getIs_participating());
				hSession.save(data);
			}
			t1.commit();
		} catch (HibernateException e) {

			LOGGER.error(
					"Hibernate error in inserting student event participation details with stud id to the data base ",
					e);
		} catch (NullPointerException e) {
			LOGGER.error(" error in inserting student event participation details to the data base ", e);
		} finally {
			hSession.close();
		}
	}
}
