package com.kpit.pojo;

// Generated Dec 22, 2016 5:10:09 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Staff generated by hbm2java
 */
@Entity
@Table(name = "staff", catalog = "student_info")
public class Staff implements java.io.Serializable {

	private Integer staffId;
	private DeptName deptName;
	private String hodName;
	private String hodSalary;

	public Staff() {
	}

	public Staff(DeptName deptName, String hodName, String hodSalary) {
		this.deptName = deptName;
		this.hodName = hodName;
		this.hodSalary = hodSalary;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Staff_Id", unique = true, nullable = false)
	public Integer getStaffId() {
		return this.staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Dept_Id", nullable = false)
	public DeptName getDeptName() {
		return this.deptName;
	}

	public void setDeptName(DeptName deptName) {
		this.deptName = deptName;
	}

	@Column(name = "HOD_Name", nullable = false)
	public String getHodName() {
		return this.hodName;
	}

	public void setHodName(String hodName) {
		this.hodName = hodName;
	}

	@Column(name = "HOD_Salary", nullable = false)
	public String getHodSalary() {
		return this.hodSalary;
	}

	public void setHodSalary(String hodSalary) {
		this.hodSalary = hodSalary;
	}

	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", hodName=" + hodName + ", hodSalary=" + hodSalary + "]";
	}

}
