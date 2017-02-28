package com.kpit.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kpit.pojo.DeptName;
import com.kpit.util.HibernateSessionFactory;
import com.kpit.vo.StudentDataVo;

import readExcel.StudentDataOperations;

/**
 * 
 * @author sachinc3 This class accept dept id and name and store them into DB
 */
public class DeptDao {

	private static final Logger LOGGER = Logger.getLogger(DeptDao.class);

	/**
	 * @author sachinc3 get dept id and name and set them into DB
	 * @exception HibernateException,
	 *                NullPointerException
	 */
	public void insertIntoDeptName() {

		Session hSession = HibernateSessionFactory.openSession();
		try {
			Transaction t1 = hSession.beginTransaction();

			List<StudentDataVo> s1 = StudentDataOperations.getStudentBeanList();
			DeptName deptData = null;

			for (StudentDataVo dept : s1) {

				String hql = " FROM DeptName A WHERE A.deptName =:name ";
				Query querry = hSession.createQuery(hql);
				querry.setString("name", dept.getDept());
				deptData = (DeptName) querry.uniqueResult();

				if (deptData == null) {

					DeptName d1 = new DeptName();
					d1.setDeptName(dept.getDept());
					hSession.save(d1);
				}
			}
			t1.commit();
		} catch (HibernateException e) {
			LOGGER.error("Hibernate error in getting dept details", e);
		} catch (NullPointerException e) {
			LOGGER.error("null pointer error in getting dept details", e);
		}
		finally {
			hSession.close();
		}
	}

}
