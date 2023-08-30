-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 30, 2023 at 12:31 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `edg_shoes`
--

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `id` varchar(10) NOT NULL,
  `shoes_id` varchar(10) NOT NULL,
  `user_id` varchar(10) NOT NULL,
  `model` varchar(100) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `color` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `payment` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`id`, `shoes_id`, `user_id`, `model`, `brand`, `color`, `price`, `quantity`, `payment`) VALUES
('T001', 'N002', 'U002', 'Sneakers', 'Nike', 'Black', 1200000, 1, 1500000),
('T002', 'V001', 'U002', 'Heels', 'Valentino', 'Black', 1150000, 1, 1200000),
('T003', 'N001', 'U002', 'Sneakers', 'Nike', 'White', 1500000, 1, 1500000),
('T004', 'N001', 'U002', 'Sneakers', 'Nike', 'White', 1500000, 2, 3000000);

-- --------------------------------------------------------

--
-- Table structure for table `shoes`
--

CREATE TABLE `shoes` (
  `id` varchar(10) NOT NULL,
  `model` varchar(100) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `color` varchar(100) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shoes`
--

INSERT INTO `shoes` (`id`, `model`, `brand`, `color`, `price`) VALUES
('A001', 'Sandals', 'Adidas', 'Black', 600000),
('N001', 'Sneakers', 'Nike', 'White', 1500000),
('N002', 'Sneakers', 'Nike', 'Black', 1200000),
('V001', 'Heels', 'Valentino', 'Black', 1150000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` varchar(10) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`, `gender`, `role`) VALUES
('U001', 'admin', 'admin@gmail.com', 'admin123', 'Male', 'admin'),
('U002', 'elyssa', 'elyssa@gmail.com', 'elys1234', 'Female', 'customer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `shoes_id` (`shoes_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `shoes`
--
ALTER TABLE `shoes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `invoice`
--
ALTER TABLE `invoice`
  ADD CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`shoes_id`) REFERENCES `shoes` (`id`),
  ADD CONSTRAINT `invoice_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
