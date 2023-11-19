-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 19, 2023 at 10:08 AM
-- Server version: 10.3.39-MariaDB-cll-lve
-- PHP Version: 8.1.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hnguyenmanhidvn_DuAn1`
--

-- --------------------------------------------------------

--
-- Table structure for table `Bill`
--

CREATE TABLE `Bill` (
  `bill_ID` int(11) NOT NULL,
  `user_ID` int(11) NOT NULL DEFAULT 0,
  `amount` float NOT NULL DEFAULT 0,
  `billDate` datetime DEFAULT NULL,
  `note` varchar(150) DEFAULT NULL,
  `Ma_KH` int(11) DEFAULT NULL,
  `VCode` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Food`
--

CREATE TABLE `Food` (
  `dish_ID` int(11) NOT NULL,
  `name` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` float DEFAULT 0,
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isLocked` bit(1) NOT NULL DEFAULT b'0',
  `img` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Food`
--

INSERT INTO `Food` (`dish_ID`, `name`, `price`, `type`, `isLocked`, `img`) VALUES
(1, 'Pho Bo', 3.5, 'Food', b'0', 'phobo.jpg'),
(2, 'Banh Mi', 1, 'Food', b'0', 'banhmi.jpg'),
(3, 'Bun Cha', 4, 'Food', b'0', 'buncha.jpg'),
(4, 'Mi Xao', 3, 'Food', b'0', 'mixao.jpg'),
(5, 'Com Tam', 3.5, 'Food', b'0', 'comtam.jpg'),
(6, 'Banh Xeo', 3, 'Food', b'0', 'banhxeo.jpg'),
(7, 'Bun Bo Hue', 4.5, 'Food', b'0', 'bunbohue.jpg'),
(8, 'Goi Cuon', 2.5, 'Food', b'0', 'goicuon.jpg'),
(9, 'Banh Canh', 3.5, 'Food', b'0', 'banhcanh.jpg'),
(10, 'Ca Kho To', 4.5, 'Food', b'0', 'cakho.jpg'),
(11, 'Ca Phe Sua', 1.5, 'Drink', b'0', 'caphesua.jpg'),
(12, 'Tra Da', 0.5, 'Drink', b'0', 'trada.jpg'),
(13, 'Nuoc Mia', 1, 'Drink', b'0', 'nuocmia.jpg'),
(14, 'Sinh To Bo', 2, 'Drink', b'0', 'sinhtobo.jpg'),
(15, 'Bia', 1.5, 'Drink', b'0', 'bia.jpg'),
(16, 'Nuoc Cam', 1.5, 'Drink', b'0', 'nuoccam.jpg'),
(17, 'Soda Chanh', 1, 'Drink', b'0', 'sodachanh.jpg'),
(18, 'Tra Sen', 2, 'Drink', b'0', 'trasen.jpg'),
(19, 'Ca Phe Den', 1.5, 'Drink', b'0', 'capheden.jpg'),
(20, 'Nuoc Dua', 1.2, 'Drink', b'0', 'nuocdua.jpg'),
(21, 'Che Ba Mau', 2, 'Dessert', b'0', 'chebamau.jpg'),
(22, 'Banh Flan', 2.5, 'Dessert', b'0', 'banhflan.jpg'),
(23, 'Banh Bao Chi', 1.5, 'Dessert', b'0', 'banhbaochi.jpg'),
(24, 'Kem', 1.5, 'Dessert', b'0', 'kem.jpg'),
(25, 'Banh Trung Thu', 3, 'Dessert', b'0', 'banhtrungthu.jpg'),
(26, 'Banh Chuoi', 2, 'Dessert', b'0', 'banhchuoi.jpg'),
(27, 'Sua Chua', 1, 'Dessert', b'0', 'suachua.jpg'),
(28, 'Banh Pia', 2.5, 'Dessert', b'0', 'banhpia.jpg'),
(29, 'Tao Pho', 1.5, 'Dessert', b'0', 'taopho.jpg'),
(30, 'Banh Khoai Mi', 2, 'Dessert', b'0', 'banhkhoaimi.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `KhachHang`
--

CREATE TABLE `KhachHang` (
  `Ma_KH` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SDT` varchar(12) NOT NULL,
  `Diem` int(11) NOT NULL DEFAULT 0,
  `email` varchar(50) DEFAULT NULL,
  `birthDay` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `KhachHang`
--

INSERT INTO `KhachHang` (`Ma_KH`, `name`, `SDT`, `Diem`, `email`, `birthDay`) VALUES
(1, 'Nguyễn Văn A', '0912345678', 100, 'nguyenvana@gmail.com', '1980-01-01'),
(2, 'Trần Thị B', '0923456789', 200, 'tranthib@gmail.com', '1981-02-02'),
(3, 'Lê Văn C', '0934567890', 300, 'levanc@gmail.com', '1982-03-03'),
(4, 'Phạm Thị D', '0945678901', 400, 'phamthid@gmail.com', '1983-04-04'),
(5, 'Võ Văn E', '0956789012', 500, 'vovane@gmail.com', '1984-05-05'),
(6, 'Đặng Thị F', '0967890123', 600, 'dangthif@gmail.com', '1985-06-06'),
(7, 'Bùi Văn G', '0978901234', 700, 'buivang@gmail.com', '1986-07-07'),
(8, 'Ngô Thị H', '0989012345', 800, 'ngothih@gmail.com', '1987-08-08'),
(9, 'Dương Văn I', '0990123456', 900, 'duongvani@gmail.com', '1988-09-09'),
(10, 'Hoàng Thị J', '0901234567', 1000, 'hoangthij@gmail.com', '1989-10-10');

-- --------------------------------------------------------

--
-- Table structure for table `LoginHistory`
--

CREATE TABLE `LoginHistory` (
  `user_ID` int(11) NOT NULL,
  `login_time` datetime NOT NULL,
  `logout_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Order`
--

CREATE TABLE `Order` (
  `order_ID` int(11) NOT NULL,
  `bill_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `OrderDetails`
--

CREATE TABLE `OrderDetails` (
  `ID` int(11) NOT NULL,
  `quantity` int(11) DEFAULT 0,
  `order_ID` int(11) DEFAULT 0,
  `dish_ID` int(11) DEFAULT 0,
  `note` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Tables`
--

CREATE TABLE `Tables` (
  `table_ID` int(11) NOT NULL,
  `order_ID` int(11) DEFAULT NULL,
  `tableName` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `user_ID` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` bit(1) NOT NULL DEFAULT b'0',
  `isLocked` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`user_ID`, `username`, `password`, `role`, `isLocked`) VALUES
(1, 'user1', 'pass123', b'0', b'0'),
(2, 'user2', 'pass234', b'0', b'0'),
(3, 'user3', 'pass345', b'1', b'0'),
(4, 'user4', 'pass456', b'0', b'0'),
(5, 'user5', 'pass567', b'1', b'0'),
(6, 'user6', 'pass678', b'0', b'0'),
(7, 'user7', 'pass789', b'0', b'1'),
(8, 'user8', 'pass890', b'1', b'0'),
(9, 'user9', 'pass901', b'0', b'0'),
(10, 'user10', 'pass012', b'0', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `UserInfo`
--

CREATE TABLE `UserInfo` (
  `user_ID` int(11) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` datetime NOT NULL,
  `SDT` varchar(12) NOT NULL,
  `CCCD` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `UserInfo`
--

INSERT INTO `UserInfo` (`user_ID`, `address`, `name`, `birthday`, `SDT`, `CCCD`) VALUES
(1, '123 Le Loi, Quận 1, TP HCM', 'Alice', '1990-01-01 00:00:00', '0123456789', '001234567891'),
(2, '456 Tran Hung Dao, Quận 5, TP HCM', 'Bob', '1991-02-02 00:00:00', '0234567890', '002345678912'),
(3, '789 Nguyen Hue, Quận 1, TP HCM', 'Charlie', '1992-03-03 00:00:00', '0345678901', '003456789013'),
(4, '101 Bach Dang, Quận 3, TP HCM', 'Diana', '1993-04-04 00:00:00', '0456789012', '004567890124'),
(5, '202 Le Duan, Quận 1, TP HCM', 'Ethan', '1994-05-05 00:00:00', '0567890123', '005678901235'),
(6, '303 Tran Phu, Quận 5, TP HCM', 'Fiona', '1995-06-06 00:00:00', '0678901234', '006789012346'),
(7, '404 Cach Mang Thang Tam, Quận 3, TP HCM', 'George', '1996-07-07 00:00:00', '0789012345', '007890123457'),
(8, '505 Pham Ngu Lao, Quận 1, TP HCM', 'Hannah', '1997-08-08 00:00:00', '0890123456', '008901234568'),
(9, '606 Hai Ba Trung, Quận 3, TP HCM', 'Ivan', '1998-09-09 00:00:00', '0901234567', '009012345679'),
(10, '707 Le Thanh Ton, Quận 1, TP HCM', 'Julia', '1999-10-10 00:00:00', '1012345678', '010123456780');

-- --------------------------------------------------------

--
-- Table structure for table `Voucher`
--

CREATE TABLE `Voucher` (
  `VoucherID` int(11) NOT NULL,
  `discountPercent` decimal(5,2) NOT NULL DEFAULT 0.00,
  `VCode` varchar(25) NOT NULL,
  `expireDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Bill`
--
ALTER TABLE `Bill`
  ADD PRIMARY KEY (`bill_ID`),
  ADD KEY `user_ID` (`user_ID`);

--
-- Indexes for table `Food`
--
ALTER TABLE `Food`
  ADD PRIMARY KEY (`dish_ID`);

--
-- Indexes for table `KhachHang`
--
ALTER TABLE `KhachHang`
  ADD PRIMARY KEY (`Ma_KH`);

--
-- Indexes for table `LoginHistory`
--
ALTER TABLE `LoginHistory`
  ADD KEY `user_ID` (`user_ID`);

--
-- Indexes for table `Order`
--
ALTER TABLE `Order`
  ADD PRIMARY KEY (`order_ID`),
  ADD KEY `bill_ID` (`bill_ID`);

--
-- Indexes for table `OrderDetails`
--
ALTER TABLE `OrderDetails`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `dish_ID` (`dish_ID`),
  ADD KEY `order_ID` (`order_ID`);

--
-- Indexes for table `Tables`
--
ALTER TABLE `Tables`
  ADD PRIMARY KEY (`table_ID`),
  ADD KEY `order_ID` (`order_ID`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`user_ID`);

--
-- Indexes for table `UserInfo`
--
ALTER TABLE `UserInfo`
  ADD KEY `user_ID` (`user_ID`);

--
-- Indexes for table `Voucher`
--
ALTER TABLE `Voucher`
  ADD PRIMARY KEY (`VoucherID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Bill`
--
ALTER TABLE `Bill`
  MODIFY `bill_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `Food`
--
ALTER TABLE `Food`
  MODIFY `dish_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `KhachHang`
--
ALTER TABLE `KhachHang`
  MODIFY `Ma_KH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Order`
--
ALTER TABLE `Order`
  MODIFY `order_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `OrderDetails`
--
ALTER TABLE `OrderDetails`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Tables`
--
ALTER TABLE `Tables`
  MODIFY `table_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `user_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Voucher`
--
ALTER TABLE `Voucher`
  MODIFY `VoucherID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Bill`
--
ALTER TABLE `Bill`
  ADD CONSTRAINT `Bill_ibfk_3` FOREIGN KEY (`user_ID`) REFERENCES `User` (`user_ID`);

--
-- Constraints for table `LoginHistory`
--
ALTER TABLE `LoginHistory`
  ADD CONSTRAINT `LoginHistory_ibfk_1` FOREIGN KEY (`user_ID`) REFERENCES `User` (`user_ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `Order`
--
ALTER TABLE `Order`
  ADD CONSTRAINT `Order_ibfk_1` FOREIGN KEY (`bill_ID`) REFERENCES `Bill` (`bill_ID`);

--
-- Constraints for table `OrderDetails`
--
ALTER TABLE `OrderDetails`
  ADD CONSTRAINT `OrderDetails_ibfk_1` FOREIGN KEY (`dish_ID`) REFERENCES `Food` (`dish_ID`),
  ADD CONSTRAINT `OrderDetails_ibfk_2` FOREIGN KEY (`order_ID`) REFERENCES `Order` (`order_ID`);

--
-- Constraints for table `Tables`
--
ALTER TABLE `Tables`
  ADD CONSTRAINT `Tables_ibfk_1` FOREIGN KEY (`order_ID`) REFERENCES `Order` (`order_ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `UserInfo`
--
ALTER TABLE `UserInfo`
  ADD CONSTRAINT `userinfo_user` FOREIGN KEY (`user_ID`) REFERENCES `User` (`user_ID`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
