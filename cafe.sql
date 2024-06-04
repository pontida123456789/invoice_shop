-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 04, 2024 at 04:33 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 7.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `invoice_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `time_print_invoice` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`invoice_id`, `user_id`, `total_price`, `time_print_invoice`) VALUES
(26, 7, 938.34, '2024-06-03 09:09:47'),
(27, 7, 1019.07, '2024-06-03 09:10:06'),
(28, 7, 1019.07, '2024-06-03 09:10:22'),
(29, 7, 3859.83, '2024-06-03 09:48:29'),
(30, 7, 0, '2024-06-03 11:48:54'),
(31, 7, 1420.38, '2024-06-03 12:03:39');

-- --------------------------------------------------------

--
-- Table structure for table `invoice_datail`
--

CREATE TABLE `invoice_datail` (
  `invoice_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `Qty` int(11) NOT NULL,
  `price_per_list` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `invoice_datail`
--

INSERT INTO `invoice_datail` (`invoice_id`, `product_id`, `Qty`, `price_per_list`) VALUES
(26, 1, 1, 150),
(26, 6, 1, 15),
(26, 43, 1, 120),
(26, 45, 1, 139),
(26, 46, 1, 189),
(26, 47, 1, 189),
(27, 1, 1, 150),
(27, 6, 1, 15),
(27, 44, 1, 189),
(27, 45, 1, 139),
(27, 46, 1, 189),
(27, 47, 1, 189),
(28, 43, 1, 120),
(28, 45, 1, 139),
(28, 46, 2, 378),
(28, 48, 2, 138),
(29, 1, 6, 900),
(29, 6, 6, 90),
(29, 43, 4, 480),
(29, 45, 5, 695),
(29, 46, 3, 567),
(29, 47, 3, 567),
(30, 1, 1, 150),
(30, 6, 3, 45),
(30, 44, 1, 189),
(30, 45, 1, 139),
(30, 46, 2, 378),
(30, 47, 1, 189),
(31, 1, 1, 150),
(31, 6, 2, 30),
(31, 43, 1, 120),
(31, 44, 1, 189),
(31, 45, 2, 278),
(31, 46, 1, 189),
(31, 47, 1, 189),
(31, 48, 1, 69);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `product_name` text COLLATE utf8_bin NOT NULL,
  `product_price` double NOT NULL,
  `product_capable` int(11) NOT NULL,
  `product_Image` text COLLATE utf8_bin NOT NULL,
  `username` text COLLATE utf8_bin NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_name`, `product_price`, `product_capable`, `product_Image`, `username`, `time`) VALUES
(1, 'Chocolate cake', 150, 6, '/image/cake_chocolate.jpg', '1234', '2024-06-03 12:03:39'),
(2, 'Orange cake', 180, 0, '/image/cake_orange.jpg', '1234', '2024-04-07 00:12:50'),
(5, 'Macarong', 25, 11, '/image/macarong.jpg', '1234', '2024-06-03 12:04:30'),
(6, 'Browny', 15, 45, '/image/browny.jpeg', '1234', '2024-06-03 12:03:39'),
(43, 'Donut set', 120, 12, '/image/Donut.jpg', '1234', '2024-06-03 12:03:39'),
(44, 'Mango Bingsoo', 189, 23, '/image/Mango_Bingsoo.jpg', '1234', '2024-06-03 12:03:39'),
(45, 'Fluffy Waffle', 139, 12, '/image/Fluffy_waffle.jpg', '1234', '2024-06-03 12:03:39'),
(46, 'Happy set', 189, 0, '/image/Happy_set.jpg', '1234', '2024-06-03 12:04:53'),
(47, 'Strawbwrry_Bingsoo', 189, 19, '/image/strawberry_bigsoo.jpg', '1234', '2024-06-03 12:03:39'),
(48, 'Mango sticky rice', 69, 28, '/image/Mango_stickyIrice.jpeg', '1234', '2024-06-03 12:03:39');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` text COLLATE utf8_bin NOT NULL,
  `password` text COLLATE utf8_bin NOT NULL,
  `status` enum('user','admin') COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `status`) VALUES
(3, 'Eye', '1234', 'admin'),
(7, '1234', '1234', 'admin'),
(11, 'Boom', '1234', 'user'),
(12, 'Pontida', '1234', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`invoice_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `invoice_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
