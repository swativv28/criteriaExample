-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 28, 2017 at 10:42 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `student_info`
--

-- --------------------------------------------------------

--
-- Table structure for table `dept_name`
--

CREATE TABLE `dept_name` (
  `Dept_Id` int(11) NOT NULL,
  `Dept_Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dept_name`
--

INSERT INTO `dept_name` (`Dept_Id`, `Dept_Name`) VALUES
(4, 'IT'),
(5, 'CS'),
(6, 'ExTc');

-- --------------------------------------------------------

--
-- Table structure for table `exam_result`
--

CREATE TABLE `exam_result` (
  `Result_Id` int(11) NOT NULL,
  `Sub1` double NOT NULL,
  `Sub2` double NOT NULL,
  `Sub3` double NOT NULL,
  `Result` double NOT NULL,
  `Result_Status` varchar(255) NOT NULL,
  `Stud_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `exam_result`
--

INSERT INTO `exam_result` (`Result_Id`, `Sub1`, `Sub2`, `Sub3`, `Result`, `Result_Status`, `Stud_Id`) VALUES
(1, 30, 30, 30, 30, 'Failed', 1001),
(2, 33.33, 20, 60, 37.776666666666664, 'Failed', 1002),
(3, 45, 56, 100, 67, 'Pass', 2001),
(4, 56, 40, 40, 45.33333333333333, 'Pass', 2002),
(5, 70, 87.98, 90, 82.66000000000001, 'Pass', 2003),
(6, 30, 30, 30, 30, 'Failed', 2004),
(7, 33.33, 20, 60, 37.776666666666664, 'Failed', 2005),
(8, 45, 56, 100, 67, 'Pass', 1006),
(9, 34, 40, 40, 38, 'Failed', 1003),
(10, 70, 87.98, 90, 82.66000000000001, 'Pass', 1004),
(11, 30, 30, 30, 30, 'Failed', 1005),
(12, 33.33, 70, 50, 51.11, 'Pass', 3001),
(13, 45, 56, 98, 66.33333333333333, 'Pass', 3002),
(14, 40, 40, 40, 40, 'Pass', 3003),
(15, 70, 87.98, 90, 82.66000000000001, 'Pass', 3004),
(16, 33.33, 20, 60, 37.776666666666664, 'Failed', 3005),
(17, 45, 56, 100, 67, 'Pass', 3006),
(18, 34, 40, 40, 38, 'Failed', 3007),
(19, 70, 87.98, 90, 82.66000000000001, 'Pass', 3008);

-- --------------------------------------------------------

--
-- Table structure for table `fees`
--

CREATE TABLE `fees` (
  `Fee_id` int(11) NOT NULL,
  `Fee` varchar(255) NOT NULL,
  `Other_Cost` double NOT NULL,
  `Stud_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fees`
--

INSERT INTO `fees` (`Fee_id`, `Fee`, `Other_Cost`, `Stud_Id`) VALUES
(1, '80 lacs', 0.3, 1001),
(2, '10 lacs', 0.3, 1002),
(3, '67 lacs', 0.1, 2001),
(4, '7.4 lacs', 0.1, 2002),
(5, '84 lacs', 0.1, 2003),
(6, '85 lacs', 0.1, 2004),
(7, '86 lacs', 0.1, 2005),
(8, '87 lacs', 0.3, 1006),
(9, '70lacs', 0.3, 1003),
(10, '89 lacs', 0.3, 1004),
(11, '90 lacs', 0.3, 1005),
(12, '91 lacs', 1.8, 3001),
(13, '93.8 lacs', 1.8, 3002),
(14, '93 lacs', 1.8, 3003),
(15, '4 lacs', 1.8, 3004),
(16, '95 lacs', 1.8, 3005),
(17, '96.3 lacs', 1.8, 3006),
(18, '97 lacs', 1.8, 3007),
(19, '98 lacs', 1.8, 3008);

-- --------------------------------------------------------

--
-- Table structure for table `master_student`
--

CREATE TABLE `master_student` (
  `Stud_Id` int(11) NOT NULL,
  `Stud_Name` varchar(255) NOT NULL,
  `Stud_Address` varchar(255) NOT NULL,
  `Stud_Email` varchar(255) NOT NULL,
  `Dept_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `master_student`
--

INSERT INTO `master_student` (`Stud_Id`, `Stud_Name`, `Stud_Address`, `Stud_Email`, `Dept_Id`) VALUES
(1001, 'Snehal   ', 'Pimpri', 'Snehal   @kpit.com', 4),
(1002, 'Taranath', 'shukravar peth', 'Taranath@kpit.com', 4),
(1003, 'rohan', 'shukravar peth', 'rohan@kpit.com', 4),
(1004, 'CHANDRA', 'Pimpri', 'CHANDRA@kpit.com', 4),
(1005, 'Sanjay', 'wakad', 'Sanjay@kpit.com', 4),
(1006, 'Karthik', 'hinjewadi phase 1', 'Karthik@kpit.com', 4),
(2001, 'Swati', 'sahakar nagar', 'Swati@kpit.com', 5),
(2002, 'Anjesh', 'wakad', 'Anjesh@kpit.com', 5),
(2003, 'Surya', 'Pimpri', 'Surya@kpit.com', 5),
(2004, 'Anand', 'hinjewadi phase 1', 'Anand@kpit.com', 5),
(2005, 'Jyoti', 'hinjewadi phase 1', 'Jyoti@kpit.com', 5),
(3001, 'Sheela', 'sahakar nagar', 'Sheela@kpit.com', 6),
(3002, 'Nutan', 'Pimpri', 'Nutan@kpit.com', 6),
(3003, 'Nilam', 'shukravar peth', 'Nilam@kpit.com', 6),
(3004, 'Gaurav', 'sahakar nagar', 'Gaurav@kpit.com', 6),
(3005, 'Pooja', 'wakad', 'Pooja@kpit.com', 6),
(3006, 'Shalaka', 'Pimpri', 'Shalaka@kpit.com', 6),
(3007, 'Rajesh', 'shukravar peth', 'Rajesh@kpit.com', 6),
(3008, 'Samir', 'Pimpri', 'Samir@kpit.com', 6);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `Staff_Id` int(11) NOT NULL,
  `HOD_Name` varchar(255) NOT NULL,
  `HOD_Salary` varchar(255) NOT NULL,
  `Dept_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`Staff_Id`, `HOD_Name`, `HOD_Salary`, `Dept_Id`) VALUES
(4, 'Gode', '10.1lacs', 4),
(5, 'VirendraSing', '4.5lacs', 5),
(6, 'Khandekar', '7.8lacs', 6);

-- --------------------------------------------------------

--
-- Table structure for table `stud_event_participation`
--

CREATE TABLE `stud_event_participation` (
  `Stud_Event_Id` int(11) NOT NULL,
  `Event_Participation` varchar(255) NOT NULL,
  `Stud_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stud_event_participation`
--

INSERT INTO `stud_event_participation` (`Stud_Event_Id`, `Event_Participation`, `Stud_Id`) VALUES
(1, 'Yes', 1001),
(2, 'Yes', 1002),
(3, 'Yes', 2001),
(4, 'Yes', 2002),
(5, 'Yes', 2003),
(6, 'Yes', 2004),
(7, 'Yes', 2005),
(8, 'No', 1006),
(9, 'No', 1003),
(10, 'Yes', 1004),
(11, 'Yes', 1005),
(12, 'Yes', 3001),
(13, 'No', 3002),
(14, 'No', 3003),
(15, 'Yes', 3004),
(16, 'Yes', 3005),
(17, 'Yes', 3006),
(18, 'No', 3007),
(19, 'No', 3008);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dept_name`
--
ALTER TABLE `dept_name`
  ADD PRIMARY KEY (`Dept_Id`);

--
-- Indexes for table `exam_result`
--
ALTER TABLE `exam_result`
  ADD PRIMARY KEY (`Result_Id`),
  ADD KEY `fk_studId` (`Stud_Id`);

--
-- Indexes for table `fees`
--
ALTER TABLE `fees`
  ADD PRIMARY KEY (`Fee_id`),
  ADD KEY `fk_fee` (`Stud_Id`);

--
-- Indexes for table `master_student`
--
ALTER TABLE `master_student`
  ADD PRIMARY KEY (`Stud_Id`),
  ADD KEY `Dept_Id` (`Dept_Id`) USING BTREE;

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`Staff_Id`),
  ADD KEY `fk_staff` (`Dept_Id`);

--
-- Indexes for table `stud_event_participation`
--
ALTER TABLE `stud_event_participation`
  ADD PRIMARY KEY (`Stud_Event_Id`),
  ADD KEY `fk_studevent` (`Stud_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dept_name`
--
ALTER TABLE `dept_name`
  MODIFY `Dept_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `exam_result`
--
ALTER TABLE `exam_result`
  MODIFY `Result_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `fees`
--
ALTER TABLE `fees`
  MODIFY `Fee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `Staff_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `stud_event_participation`
--
ALTER TABLE `stud_event_participation`
  MODIFY `Stud_Event_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `exam_result`
--
ALTER TABLE `exam_result`
  ADD CONSTRAINT `fk_studId` FOREIGN KEY (`Stud_Id`) REFERENCES `master_student` (`Stud_Id`);

--
-- Constraints for table `fees`
--
ALTER TABLE `fees`
  ADD CONSTRAINT `fk_fee` FOREIGN KEY (`Stud_Id`) REFERENCES `master_student` (`Stud_Id`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `fk_staff` FOREIGN KEY (`Dept_Id`) REFERENCES `dept_name` (`Dept_Id`);

--
-- Constraints for table `stud_event_participation`
--
ALTER TABLE `stud_event_participation`
  ADD CONSTRAINT `fk_studevent` FOREIGN KEY (`Stud_Id`) REFERENCES `master_student` (`Stud_Id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
