package readExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.kpit.constants.Constants;
import com.kpit.util.PropertyReader;
import com.kpit.vo.StudentDataVo;

/**
 * 
 * @author sachinc3 This class performs the operations like reading of student
 *         data from excel file.
 *
 */
public class StudentDataOperations {
	private static final Logger LOGGER = Logger.getLogger(StudentDataOperations.class);

	private static XSSFWorkbook wb = null;
	private static List<StudentDataVo> studentList = null;

	/**
	 * @return studentBeanList- which contains all the student details
	 * @author sachinc3
	 * @exception filenotfoundException
	 *                - if the given input file is not found
	 * @exception null
	 *                pointer exception, IOException
	 * @return This function read all the data from the given excel file and
	 *         store all the data in studentBeanList
	 * 
	 */
	public void readData() {

		try {

			FileInputStream fis = new FileInputStream(new File(PropertyReader.getPropertyVal(Constants.FILE_NAME)));
			wb = new XSSFWorkbook(fis);
			XSSFSheet spreadsheet = wb.getSheetAt(0);
			StudentDataVo studentdatavo = null;
			studentList = new ArrayList<StudentDataVo>();

			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.configure("hibernate.cfg.xml");

			SessionFactory factory = cfg.buildSessionFactory();
			Session session = factory.openSession();

			for (Row row : spreadsheet) {

				if (row.getCell(0).getStringCellValue().equalsIgnoreCase("EXIT")) {
					break;

				} else {
					if (row.getRowNum() != 0) {
						studentdatavo = new StudentDataVo();
						studentdatavo.setName(row.getCell(0).getStringCellValue());
						studentdatavo.setAddress(row.getCell(1).getStringCellValue());
						studentdatavo.setEmail(row.getCell(2).getStringCellValue());
						studentdatavo.setStudent_id((int) row.getCell(3).getNumericCellValue());
						studentdatavo.setDept(row.getCell(4).getStringCellValue());
						studentdatavo.setHod(row.getCell(5).getStringCellValue());
						studentdatavo.setHod_salary(row.getCell(6).getStringCellValue());
						studentdatavo.setOther_cost(row.getCell(7).getNumericCellValue());
						studentdatavo.setFee(row.getCell(8).getStringCellValue());
						studentdatavo.setSub1(row.getCell(9).getNumericCellValue());
						studentdatavo.setSub2(row.getCell(10).getNumericCellValue());
						studentdatavo.setSub3(row.getCell(11).getNumericCellValue());
						studentdatavo.setResult_status(row.getCell(12).getStringCellValue());
						studentdatavo.setResult(row.getCell(13).getNumericCellValue());
						studentdatavo.setIs_participating(row.getCell(14).getStringCellValue());
						studentList.add(studentdatavo);
					}
				}
				fis.close();
			}
			session.close();
		} catch (FileNotFoundException e) {

			LOGGER.error("file not found if path does not exist", e);

		} catch (IOException e) {

			LOGGER.error(" error in getting values", e);
		} catch (NullPointerException e) {

			LOGGER.error("NullPointerException if sheet contains null values", e);
		}

	}

	/**
	 * @param List
	 *            of VO(value object student)
	 * @author sachinc3
	 * @return This class displays all the student details.
	 */
	public void printDetails(List<StudentDataVo> s1) {

		try {
			for (StudentDataVo studentdatavo : s1) {

				LOGGER.info(studentdatavo.getName() + "\t");
				LOGGER.info(studentdatavo.getAddress() + "\t");
				LOGGER.info(studentdatavo.getEmail() + "\t");
				LOGGER.info(studentdatavo.getStudent_id() + "\t");
				LOGGER.info(studentdatavo.getDept() + "\t");
				LOGGER.info(studentdatavo.getHod() + "\t");
				LOGGER.info(studentdatavo.getHod_salary() + "\t");
				LOGGER.info(studentdatavo.getOther_cost() + "\t");
				LOGGER.info(studentdatavo.getFee() + "\t");
				LOGGER.info(studentdatavo.getSub1() + "\t");
				LOGGER.info(studentdatavo.getSub2() + "\t");
				LOGGER.info(studentdatavo.getSub3() + "\t");
				LOGGER.info(studentdatavo.getResult_status() + "\t");
				LOGGER.info(studentdatavo.getResult() + "\t");
				LOGGER.info(studentdatavo.getIs_participating());
				LOGGER.info("\n\n");

			}
		} catch (Exception e) {
			LOGGER.error(" values are not retrived ", e);
		}
	}

	/**
	 * 
	 * @return studentList - contains all the student details
	 */
	public static List<StudentDataVo> getStudentBeanList() {
		return studentList;
	}

	public static void setStudentBeanList(List<StudentDataVo> studentBeanList) {
		StudentDataOperations.studentList = studentBeanList;
	}
}
