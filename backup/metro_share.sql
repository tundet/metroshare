-- phpMyAdmin SQL Dump
-- version 4.4.15.8
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 22, 2016 at 10:18 AM
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`ID`, `mediaId`, `userId`, `message`, `date`) VALUES
(1, 2, 1, 'Hieno Kuva', '2016-11-16 07:51:01'),
(2, 3, 1, 'FIRST!!!', '2016-11-16 07:51:34'),
(3, 4, 3, 'I call to your MOM!!!', '2016-11-16 07:52:33'),
(4, 5, 2, 'I have dont that much better in the past but i forgot to the sd card somewhere!! QQ', '2016-11-16 07:52:33');

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
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nsfw` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `media`
--

INSERT INTO `media` (`ID`, `userId`, `mediaLocation`, `date`, `nsfw`) VALUES
(2, 1, '/tmp/MetroShare/media/1/1/testi.png', '2016-11-16 07:41:15', 0),
(3, 1, '/tmp/MetroShare/media/1/1/testi.png', '2016-11-16 07:41:15', 0),
(4, 2, '/tmp/MetroShare/media/2/1/testi.png', '2016-11-16 07:41:32', 0),
(5, 3, '/tmp/MetroShare/media/3/1/testi.png', '2016-11-16 07:41:32', 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `media_like`
--

INSERT INTO `media_like` (`ID`, `mediaId`, `userId`, `likeBoolean`, `date`) VALUES
(1, 2, 1, 1, '2016-11-16 08:04:40'),
(2, 3, 2, 0, '2016-11-16 08:04:40'),
(3, 4, 3, 1, '2016-11-16 08:04:58'),
(4, 2, 2, 1, '2016-11-16 08:04:58');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID`, `Login`, `Password`, `Privileges`, `Activity`, `SessionID`) VALUES
(1, 'matti', 'seppo', 'user', '2016-11-16 07:39:05', '&w#60pjfx=-90cg9p4lkbnpsdmkc62m+oep&mv4--b0-24-tuf7+@&rtpe94v1#u'),
(2, 'seppo', 'teppo', 'user', '2016-11-16 07:39:57', NULL),
(3, 'teppo', 'matti', 'user', '2016-11-16 07:39:57', NULL);

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
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `friend`
--
ALTER TABLE `friend`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `media`
--
ALTER TABLE `media`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `media_like`
--
ALTER TABLE `media_like`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
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
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
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
