-- phpMyAdmin SQL Dump
-- version 4.4.15.8
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 12, 2016 at 01:09 PM
-- Server version: 5.5.50-MariaDB
-- PHP Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `metro_share`
--
CREATE DATABASE IF NOT EXISTS `metro_share` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `metro_share`;

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `ID` int(11) NOT NULL,
  `mediaId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `message` tinytext NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`ID`, `mediaId`, `userId`, `message`, `date`) VALUES
(1, 2, 1, 'Hieno Kuva', '2016-11-16 07:51:01'),
(2, 3, 1, 'FIRST!!!', '2016-11-16 07:51:34'),
(3, 4, 3, 'I call to your MOM!!!', '2016-11-16 07:52:33'),
(4, 5, 2, 'I have dont that much better in the past but i forgot to the sd card somewhere!! QQ', '2016-11-16 07:52:33'),
(5, 2, 3, 'NOYYY!!!', '2016-11-28 07:59:39'),
(6, 2, 2, 'HÄÄÄHÄÄHÄHÄÄHÄÄÄ', '2016-11-28 07:59:39'),
(7, 14, 2, 'dasdas', '2016-12-05 11:27:03'),
(8, 14, 2, 'dddd', '2016-12-05 11:30:00'),
(9, 14, 2, 'dddd', '2016-12-05 11:30:18'),
(10, 14, 2, 'dddd', '2016-12-05 11:30:38'),
(11, 13, 2, 'ddd', '2016-12-05 11:32:15'),
(12, 13, 2, 'ssss', '2016-12-05 11:34:22'),
(13, 13, 2, 'xddd', '2016-12-05 11:34:36'),
(14, 13, 2, 'ddddddddddddfff', '2016-12-05 11:35:09'),
(15, 2, 2, 'hsdkjsdflköfs', '2016-12-05 11:37:14'),
(16, 14, 2, 'hhh', '2016-12-05 11:48:16'),
(17, 14, 2, 'Test commenty\n', '2016-12-05 11:49:19'),
(18, 14, 2, 'yhyhyyyy', '2016-12-05 11:59:02'),
(19, 8, 2, 'test', '2016-12-05 12:49:47'),
(20, 8, 2, 'sdsdg', '2016-12-05 12:51:02'),
(21, 8, 2, 'asdgfsadgsdg', '2016-12-05 12:53:40'),
(22, 2, 2, 'ddss', '2016-12-05 12:55:45'),
(23, 6, 2, 'Makkara', '2016-12-05 12:57:03'),
(24, 7, 1, 'test', '2016-12-05 13:05:42'),
(25, 9, 1, 'test', '2016-12-07 07:22:19'),
(26, 64, 1, 'cool', '2016-12-12 11:58:48'),
(27, 47, 1, 'Beautiful!', '2016-12-12 12:46:30'),
(28, 47, 3, 'wow!', '2016-12-12 13:03:24');

-- --------------------------------------------------------

--
-- Table structure for table `friend`
--

CREATE TABLE IF NOT EXISTS `friend` (
  `ID` int(11) NOT NULL,
  `ownerId` int(11) NOT NULL,
  `friendId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friend`
--

INSERT INTO `friend` (`ID`, `ownerId`, `friendId`) VALUES
(1, 1, 2),
(2, 2, 3),
(3, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `media`
--

CREATE TABLE IF NOT EXISTS `media` (
  `ID` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `mediaLocation` varchar(255) NOT NULL,
  `title` tinytext,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nsfw` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `media`
--

INSERT INTO `media` (`ID`, `userId`, `mediaLocation`, `title`, `date`, `nsfw`) VALUES
(2, 1, 'uploads/1/2.png', 'Epic fantasy', '2016-11-16 07:41:15', 1),
(3, 1, 'uploads/1/3.png', 'Totally random', '2016-11-16 07:41:15', 1),
(4, 2, 'uploads/2/4.jpg', 'Inspiring thought! Motto of the day!', '2016-11-16 07:41:32', 0),
(5, 3, 'uploads/3/5.png', 'gigling baby video!', '2016-11-16 07:41:32', 0),
(6, 1, 'uploads/1/6.jpg', 'My Mother is so lovely!', '2016-11-29 08:23:35', 0),
(7, 2, 'uploads/2/7.jpg', 'Wonderland', '2016-11-29 08:23:35', 0),
(8, 3, 'uploads/3/8.jpg', 'Nature''s Wonders', '2016-11-29 08:24:57', 0),
(9, 2, 'uploads/2/9.jpg', 'Rankka päivä', '2016-11-29 08:24:57', 0),
(12, 5, 'uploads/5/12.jpg', 'so pretty kitty!', '2016-11-30 07:13:04', 0),
(13, 5, 'uploads/5/13.jpg', 'AWWS!', '2016-11-30 07:13:04', 0),
(14, 5, 'uploads/5/14.jpg', 'MEOW!', '2016-11-30 07:13:26', 0),
(47, 1, 'uploads/1/15.jpg', 'Misty lake', '2016-12-12 10:11:12', 0),
(49, 1, 'uploads/1/48.jpg', 'Big cute cat', '2016-12-12 10:13:13', 1),
(50, 1, 'uploads/1/50.jpeg', 'Ninja in a tree', '2016-12-12 10:19:30', 1),
(51, 1, 'uploads/1/51.jpg', 'So big', '2016-12-12 10:19:43', 1),
(52, 1, 'uploads/1/52.jpg', 'So small', '2016-12-12 10:20:00', 0),
(53, 1, 'uploads/1/53.jpg', 'Nice teeth you have', '2016-12-12 10:21:07', 1),
(54, 1, 'uploads/1/54.jpg', 'Yet another landscape', '2016-12-12 10:21:26', 0),
(55, 1, 'uploads/1/55.jpg', 'Bridge in a rainforest', '2016-12-12 10:21:56', 0),
(56, 1, 'uploads/1/56.jpeg', 'Broken trees', '2016-12-12 10:22:59', 0),
(57, 1, 'uploads/1/57.jpg', 'So happy', '2016-12-12 10:23:13', 0),
(58, 1, 'uploads/1/58.jpg', 'Underwater bubbles', '2016-12-12 10:23:33', 0),
(59, 1, 'uploads/1/59.jpg', 'Landscape. So basic.', '2016-12-12 10:24:05', 0),
(60, 1, 'uploads/1/60.jpg', 'Speedway', '2016-12-12 10:24:20', 0),
(61, 1, 'uploads/1/61.jpg', 'Lonely sheep', '2016-12-12 10:25:40', 0),
(62, 1, 'uploads/1/62.jpg', 'Cat', '2016-12-12 10:26:08', 0),
(63, 1, 'uploads/1/63.jpg', 'Gaming setup', '2016-12-12 10:26:25', 0),
(64, 1, 'uploads/1/64.jpg', 'My desktop', '2016-12-12 10:26:45', 0),
(65, 1, 'uploads/1/65.jpg', 'Dual monitor setup', '2016-12-12 10:27:08', 0),
(66, 1, 'uploads/1/66.jpg', 'Green garden', '2016-12-12 10:27:29', 0),
(67, 1, 'uploads/1/67.jpg', 'Beautiful rainforest', '2016-12-12 10:27:47', 0),
(68, 1, 'uploads/1/68.jpg', 'Green pond', '2016-12-12 10:28:40', 0),
(69, 1, 'uploads/1/69.jpg', 'Red sunset', '2016-12-12 10:28:57', 0),
(70, 1, 'uploads/1/70.jpg', 'Bird in a forest', '2016-12-12 10:29:36', 0),
(71, 1, 'uploads/1/71.jpg', 'Big hungry cat', '2016-12-12 10:30:07', 1),
(72, 1, 'uploads/1/72.jpg', 'In a galaxy far far away', '2016-12-12 10:30:50', 0),
(77, 1, 'uploads/1/77.jpg', 'Totally not NSFW', '2016-12-12 10:43:45', 1);

-- --------------------------------------------------------

--
-- Table structure for table `media_like`
--

CREATE TABLE IF NOT EXISTS `media_like` (
  `ID` int(11) NOT NULL,
  `mediaId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `likeBoolean` tinyint(1) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `media_like`
--

INSERT INTO `media_like` (`ID`, `mediaId`, `userId`, `likeBoolean`, `date`) VALUES
(1, 2, 1, 1, '2016-11-16 08:04:40'),
(2, 3, 2, 0, '2016-11-16 08:04:40'),
(3, 4, 3, 1, '2016-11-16 08:04:58'),
(4, 2, 2, 1, '2016-11-16 08:04:58'),
(12, 5, 3, 1, '2016-12-08 11:03:34'),
(13, 6, 3, 1, '2016-12-08 11:40:13'),
(14, 5, 2, 0, '2016-12-08 11:56:18'),
(22, 12, 2, 1, '2016-12-09 07:38:11'),
(24, 14, 3, 1, '2016-12-12 10:16:01'),
(25, 14, 1, 1, '2016-12-12 10:16:14'),
(26, 57, 1, 1, '2016-12-12 11:00:13'),
(28, 47, 1, 1, '2016-12-12 12:45:36'),
(29, 47, 3, 1, '2016-12-12 13:03:08'),
(30, 47, 2, 1, '2016-12-12 13:03:22'),
(31, 63, 1, 0, '2016-12-12 13:04:05');

-- --------------------------------------------------------

--
-- Table structure for table `media_tag`
--

CREATE TABLE IF NOT EXISTS `media_tag` (
  `ID` int(11) NOT NULL,
  `mediaId` int(11) NOT NULL,
  `tagId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `media_tag`
--

INSERT INTO `media_tag` (`ID`, `mediaId`, `tagId`) VALUES
(1, 2, 2),
(2, 3, 3),
(3, 4, 4),
(4, 5, 6),
(5, 2, 4),
(6, 33, 1),
(7, 33, 3),
(8, 33, 2),
(15, 48, 10),
(16, 48, 11),
(17, 48, 12),
(18, 50, 14),
(19, 51, 15),
(20, 52, 15),
(21, 53, 16),
(22, 54, 17),
(23, 54, 11),
(24, 54, 18),
(25, 55, 19),
(26, 55, 20),
(27, 56, 21),
(28, 57, 22),
(29, 58, 23),
(30, 59, 17),
(31, 60, 24),
(32, 60, 17),
(33, 61, 25),
(34, 61, 26),
(35, 62, 27),
(36, 63, 28),
(37, 64, 29),
(38, 64, 28),
(39, 65, 29),
(40, 65, 30),
(41, 66, 31),
(42, 67, 19),
(43, 68, 19),
(44, 68, 32),
(45, 69, 33),
(46, 70, 34),
(47, 70, 35),
(48, 71, 36),
(49, 71, 27),
(50, 72, 37),
(51, 72, 38),
(52, 73, 39),
(53, 74, 39),
(54, 75, 39),
(55, 76, 39),
(56, 77, 39);

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE IF NOT EXISTS `tag` (
  `ID` int(11) NOT NULL,
  `tag` varchar(35) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tag`
--

INSERT INTO `tag` (`ID`, `tag`) VALUES
(1, 'Matti'),
(2, 'Teppo'),
(3, 'Seppo'),
(4, 'ED'),
(5, 'NEEDMORECAFEIN!'),
(6, 'RIP'),
(10, 'mist'),
(11, 'water'),
(12, 'lake'),
(13, 'cute'),
(14, 'ninja'),
(15, 'elephant'),
(16, 'pet'),
(17, 'landscape'),
(18, 'ocean'),
(19, 'rainforest'),
(20, 'bridge'),
(21, 'tree'),
(22, 'happy'),
(23, 'bubbles'),
(24, 'speed'),
(25, 'lonely'),
(26, 'sheep'),
(27, 'cat'),
(28, 'gaming'),
(29, 'desktop'),
(30, 'setup'),
(31, 'garden'),
(32, 'pond'),
(33, 'sunset'),
(34, 'forest'),
(35, 'bird'),
(36, 'hungry'),
(37, 'earth'),
(38, 'galaxy'),
(39, 'puppy');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL,
  `Login` varchar(20) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Privileges` varchar(10) NOT NULL DEFAULT 'user',
  `Activity` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `SessionID` varchar(64) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID`, `Login`, `Password`, `Privileges`, `Activity`, `SessionID`) VALUES
(1, 'matti', 'seppo', 'user', '2016-11-16 07:39:05', 'br4PF079Z6uufHxSpiLQHgI4S3Lu5ISMI0LReUp0P8wZ2IBenOq4qWfblGLnVoh1'),
(2, 'seppo', 'teppo', 'admin', '2016-11-16 07:39:57', 'TV4k3f4SfpPNh1rUohhgXLYJWG3zvjVRQo7D3sFqMjKsQTYEe9jv9seRRcJ7WLid'),
(3, 'teppo', 'matti', 'user', '2016-11-16 07:39:57', 'uZ8xAjv0BmfEQEATdxOjI6Z8ncCKX1cbxwj2QD9Hq0ulqur4KkwWkeT7eRfomR0P'),
(5, 'tester', 'tester', 'user', '2016-11-24 10:26:40', 'HU8p2HU4Oyz2b099Ka8WprdlDWicuRLtCGoqgRNBiTC7SXDlLBksWPV3LXlcTYK6'),
(6, 'tester2', 'tester2', 'user', '2016-11-24 10:26:58', NULL),
(7, 'partyanimal', 'banaanimies', 'user', '2016-11-30 10:34:10', NULL),
(8, 'pölöööö', 'asd', 'user', '2016-11-30 11:07:16', NULL),
(19, 'test2', 'test2', 'user', '2016-12-02 09:44:48', NULL),
(20, 'test3', 'test3', 'user', '2016-12-02 10:03:04', NULL),
(22, 'kala', 'juusto', 'user', '2016-12-12 07:20:13', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `imgId` (`mediaId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `imgId_2` (`mediaId`),
  ADD KEY `userId_2` (`userId`);

--
-- Indexes for table `friend`
--
ALTER TABLE `friend`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ownerId` (`ownerId`),
  ADD KEY `friendId` (`friendId`);

--
-- Indexes for table `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `media_like`
--
ALTER TABLE `media_like`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `mediaId` (`mediaId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `media_tag`
--
ALTER TABLE `media_tag`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `mediaId` (`mediaId`),
  ADD KEY `tagId` (`tagId`);

--
-- Indexes for table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=29;
--
-- AUTO_INCREMENT for table `friend`
--
ALTER TABLE `friend`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `media`
--
ALTER TABLE `media`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=78;
--
-- AUTO_INCREMENT for table `media_like`
--
ALTER TABLE `media_like`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `media_tag`
--
ALTER TABLE `media_tag`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=57;
--
-- AUTO_INCREMENT for table `tag`
--
ALTER TABLE `tag`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=23;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`ID`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`mediaId`) REFERENCES `media` (`ID`);

--
-- Constraints for table `friend`
--
ALTER TABLE `friend`
  ADD CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`ownerId`) REFERENCES `user` (`ID`),
  ADD CONSTRAINT `friend_ibfk_2` FOREIGN KEY (`friendId`) REFERENCES `user` (`ID`);

--
-- Constraints for table `media`
--
ALTER TABLE `media`
  ADD CONSTRAINT `media_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`ID`);

--
-- Constraints for table `media_like`
--
ALTER TABLE `media_like`
  ADD CONSTRAINT `media_like_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`ID`),
  ADD CONSTRAINT `media_like_ibfk_2` FOREIGN KEY (`mediaId`) REFERENCES `media` (`ID`);

--
-- Constraints for table `media_tag`
--
ALTER TABLE `media_tag`
  ADD CONSTRAINT `media_tag_ibfk_1` FOREIGN KEY (`tagId`) REFERENCES `tag` (`ID`),
  ADD CONSTRAINT `media_tag_ibfk_2` FOREIGN KEY (`mediaId`) REFERENCES `media` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
