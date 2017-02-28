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
 * Fees generated by hbm2java
 */
@Entity
@Table(name = "fees", catalog = "student_info")
public class Fees implements java.io.Serializable {

	private Integer feeId;
	private MasterStudent masterStudent;
	private String fee;
	private double otherCost;

	public Fees() {
	}

	public Fees(MasterStudent masterStudent, String fee, double otherCost) {
		this.masterStudent = masterStudent;
		this.fee = fee;
		this.otherCost = otherCost;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Fee_id", unique = true, nullable = false)
	public Integer getFeeId() {
		return this.feeId;
	}

	public void setFeeId(Integer feeId) {
		this.feeId = feeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Stud_Id", nullable = false)
	public MasterStudent getMasterStudent() {
		return this.masterStudent;
	}

	public void setMasterStudent(MasterStudent masterStudent) {
		this.masterStudent = masterStudent;
	}

	@Column(name = "Fee", nullable = false)
	public String getFee() {
		return this.fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	@Column(name = "Other_Cost", nullable = false, precision = 22, scale = 0)
	public double getOtherCost() {
		return this.otherCost;
	}

	public void setOtherCost(double otherCost) {
		this.otherCost = otherCost;
	}

	@Override
	public String toString() {
		return "Fees [feeId=" + feeId + ", fee=" + fee + ", otherCost=" + otherCost + "]";
	}
}