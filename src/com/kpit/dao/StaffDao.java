package com.kpit.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kpit.pojo.DeptName;
import com.kpit.pojo.Staff;
import com.kpit.util.HibernateSessionFactory;
import com.kpit.vo.StudentDataVo;

import readExcel.StudentDataOperations;

/**
 * get all the staff details
 * 
 * @author sachinc3
 *
 */
public class StaffDao {

	private static final Logger LOGGER = Logger.getLogger(StaffDao.class);

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

	public void insertIntoStaff() {

		Session hSession = HibernateSessionFactory.openSession();
		try {
			Transaction t1 = hSession.beginTransaction();

			List<StudentDataVo> s1 = StudentDataOperations.getStudentBeanList();
			Staff data = null;

			for (StudentDataVo st : s1) {

				String hql = " FROM Staff A WHERE A.hodName =:name ";
				Query querry = hSession.createQuery(hql);
				querry.setString("name", st.getHod());
				data = (Staff) querry.uniqueResult();

				if (data == null) {

					Staff sd1 = new Staff();
					sd1.setDeptName(getDeptName(st.getDept()));
					sd1.setHodName(st.getHod());
					sd1.setHodSalary(st.getHod_salary());
					// d1.setDeptName(dept.getDept());
					hSession.save(sd1);
				}
			}
			t1.commit();
		} catch (HibernateException e) {
			LOGGER.error("Hibernate error in getting dept details", e);
		} catch (NullPointerException e) {
			LOGGER.error("null pointer error in getting dept details", e);
		}
	}
}
