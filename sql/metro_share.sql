-- phpMyAdmin SQL Dump
-- version 4.4.15.8
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 12, 2016 at 06:49 AM
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

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
(25, 9, 1, 'test', '2016-12-07 07:22:19');

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `media`
--

INSERT INTO `media` (`ID`, `userId`, `mediaLocation`, `title`, `date`, `nsfw`) VALUES
(2, 1, 'uploads/1/2.png', 'Awesome tit pic!', '2016-11-16 07:41:15', 1),
(3, 1, 'uploads/1/3.png', 'Nice ass pic!', '2016-11-16 07:41:15', 1),
(4, 2, 'uploads/2/4.jpg', 'Inspiring tought! Moto of the day!', '2016-11-16 07:41:32', 0),
(5, 3, 'uploads/3/5.png', 'gigling baby video!', '2016-11-16 07:41:32', 0),
(6, 1, 'uploads/1/6.jpg', 'My Mother is so lovely!', '2016-11-29 08:23:35', 0),
(7, 2, 'uploads/2/7.jpg', 'KALJAAA!!!!', '2016-11-29 08:23:35', 0),
(8, 3, 'uploads/3/8.jpg', 'Olipas hyvää!', '2016-11-29 08:24:57', 0),
(9, 2, 'uploads/2/9.jpg', 'Rankkapäivä', '2016-11-29 08:24:57', 0),
(12, 5, 'uploads/5/12.jpg', 'so pretty kitty!', '2016-11-30 07:13:04', 0),
(13, 5, 'uploads/5/13.jpg', 'AWWS!', '2016-11-30 07:13:04', 0),
(14, 5, 'uploads/5/14.jpg', 'MEOW!', '2016-11-30 07:13:26', 0),
(23, 1, 'uploads/1/15.gif', 'test', '2016-12-05 13:04:57', 0),
(24, 1, 'uploads/1/24.gif', 'test15', '2016-12-07 07:06:47', 0),
(25, 1, 'uploads/1/25.gif', 'test16', '2016-12-07 07:10:03', 0),
(26, 1, 'uploads/1/26.gif', 'test17', '2016-12-07 07:10:27', 0),
(27, 1, 'uploads/1/27.gif', 'test18', '2016-12-07 07:11:11', 0),
(28, 1, 'uploads/1/28.gif', 'test19', '2016-12-07 07:23:56', 0),
(29, 1, 'uploads/1/29.gif', 'test22', '2016-12-09 09:39:22', 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

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
(22, 12, 2, 1, '2016-12-09 07:38:11');

-- --------------------------------------------------------

--
-- Table structure for table `media_tag`
--

CREATE TABLE IF NOT EXISTS `media_tag` (
  `ID` int(11) NOT NULL,
  `mediaId` int(11) NOT NULL,
  `tagId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `media_tag`
--

INSERT INTO `media_tag` (`ID`, `mediaId`, `tagId`) VALUES
(1, 2, 2),
(2, 3, 3),
(3, 4, 4),
(4, 5, 6),
(5, 2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE IF NOT EXISTS `tag` (
  `ID` int(11) NOT NULL,
  `tag` varchar(35) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tag`
--

INSERT INTO `tag` (`ID`, `tag`) VALUES
(1, 'Matti'),
(2, 'Teppo'),
(3, 'Seppo'),
(4, 'ED'),
(5, 'NEEDMORECAFEIN!'),
(6, 'RIP');

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID`, `Login`, `Password`, `Privileges`, `Activity`, `SessionID`) VALUES
(1, 'matti', 'seppo', 'user', '2016-11-16 07:39:05', 'BrZVBAtv4Zc2kS9ia9epU2G4MXcWrT4rrc9NHzgYAtViPpGbv7XMg8XMlTc99nnm'),
(2, 'seppo', 'teppo', 'user', '2016-11-16 07:39:57', 'a4jvxGZrNq55Z7Dx5PxcDErYbEisFMKp7oBSFuLK8mct6DCGEXeAyto7ZJ7nBoQN'),
(3, 'teppo', 'matti', 'user', '2016-11-16 07:39:57', 'Rsy6gbVPWR9lnNuW8VbGI7OaUEU8nrUkfI1l0ihRCCPQbPiwJfrCEu1hGzvH3g9n'),
(5, 'tester', 'tester', 'user', '2016-11-24 10:26:40', '6IyolKYGaBVP8dCLMqDgHOsi6T0POUXiJYm6Xbdr9waNt1RoUvCEymtd0VXzREzb'),
(6, 'tester2', 'tester2', 'user', '2016-11-24 10:26:58', NULL),
(7, 'partyanimal', 'banaanimies', 'user', '2016-11-30 10:34:10', NULL),
(8, 'pölöööö', 'asd', 'user', '2016-11-30 11:07:16', NULL),
(19, 'test2', 'test2', 'user', '2016-12-02 09:44:48', NULL),
(20, 'test3', 'test3', 'user', '2016-12-02 10:03:04', NULL);

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
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT for table `friend`
--
ALTER TABLE `friend`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `media`
--
ALTER TABLE `media`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT for table `media_like`
--
ALTER TABLE `media_like`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `media_tag`
--
ALTER TABLE `media_tag`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `tag`
--
ALTER TABLE `tag`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
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
