-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 25, 2024 at 09:57 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `beauty_shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `branchid` bigint(20) NOT NULL,
  `branch_name` varchar(255) NOT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `latitude` decimal(10,8) DEFAULT NULL,
  `location` varchar(255) NOT NULL,
  `longitude` decimal(11,8) DEFAULT NULL,
  `manager` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`branchid`, `branch_name`, `contact_number`, `latitude`, `location`, `longitude`, `manager`) VALUES
(1, 'Downtown Flagship Store', '+1 212-555-1234', NULL, '123 Main St, New York, NY 10001', NULL, 'Emily Johnson'),
(2, 'Westfield Mall Outlet', '+44 20 7123 4567', NULL, '400 Oxford St, London W1A 1AB, UK', NULL, 'James Smith'),
(3, 'Champs-Élysées Boutique', '+33 1 23 45 67 89', NULL, '101 Avenue des Champs-Élysées, 75008 Paris, France', NULL, 'Sophie Dubois'),
(4, 'Ginza Shopping Center', '+81 3-1234-5678', NULL, '4-5-6 Ginza, Chuo-ku, Tokyo 104-0061, Japan', NULL, 'Takashi Yamamoto'),
(5, 'Online Warehouse', '+1 206-555-9876', NULL, '789 E-commerce Way, Seattle, WA 98101', NULL, 'Michael Chang');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cartid` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `customerid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cart_item`
--

CREATE TABLE `cart_item` (
  `cart_itemid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `cartid` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `coupon`
--

CREATE TABLE `coupon` (
  `couponid` bigint(20) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount_type` varchar(255) NOT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `end_date` date NOT NULL,
  `is_active` bit(1) NOT NULL,
  `max_usage_limit` int(11) DEFAULT NULL,
  `minimum_order_value` decimal(10,2) DEFAULT NULL,
  `start_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerid` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `customer_type` varchar(255) DEFAULT NULL,
  `date_joined` date DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `last_login_date` date DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `preferred_language` varchar(255) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `security_answer_hash` varchar(255) DEFAULT NULL,
  `security_question` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `districts`
--

CREATE TABLE `districts` (
  `DistrictID` int(11) NOT NULL,
  `DistrictName` varchar(255) NOT NULL,
  `ProvinceID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `districts`
--

INSERT INTO `districts` (`DistrictID`, `DistrictName`, `ProvinceID`) VALUES
(1, 'Huyện Bình Tân', 2),
(2, 'Huyện Long Hồ', 2),
(3, 'Huyện Mang Thít', 2),
(4, 'Huyện Tam Bình', 2),
(5, 'Huyện Trà Ôn', 2),
(6, 'Huyện Vũng Liêm', 2),
(7, 'Thành Phố Vĩnh Long', 2),
(8, 'Thị Xã Bình Minh', 2);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `inventoryid` bigint(20) NOT NULL,
  `last_updated_date` date NOT NULL,
  `quantity` int(11) NOT NULL,
  `branchid` bigint(20) NOT NULL,
  `productid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`inventoryid`, `last_updated_date`, `quantity`, `branchid`, `productid`) VALUES
(1, '2024-09-24', 31, 1, 1),
(2, '2024-09-23', 25, 1, 2),
(3, '2024-09-23', 40, 1, 3),
(4, '2024-09-23', 50, 1, 4),
(5, '2024-09-23', 20, 1, 5),
(6, '2024-09-23', 25, 2, 1),
(7, '2024-09-23', 20, 2, 2),
(8, '2024-09-23', 30, 2, 6),
(9, '2024-09-23', 60, 2, 7),
(10, '2024-09-23', 35, 2, 8),
(11, '2024-09-23', 40, 3, 6),
(12, '2024-09-23', 15, 3, 9),
(13, '2024-09-23', 10, 3, 10),
(14, '2024-09-23', 30, 3, 11),
(15, '2024-09-23', 55, 3, 12),
(16, '2024-09-23', 40, 4, 13),
(17, '2024-09-23', 25, 4, 14),
(18, '2024-09-23', 45, 4, 15),
(19, '2024-09-23', 35, 4, 16),
(20, '2024-09-23', 20, 4, 17),
(21, '2024-09-23', 70, 5, 18),
(22, '2024-09-23', 50, 5, 19),
(23, '2024-09-23', 60, 5, 20),
(24, '2024-09-23', 45, 5, 1),
(25, '2024-09-23', 35, 5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `login_history`
--

CREATE TABLE `login_history` (
  `loginid` bigint(20) NOT NULL,
  `device_info` varchar(255) DEFAULT NULL,
  `ipaddress` varchar(255) DEFAULT NULL,
  `login_status` varchar(255) NOT NULL,
  `login_timestamp` datetime(6) NOT NULL,
  `customerid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `orderid` int(11) NOT NULL,
  `order_date` date DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_amount` double NOT NULL,
  `branchid` bigint(20) DEFAULT NULL,
  `couponid` bigint(20) DEFAULT NULL,
  `customerid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `order_detailid` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `orderid` int(11) NOT NULL,
  `productid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentid` bigint(20) NOT NULL,
  `amount_paid` decimal(10,2) NOT NULL,
  `payment_date` date NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `orderid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE `permission` (
  `permissionid` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`permissionid`, `description`, `permission_name`) VALUES
(1, 'Access to view dashboard', 'view_dashboard'),
(2, 'Create, edit, and delete user accounts', 'manage_users'),
(3, 'Add, edit, and remove products', 'manage_products'),
(4, 'View and process customer orders', 'manage_orders'),
(5, 'Update and track inventory levels', 'manage_inventory'),
(6, 'Create and manage promotional campaigns', 'manage_promotions'),
(7, 'Access to sales and inventory reports', 'view_reports'),
(8, 'Add and manage supplier information', 'manage_suppliers'),
(9, 'Process product returns and exchanges', 'manage_returns'),
(10, 'Access and manage customer information', 'manage_customer_data');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productid` int(11) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `stock_quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productid`, `brand`, `category`, `description`, `price`, `product_name`, `stock_quantity`) VALUES
(1, 'Lancôme', 'Makeup', 'Long-lasting foundation with SPF 15', 47, 'Teint Idole Ultra Wear Foundation', 100),
(2, 'Estée Lauder', 'Skincare', 'Anti-aging serum for all skin types', 75, 'Advanced Night Repair Serum', 80),
(3, 'Shiseido', 'Suncare', 'Water-resistant sunscreen for face and body', 49, 'Ultimate Sun Protector Lotion SPF 50+', 120),
(4, 'Olay', 'Skincare', 'Anti-aging moisturizer with peptides', 26.99, 'Regenerist Micro-Sculpting Cream', 150),
(5, 'Urban Decay', 'Makeup', '12 rose-hued neutral eyeshadows', 54, 'Naked3 Eyeshadow Palette', 75),
(6, 'Dior', 'Makeup', 'Luxurious lipstick in various finishes', 38, 'Rouge Dior Lipstick', 90),
(7, 'Neutrogena', 'Skincare', 'Lightweight gel moisturizer with hyaluronic acid', 19.99, 'Hydro Boost Water Gel', 200),
(8, 'Too Faced', 'Makeup', 'Volumizing and lengthening mascara', 26, 'Better Than Sex Mascara', 110),
(9, 'Cooluli', 'Accessories', 'Mini fridge for storing skincare products', 49.99, 'Skincare Fridge', 30),
(10, 'Dyson', 'Hair Care', 'High-speed intelligent hair dryer', 399.99, 'Supersonic Hair Dryer', 25),
(11, 'Dior', 'Makeup', 'Nourishing lip oil with color-reviving technology', 35, 'Lip Glow Oil', 85),
(12, 'CeraVe', 'Skincare', 'Daily face moisturizer for normal to dry skin', 16.99, 'Hydrating Face Cream', 180),
(13, 'Anastasia Beverly Hills', 'Makeup', 'Ultra-slim mechanical brow pencil', 23, 'Brow Wiz', 95),
(14, 'DHC', 'Skincare', 'Olive oil-based makeup remover', 28, 'Cleansing Oil', 70),
(15, 'Glossier', 'Makeup', 'Seamless cheek color', 18, 'Cloud Paint', 130),
(16, 'Glossier', 'Suncare', 'Daily sunscreen with SPF 35', 25, 'Invisible Shield Sunscreen', 110),
(17, 'Moroccanoil', 'Hair Care', 'Argan oil-infused hair treatment', 34, 'Moroccan Oil Treatment', 60),
(18, 'Clinique', 'Skincare', 'Dermatologist-developed face moisturizer', 29.5, 'Dramatically Different Moisturizing Lotion+', 140),
(19, 'Urban Decay', 'Makeup', 'Lightweight liquid foundation', 40, 'Naked Skin Weightless Foundation', 85),
(20, 'Laneige', 'Skincare', 'Leave-on lip mask with vitamin C and antioxidants', 22, 'Lip Sleeping Mask', 100);

-- --------------------------------------------------------

--
-- Table structure for table `provinces`
--

CREATE TABLE `provinces` (
  `ProvinceID` int(11) NOT NULL,
  `ProvinceName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `provinces`
--

INSERT INTO `provinces` (`ProvinceID`, `ProvinceName`) VALUES
(1, 'Cần Thơ'),
(2, 'Vĩnh Long');

-- --------------------------------------------------------

--
-- Table structure for table `purchase_order`
--

CREATE TABLE `purchase_order` (
  `purchase_orderid` bigint(20) NOT NULL,
  `order_date` date NOT NULL,
  `status` varchar(255) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `supplierid` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `purchase_order`
--

INSERT INTO `purchase_order` (`purchase_orderid`, `order_date`, `status`, `total_amount`, `supplierid`) VALUES
(1, '2024-09-11', 'Completed', 0.00, 1),
(2, '2024-09-24', 'Completed', 0.00, 2),
(3, '2024-09-24', 'Completed', 0.00, 3),
(4, '2024-09-07', 'Processing', 18350.00, 4),
(5, '2024-09-09', 'Completed', 8500.00, 5),
(6, '2024-09-11', 'In Transit', 19700.00, 6),
(7, '2024-09-13', 'Processing', 7500.00, 7),
(8, '2024-09-15', 'Completed', 11200.00, 8),
(9, '2024-09-17', 'In Transit', 11000.00, 9),
(10, '2024-09-04', 'Processing', 0.00, 10),
(11, '2024-09-21', 'Completed', 8900.00, 11),
(12, '2024-09-22', 'Processing', 13500.00, 12),
(13, '2024-09-23', 'In Transit', 19000.00, 13),
(14, '2024-09-24', 'Processing', 10500.00, 14),
(15, '2024-09-24', 'Processing', 17200.00, 15),
(16, '2024-08-15', 'Completed', 18500.00, 1),
(17, '2024-08-20', 'Completed', 21000.00, 2),
(18, '2024-08-25', 'Completed', 16800.00, 3),
(19, '2024-08-30', 'Completed', 13200.00, 4),
(20, '2024-09-02', 'Completed', 10800.00, 5),
(21, '2024-09-24', 'Processing', 0.00, 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchase_order_details`
--

CREATE TABLE `purchase_order_details` (
  `purchase_order_detailid` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `received_date` date DEFAULT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `productid` int(11) NOT NULL,
  `purchase_orderid` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `purchase_order_details`
--

INSERT INTO `purchase_order_details` (`purchase_order_detailid`, `quantity`, `received_date`, `unit_price`, `productid`, `purchase_orderid`) VALUES
(10, 600, '2024-09-24', 20.00, 4, 4),
(11, 250, '2024-09-24', 15.00, 7, 4),
(12, 200, '2024-09-03', 13.00, 12, 4),
(13, 200, '2024-09-18', 20.00, 4, 5),
(14, 300, '2024-09-18', 15.00, 7, 5),
(15, 150, NULL, 30.00, 6, 6),
(16, 200, NULL, 28.00, 11, 6),
(17, 300, NULL, 32.00, 19, 6),
(18, 100, NULL, 20.00, 8, 7),
(19, 200, NULL, 14.00, 15, 7),
(20, 150, NULL, 18.00, 20, 7),
(21, 400, '2024-09-22', 13.00, 12, 8),
(22, 300, '2024-09-22', 20.00, 16, 8),
(23, 150, NULL, 40.00, 3, 9),
(24, 200, NULL, 22.00, 14, 9),
(27, 300, '2024-09-23', 15.00, 7, 11),
(28, 200, '2024-09-23', 13.00, 12, 11),
(29, 100, NULL, 60.00, 2, 12),
(30, 250, NULL, 22.00, 18, 12),
(31, 400, NULL, 14.00, 15, 13),
(32, 250, NULL, 28.00, 17, 13),
(33, 200, NULL, 20.00, 8, 14),
(34, 250, NULL, 18.00, 13, 14),
(35, 150, NULL, 40.00, 5, 15),
(36, 300, NULL, 32.00, 19, 15),
(37, 200, '2024-08-25', 35.00, 1, 16),
(38, 150, '2024-08-25', 40.00, 5, 16),
(39, 200, '2024-08-30', 60.00, 2, 17),
(40, 250, '2024-08-30', 22.00, 18, 17),
(41, 250, '2024-09-04', 40.00, 3, 18),
(42, 200, '2024-09-04', 20.00, 16, 18),
(43, 400, '2024-09-09', 20.00, 4, 19),
(44, 300, '2024-09-09', 13.00, 12, 19),
(45, 300, '2024-09-11', 20.00, 4, 20),
(46, 350, '2024-09-11', 15.00, 7, 20);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `reviewid` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `rating` int(11) NOT NULL,
  `review_date` date NOT NULL,
  `customerid` int(11) NOT NULL,
  `productid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `roleid` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`roleid`, `description`, `role_name`) VALUES
(1, 'Full access to all system functions', 'Admin'),
(2, 'Manage store operations and staff', 'Store Manager'),
(3, 'Handle sales and customer service', 'Sales Associate'),
(4, 'Manage product inventory and stock', 'Inventory Manager'),
(5, 'Handle customer inquiries and issues', 'Customer Service Rep'),
(6, 'Manage promotions and marketing campaigns', 'Marketing Specialist'),
(7, 'Handle purchasing and supplier relations', 'Procurement Officer');

-- --------------------------------------------------------

--
-- Table structure for table `role_permission`
--

CREATE TABLE `role_permission` (
  `role_permissionid` int(11) NOT NULL,
  `permissionid` int(11) DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role_permission`
--

INSERT INTO `role_permission` (`role_permissionid`, `permissionid`, `roleid`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 1),
(6, 6, 1),
(7, 7, 1),
(8, 8, 1),
(9, 9, 1),
(10, 10, 1),
(11, 1, 2),
(12, 2, 2),
(13, 3, 2),
(14, 4, 2),
(15, 5, 2),
(16, 6, 2),
(17, 7, 2),
(18, 9, 2),
(19, 1, 3),
(20, 3, 3),
(21, 4, 3),
(22, 9, 3),
(23, 1, 4),
(24, 3, 4),
(25, 5, 4),
(26, 7, 4),
(27, 8, 4),
(28, 1, 5),
(29, 4, 5),
(30, 9, 5),
(31, 10, 5),
(32, 1, 6),
(33, 3, 6),
(34, 6, 6),
(35, 7, 6),
(36, 1, 7),
(37, 3, 7),
(38, 5, 7),
(39, 7, 7),
(40, 8, 7);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplierid` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contact_person` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `supplier_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplierid`, `address`, `contact_person`, `email`, `phone`, `supplier_name`) VALUES
(1, '14 Rue Royale, 75008 Paris, France', 'Sophie Dubois', 'contact@loreal.com', '+33 1 47 56 70 00', 'L\'Oréal Group'),
(2, '767 Fifth Avenue, New York, NY 10153, USA', 'John Smith', 'info@elcompanies.com', '+1 212-572-4200', 'Estée Lauder Companies'),
(3, '5-5, Ginza 7-chome, Chuo-ku, Tokyo 104-0061, Japan', 'Yuki Tanaka', 'customer.service@shiseido.co.jp', '+81 3-6218-5111', 'Shiseido Company, Limited'),
(4, '100 Victoria Embankment, London EC4Y 0DY, UK', 'Emma Brown', 'unilever.corporate@unilever.com', '+44 20 7822 5252', 'Unilever'),
(5, '1 P&G Plaza, Cincinnati, OH 45202, USA', 'Michael Johnson', 'pg.consumer.relations@pg.com', '+1 513-983-1100', 'Procter & Gamble'),
(6, '22 Avenue Montaigne, 75008 Paris, France', 'François Dupont', 'contact@lvmh.fr', '+33 1 44 13 22 22', 'LVMH Moët Hennessy Louis Vuitton'),
(7, '350 Fifth Avenue, New York, NY 10118, USA', 'Sarah Miller', 'info@coty.com', '+1 212-389-7300', 'Coty Inc.'),
(8, 'Unnastraße 48, 20245 Hamburg, Germany', 'Hans Schmidt', 'kontakt@beiersdorf.com', '+49 40 4909-0', 'Beiersdorf AG'),
(9, '1-14-10, Nihonbashi Kayabacho, Chuo-ku, Tokyo 103-8210, Japan', 'Hiroshi Yamamoto', 'kao_corp@kao.co.jp', '+81 3-3660-7111', 'Kao Corporation'),
(10, '100 Cheonggyecheon-ro, Jung-gu, Seoul, South Korea', 'Ji-soo Kim', 'csteam@amorepacific.com', '+82 2-709-5114', 'Amore Pacific'),
(11, 'Watersmead, Littlehampton, West Sussex, BN17 6LS, UK', 'Emily Green', 'customer.relations@thebodyshop.com', '+44 1903 844747', 'The Body Shop International Limited'),
(12, '9 rue du Commandant Pilot, 92200 Neuilly-sur-Seine, France', 'Marie Lefevre', 'info@clarins.com', '+33 1 56 60 63 72', 'Clarins Group'),
(13, 'Av. Alexandre Colares, 1188, São Paulo, SP, 05106-000, Brazil', 'Carlos Silva', 'relationamento@natura.net', '+55 11 4389-7000', 'Natura &Co'),
(14, '1 Avon Place, Suffern, NY 10901, USA', 'Lisa Thompson', 'investor.relations@avon.com', '+1 212-282-5000', 'Avon Products, Inc.'),
(15, '1 New York Plaza, New York, NY 10004, USA', 'David Martinez', 'contact_us@revlon.com', '+1 212-527-4000', 'Revlon, Inc.');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_roleid` int(11) NOT NULL,
  `customerid` int(11) DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wards`
--

CREATE TABLE `wards` (
  `WardID` int(11) NOT NULL,
  `WardName` varchar(255) NOT NULL,
  `DistrictID` int(11) NOT NULL,
  `Latitude` decimal(10,8) NOT NULL,
  `Longitude` decimal(11,8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`branchid`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cartid`),
  ADD UNIQUE KEY `UKajccgo6xiog4ypu5w91ss3skk` (`customerid`);

--
-- Indexes for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD PRIMARY KEY (`cart_itemid`),
  ADD KEY `FK4teld0nibbe4l2rucr2yybw2e` (`cartid`),
  ADD KEY `FK9pw5b35944xvbr3h8avlakscl` (`productid`);

--
-- Indexes for table `coupon`
--
ALTER TABLE `coupon`
  ADD PRIMARY KEY (`couponid`),
  ADD UNIQUE KEY `UKbg4p9ontpj7adq7yr71h93sdn` (`code`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerid`);

--
-- Indexes for table `districts`
--
ALTER TABLE `districts`
  ADD PRIMARY KEY (`DistrictID`),
  ADD KEY `ProvinceID` (`ProvinceID`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`inventoryid`),
  ADD KEY `FKcwqf7vauhw5kufw0wg8lgxxu5` (`branchid`),
  ADD KEY `FK3i7p043bjgl1s0y2ptrgfn78x` (`productid`);

--
-- Indexes for table `login_history`
--
ALTER TABLE `login_history`
  ADD PRIMARY KEY (`loginid`),
  ADD KEY `FKhultxghdbx5s9di9d364qq110` (`customerid`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`orderid`),
  ADD KEY `FK7dq91bw6e7g4fw66tet5w8k8t` (`branchid`),
  ADD KEY `FK2womuo8rw8uvn7lw1dc0jy1b6` (`couponid`),
  ADD KEY `FK1t7f2e7ygr5axjy3500yofr4s` (`customerid`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`order_detailid`),
  ADD KEY `FKlbxu5fj479do0s9ux84yms99s` (`orderid`),
  ADD KEY `FKjmdsv4sh1x4akwdtaibwspa0v` (`productid`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentid`),
  ADD UNIQUE KEY `UKbtr1t322wfhl7o89s277iuxch` (`orderid`);

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`permissionid`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productid`);

--
-- Indexes for table `provinces`
--
ALTER TABLE `provinces`
  ADD PRIMARY KEY (`ProvinceID`);

--
-- Indexes for table `purchase_order`
--
ALTER TABLE `purchase_order`
  ADD PRIMARY KEY (`purchase_orderid`),
  ADD KEY `FKq4yhqqwlr5f4282obqkepn04t` (`supplierid`);

--
-- Indexes for table `purchase_order_details`
--
ALTER TABLE `purchase_order_details`
  ADD PRIMARY KEY (`purchase_order_detailid`),
  ADD KEY `FKss7rar4i0cwt1scl8pvm0pmxn` (`productid`),
  ADD KEY `FK5ujfoxcrsnwo8oelx1o72xeet` (`purchase_orderid`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`reviewid`),
  ADD KEY `FKry6hyp71d3k629ky7rgl2lnxk` (`customerid`),
  ADD KEY `FKg69bbwjotlkw04k2hqguscgbo` (`productid`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`roleid`);

--
-- Indexes for table `role_permission`
--
ALTER TABLE `role_permission`
  ADD PRIMARY KEY (`role_permissionid`),
  ADD KEY `FK9vqqf9ycmck1ky2ltg74cgvnk` (`permissionid`),
  ADD KEY `FKppv6whyg3nm3h21k62fwuepjg` (`roleid`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplierid`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_roleid`),
  ADD KEY `FKh4vtyjy0k24gisv1josu2a4dq` (`customerid`),
  ADD KEY `FKbo5ik0bthje7hum554xb17ry6` (`roleid`);

--
-- Indexes for table `wards`
--
ALTER TABLE `wards`
  ADD PRIMARY KEY (`WardID`),
  ADD KEY `DistrictID` (`DistrictID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `branchid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cartid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cart_item`
--
ALTER TABLE `cart_item`
  MODIFY `cart_itemid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `coupon`
--
ALTER TABLE `coupon`
  MODIFY `couponid` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customerid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `districts`
--
ALTER TABLE `districts`
  MODIFY `DistrictID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `inventoryid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `login_history`
--
ALTER TABLE `login_history`
  MODIFY `loginid` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `orderid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order_details`
--
ALTER TABLE `order_details`
  MODIFY `order_detailid` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `paymentid` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `permission`
--
ALTER TABLE `permission`
  MODIFY `permissionid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `provinces`
--
ALTER TABLE `provinces`
  MODIFY `ProvinceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `purchase_order`
--
ALTER TABLE `purchase_order`
  MODIFY `purchase_orderid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `purchase_order_details`
--
ALTER TABLE `purchase_order_details`
  MODIFY `purchase_order_detailid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `reviewid` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `roleid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `role_permission`
--
ALTER TABLE `role_permission`
  MODIFY `role_permissionid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `supplierid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `user_roleid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wards`
--
ALTER TABLE `wards`
  MODIFY `WardID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `FKbqjl6adcvneg48gd2i72caun3` FOREIGN KEY (`customerid`) REFERENCES `customer` (`customerid`);

--
-- Constraints for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD CONSTRAINT `FK4teld0nibbe4l2rucr2yybw2e` FOREIGN KEY (`cartid`) REFERENCES `cart` (`cartid`),
  ADD CONSTRAINT `FK9pw5b35944xvbr3h8avlakscl` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`);

--
-- Constraints for table `districts`
--
ALTER TABLE `districts`
  ADD CONSTRAINT `districts_ibfk_1` FOREIGN KEY (`ProvinceID`) REFERENCES `provinces` (`ProvinceID`);

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `FK3i7p043bjgl1s0y2ptrgfn78x` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`),
  ADD CONSTRAINT `FKcwqf7vauhw5kufw0wg8lgxxu5` FOREIGN KEY (`branchid`) REFERENCES `branch` (`branchid`);

--
-- Constraints for table `login_history`
--
ALTER TABLE `login_history`
  ADD CONSTRAINT `FKhultxghdbx5s9di9d364qq110` FOREIGN KEY (`customerid`) REFERENCES `customer` (`customerid`);

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `FK1t7f2e7ygr5axjy3500yofr4s` FOREIGN KEY (`customerid`) REFERENCES `customer` (`customerid`),
  ADD CONSTRAINT `FK2womuo8rw8uvn7lw1dc0jy1b6` FOREIGN KEY (`couponid`) REFERENCES `coupon` (`couponid`),
  ADD CONSTRAINT `FK7dq91bw6e7g4fw66tet5w8k8t` FOREIGN KEY (`branchid`) REFERENCES `branch` (`branchid`);

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `FKjmdsv4sh1x4akwdtaibwspa0v` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`),
  ADD CONSTRAINT `FKlbxu5fj479do0s9ux84yms99s` FOREIGN KEY (`orderid`) REFERENCES `order` (`orderid`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `FKfn74p7y92ytpeih2i3b397cwf` FOREIGN KEY (`orderid`) REFERENCES `order` (`orderid`);

--
-- Constraints for table `purchase_order`
--
ALTER TABLE `purchase_order`
  ADD CONSTRAINT `FKq4yhqqwlr5f4282obqkepn04t` FOREIGN KEY (`supplierid`) REFERENCES `supplier` (`supplierid`);

--
-- Constraints for table `purchase_order_details`
--
ALTER TABLE `purchase_order_details`
  ADD CONSTRAINT `FK5ujfoxcrsnwo8oelx1o72xeet` FOREIGN KEY (`purchase_orderid`) REFERENCES `purchase_order` (`purchase_orderid`),
  ADD CONSTRAINT `FKss7rar4i0cwt1scl8pvm0pmxn` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `FKg69bbwjotlkw04k2hqguscgbo` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`),
  ADD CONSTRAINT `FKry6hyp71d3k629ky7rgl2lnxk` FOREIGN KEY (`customerid`) REFERENCES `customer` (`customerid`);

--
-- Constraints for table `role_permission`
--
ALTER TABLE `role_permission`
  ADD CONSTRAINT `FK9vqqf9ycmck1ky2ltg74cgvnk` FOREIGN KEY (`permissionid`) REFERENCES `permission` (`permissionid`),
  ADD CONSTRAINT `FKppv6whyg3nm3h21k62fwuepjg` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKbo5ik0bthje7hum554xb17ry6` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`),
  ADD CONSTRAINT `FKh4vtyjy0k24gisv1josu2a4dq` FOREIGN KEY (`customerid`) REFERENCES `customer` (`customerid`);

--
-- Constraints for table `wards`
--
ALTER TABLE `wards`
  ADD CONSTRAINT `wards_ibfk_1` FOREIGN KEY (`DistrictID`) REFERENCES `districts` (`DistrictID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
