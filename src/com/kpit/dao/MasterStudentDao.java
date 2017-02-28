package com.kpit.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kpit.pojo.DeptName;
import com.kpit.pojo.MasterStudent;
import com.kpit.util.HibernateSessionFactory;
import com.kpit.vo.StudentDataVo;

/**
 * get the student details
 * 
 * @author sachinc3
 *
 */
public class MasterStudentDao {

	private static final Logger LOGGER = Logger.getLogger(MasterStudentDao.class);

	/**
	 * @author sachinc3 get the dept id through HQL query
	 * @param deptName
	 * @return dept id
	 * @exception HibernateException
	 */
	public DeptName getDeptName(String deptName) {

		Session session = HibernateSessionFactory.openSession();
		DeptName studData = null;

		try {
			String hql = " FROM DeptName A WHERE A.deptName =:name ";
			Query querry = session.createQuery(hql);
			querry.setString("name", deptName);
			studData = (DeptName) querry.uniqueResult();
		} catch (HibernateException e) {
			LOGGER.error("Hibernate error in getting dept name", e);
		} 
		 catch (NullPointerException e) {
				LOGGER.error("null pointer error in getting dept name", e);
			}finally {
			session.close();
		}

		return studData;
	}

	/**
	 * @author sachinc3 get the master stud details and store them into
	 *         master_student table
	 * @param studentDataVos
	 * @exception HibernateException,
	 *                Exception
	 */
	public void getMasterStudDetails(List<StudentDataVo> studentDataVos) {

		Session hSession = HibernateSessionFactory.openSession();

		try {

			Transaction t1 = hSession.beginTransaction();
			MasterStudent data = null;
			for (StudentDataVo s1 : studentDataVos) {
				data = new MasterStudent(s1.getStudent_id(), getDeptName(s1.getDept()), s1.getName(), s1.getAddress(),
						s1.getEmail());

				hSession.save(data);
			}
			t1.commit();
		} catch (HibernateException e) {

			LOGGER.error("Hibernate error in inserting student details with dept to the data base ", e);
		} catch (NullPointerException e) {
			LOGGER.error(" error in inserting student details to the data base ", e);
		} finally {
			hSession.close();
		}
	}

}