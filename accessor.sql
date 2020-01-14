-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 14, 2019 at 01:27 PM
-- Server version: 5.7.24
-- PHP Version: 7.3.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `accessor`
--

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `department_id` int(11) NOT NULL,
  `prefix` varchar(3) COLLATE utf8_polish_ci NOT NULL,
  `name` varchar(32) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`department_id`, `prefix`, `name`) VALUES
(1257, 'FIN', 'Financial'),
(1258, 'P', 'Personal'),
(1259, 'PRO', 'Production'),
(1260, 'IT', 'Information Technology');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `first_name` varchar(32) COLLATE utf8_polish_ci NOT NULL,
  `last_name` varchar(32) COLLATE utf8_polish_ci NOT NULL,
  `login` varchar(32) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(48) COLLATE utf8_polish_ci NOT NULL,
  `access_rights` varchar(64) COLLATE utf8_polish_ci DEFAULT NULL,
  `admin_rights` varchar(64) COLLATE utf8_polish_ci DEFAULT NULL,
  `class` varchar(8) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `first_name`, `last_name`, `login`, `password`, `access_rights`, `admin_rights`, `class`) VALUES
(3977, 'Grzegorz', 'Słodowy', 'GRZSLO', '705a191f6252cf78a568888f79343e5a', 'IT.2', 'P.3_FIN.3_IT.3_PRO.3', 'Admin'),
(3978, 'Kaja', 'Kozłowska', 'KAJKOZ', '9eae7c0f60c1b3d50bb36431853b1b3f', 'P.3_FIN.2_IT.1', 'P.3', 'Boss'),
(3979, 'Grzegorz', 'Przykładek', 'GRZPRZ', 'f84c767caf08808186b2ee4b2349bcc5', 'IT.3_FIN.2_P.2', 'IT.3', 'Boss'),
(3980, 'Tomasz', 'Psikuta', 'TOMPSI', '6e05e5d3e661df714284f55ab46da5ca', 'FIN.3_P.2_IT.1', 'FIN.3', 'Boss'),
(3981, 'Henryk', 'Sienkiewicz', 'HENSIE', '1c44b2edd433f11d05ede51f181b816d', 'PRO.3_FIN.2_P.2', 'PRO.3', 'Boss'),
(3982, 'Eliza', 'Orzeszkowa', 'ELIORZ', 'a9e17f5e404b56d85368d0249cadf024', 'FIN.2', 'FIN.2', 'User'),
(3983, 'Aleksander', 'Hamilton', 'ALEHAM', '7c9487c24cb4d708ecfbbdec8ce4ec9a', 'PRO.2_FIN.1', 'PRO.2', 'User'),
(3984, 'Aaron', 'Burr', 'AARBUR', '533e09952c82000bddd8584454459a3e', 'PRO.2', 'PRO.1', 'User'),
(3985, 'Andżelika', 'Schyler', 'ANDSCH', '701e12e362311bbade7212f13703f901', 'FIN.1_P.2', 'P.1', 'User'),
(3986, 'Conrad', 'Duma', 'CONDUM', '79a517d294fb8ea2df96af8ef9de806b', 'PRO.1', '', 'User'),
(3987, 'Paweł', 'Komarewicz', 'PAWKOM', '6634b9adc9b4a7ba05b72f1d65e0d8ef', 'FIN.1', '', 'User'),
(3988, 'Hanna', 'Kochanna', 'HANKOC', 'f723289e4341d08b763c7fce4db1c964', '', '', 'User');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`department_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `department_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1261;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3989;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
