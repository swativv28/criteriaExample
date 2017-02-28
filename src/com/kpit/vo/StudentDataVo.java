package com.kpit.vo;

/**
 * this class get all the values from excel sheet
 * 
 * @author sachinc3
 *
 */
public class StudentDataVo {

	private String name;
	private String address;
	private String email;
	private int student_id;
	private String deptName;
	private String hod;
	private String hod_salary;
	private double other_cost;
	private String fee;
	private double sub1;
	private double sub2;
	private double sub3;
	private String result_status;
	private double result;
	private String is_participating;

	public StudentDataVo() {

	}

	/**
	 * parameterized constructor
	 * 
	 * @param name
	 * @param address
	 * @param email
	 * @param student_id
	 * @param deptName
	 * @param hod
	 * @param hod_salary
	 * @param other_cost
	 * @param fee
	 * @param sub1
	 * @param sub2
	 * @param sub3
	 * @param result_status
	 * @param result
	 * @param is_participating
	 */
	public StudentDataVo(String name, String address, String email, int student_id, String deptName, String hod,
			String hod_salary, double other_cost, String fee, double sub1, double sub2, double sub3,
			String result_status, double result, String is_participating) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.student_id = student_id;
		this.deptName = deptName;
		this.hod = hod;
		this.hod_salary = hod_salary;
		this.other_cost = other_cost;
		this.fee = fee;
		this.sub1 = sub1;
		this.sub2 = sub2;
		this.sub3 = sub3;
		this.result_status = result_status;
		this.result = result;
		this.is_participating = is_participating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getDept() {
		return deptName;
	}

	public void setDept(String deptName) {
		this.deptName = deptName;
	}

	public String getHod() {
		return hod;
	}

	public void setHod(String hod) {
		this.hod = hod;
	}

	public String getHod_salary() {
		return hod_salary;
	}

	public void setHod_salary(String hod_salary) {
		this.hod_salary = hod_salary;
	}

	public double getOther_cost() {
		return other_cost;
	}

	public void setOther_cost(double other_cost) {
		this.other_cost = other_cost;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public double getSub1() {
		return sub1;
	}

	public void setSub1(double sub1) {
		this.sub1 = sub1;
	}

	public double getSub2() {
		return sub2;
	}

	public void setSub2(double sub2) {
		this.sub2 = sub2;
	}

	public double getSub3() {
		return sub3;
	}

	public void setSub3(double sub3) {
		this.sub3 = sub3;
	}

	public String getResult_status() {
		return result_status;
	}

	public void setResult_status(String result_status) {
		this.result_status = result_status;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public String getIs_participating() {
		return is_participating;
	}

	public void setIs_participating(String is_participating) {
		this.is_participating = is_participating;
	}

}
