-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 06, 2024 at 05:09 PM
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
(1, 7, 2000.7, '2024-04-05 10:11:07'),
(2, 7, 1889.55, '2024-04-05 11:01:44'),
(3, 7, 1579.5, '2024-04-05 11:04:25'),
(4, 7, 2088.45, '2024-04-05 11:10:52'),
(5, 7, 3334.5, '2024-04-05 11:20:17'),
(6, 7, 877.5, '2024-04-05 12:45:47'),
(7, 7, 11583, '2024-04-05 12:47:06'),
(8, 7, 0, '2024-04-05 13:04:31'),
(9, 7, 175.5, '2024-04-05 13:05:28'),
(10, 7, 1105.65, '2024-04-05 21:25:28'),
(11, 7, 0, '2024-04-05 21:37:50'),
(12, 7, 0, '2024-04-05 21:39:00'),
(13, 7, 1399.32, '2024-04-05 21:47:25'),
(14, 7, 2007.72, '2024-04-05 21:50:50'),
(15, 7, 1480.05, '2024-04-05 22:18:14'),
(16, 7, 2457, '2024-04-05 22:22:22'),
(17, 7, 930.15, '2024-04-06 14:42:43');

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
(0, 1, 8, 1200),
(0, 5, 6, 150),
(0, 6, 11, 165),
(0, 35, 9, 1620),
(5, 1, 3, 450),
(5, 5, 6, 150),
(5, 6, 18, 270),
(5, 35, 11, 1980),
(6, 49, 5, 345),
(7, 50, 55, 3025),
(8, 46, 1, 189),
(9, 6, 2, 30),
(9, 43, 1, 120),
(10, 46, 5, 945),
(11, 45, 4, 556),
(12, 43, 1, 120),
(13, 1, 1, 150),
(13, 2, 1, 180),
(13, 5, 1, 25),
(13, 6, 1, 15),
(13, 43, 1, 120),
(13, 44, 1, 189),
(13, 45, 1, 139),
(13, 46, 1, 189),
(13, 47, 1, 189),
(14, 1, 4, 600),
(14, 5, 4, 100),
(14, 43, 3, 360),
(14, 45, 2, 278),
(14, 46, 2, 378),
(15, 1, 1, 150),
(15, 2, 1, 180),
(15, 5, 1, 25),
(15, 6, 1, 15),
(15, 43, 1, 120),
(15, 44, 1, 189),
(15, 45, 1, 139),
(15, 46, 1, 189),
(15, 47, 1, 189),
(15, 48, 1, 69),
(16, 1, 14, 2100),
(17, 45, 6, 834);

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
(1, 'Chocolate cake', 150, 30, 'C:\\Users\\eyeis\\eclipse-workspace\\testGUICAFE\\src\\image\\cake_chocolate.jpg', '1234', '2024-04-05 22:24:55'),
(2, 'Orange cake', 180, 48, 'C:\\Users\\eyeis\\eclipse-workspace\\testGUICAFE\\src\\image\\cake_orange.jpg', '1234', '2024-04-05 22:18:14'),
(5, 'Macarong', 25, 66, 'C:\\Users\\eyeis\\Downloads\\macarong.jpg', 'Eye', '2024-04-05 22:18:14'),
(6, 'Browny', 15, 61, 'C:\\Users\\eyeis\\Downloads\\browny.jpeg', '1234', '2024-04-05 22:18:14'),
(43, 'Donut set', 120, 23, 'C:\\Users\\eyeis\\Downloads\\Donut.jpg', '1234', '2024-04-05 22:18:14'),
(44, 'Mango Bingsoo', 189, 28, 'C:\\Users\\eyeis\\Downloads\\Mango_Bingsoo.jpg', '1234', '2024-04-05 22:18:14'),
(45, 'Fluffy Waffle', 139, 26, 'C:\\Users\\eyeis\\Downloads\\Fluffy_waffle.jpg', '1234', '2024-04-06 14:42:43'),
(46, 'Happy set', 189, 26, 'C:\\Users\\eyeis\\Downloads\\Happy_set.jpg', '1234', '2024-04-05 22:18:14'),
(47, 'Strawbwrry_Bingsoo', 189, 28, 'C:\\Users\\eyeis\\Downloads\\strawberry_bigsoo.jpg', '1234', '2024-04-05 22:18:14'),
(48, 'Mango sticky rice', 69, 34, 'C:\\Users\\eyeis\\Downloads\\Mango_stickyIrice.jpeg', '1234', '2024-04-05 22:18:14'),
(51, 'Matcha Bingsoo', 189, 30, 'C:\\Users\\eyeis\\Downloads\\Matcha-Bingsoo.jpg', '1234', '2024-04-05 22:37:59');

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
  MODIFY `invoice_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
