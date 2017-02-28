package com.kpit.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.kpit.constants.Constants;

/**
 * PropertyReader.java
 * 
 * @author sachinc3 this class read the property file path and load it
 */
public class PropertyReader {

	private static Properties propertiesObj;
	private static final Logger LOGGER = Logger.getLogger(PropertyReader.class);

	/**
	 * trying to load its property file path
	 * 
	 * @param propFilePath
	 * @throws Exception
	 *             FileNotFoundException, IOException
	 */
	public static void readProperties(String propFilePath) throws Exception {

		propertiesObj = new Properties();
		try {
			propertiesObj.load(new FileInputStream(propFilePath));
		} catch (FileNotFoundException fileEx) {
			throw new Exception("File not found Please insert valid property file path");
		} catch (IOException ioEx) {
			throw new Exception("IO Exception occured in reading the properties file so exiting from the application");
		}
	}

	/**
	 * this method returns property value
	 * 
	 * @param property
	 * @return value
	 */
	public static String getPropertyVal(final String property) {

		final String value = String.valueOf(propertiesObj.getProperty(property));
		if (value == null) {
			LOGGER.info("Please insert date in properties file");
			System.exit(1);
		}
		return value;
	}

	public void checkPropFilePath(String args) throws Exception {

		if (PropertyReader.getPropertyVal(Constants.FILE_NAME).trim().isEmpty()) {
			throw new Exception("Provide valid path and name of Excel sheet to read  in config.properties");

		}

	}
}
