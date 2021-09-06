-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th9 07, 2021 lúc 12:38 AM
-- Phiên bản máy phục vụ: 10.4.18-MariaDB
-- Phiên bản PHP: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `vdthang_giamsatkhongkhi`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `control`
--

CREATE TABLE `control` (
  `id` int(11) NOT NULL,
  `param` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `value` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `control`
--

INSERT INTO `control` (`id`, `param`, `value`) VALUES
(1, 'fan', 1),
(2, 'mist', 0),
(3, 'servo', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `data`
--

CREATE TABLE `data` (
  `time` datetime NOT NULL,
  `temp` int(11) NOT NULL,
  `humi` int(11) NOT NULL,
  `lux` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `data`
--

INSERT INTO `data` (`time`, `temp`, `humi`, `lux`) VALUES
('2021-09-07 03:05:36', 37, 54, 34),
('2021-09-07 04:47:25', 35, 68, 100),
('2021-09-07 04:50:31', 20, 40, 120),
('2021-09-07 05:11:06', 28, 50, 40);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `control`
--
ALTER TABLE `control`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `data`
--
ALTER TABLE `data`
  ADD PRIMARY KEY (`time`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `control`
--
ALTER TABLE `control`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
