-- MySQL Script generated by MySQL Workbench
-- 03/02/15 00:09:31
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema java2_test
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `java2_test` ;

-- -----------------------------------------------------
-- Schema java2_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `java2_test` DEFAULT CHARACTER SET utf8 ;
USE `java2_test` ;

-- -----------------------------------------------------
-- Table `java2_test`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java2_test`.`users` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`users` (
  `UserID` INT(11) NOT NULL AUTO_INCREMENT,
  `Login` CHAR(16) NOT NULL,
  `Password` CHAR(80) NOT NULL,
  `Name` CHAR(32) NOT NULL,
  `Surname` CHAR(32) NOT NULL,
  `Gender` CHAR(6) NOT NULL,
  `Phone` CHAR(15) NOT NULL,
  `Email` CHAR(30) NOT NULL,
  `Access_Level` INT(3) NOT NULL,
  `Photo` CHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`UserID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `java2_test`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java2_test`.`products` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`products` (
  `ProductID` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` CHAR(32) NOT NULL,
  `Description` TEXT NOT NULL,
  `Price` DECIMAL(20,6) UNSIGNED NOT NULL,
  `Picture` CHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`ProductID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `java2_test`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java2_test`.`comments` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`comments` (
  `ID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Comment` CHAR(255) NULL DEFAULT NULL,
  `UserID` INT(11) NOT NULL,
  `ProductID` INT(11) NOT NULL,
  PRIMARY KEY (`ID`, `UserID`, `ProductID`),
  FOREIGN KEY (`UserID`) REFERENCES `java2_test`.`users` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`ProductID`) REFERENCES `java2_test`.`products` (`ProductID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `java2_test`.`news`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java2_test`.`news` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`news` (
  `DateID` CHAR(30) NOT NULL,
  `Title` CHAR(30) NOT NULL,
  `Body` CHAR(80) NOT NULL,
  `Likes` INT(11),
  PRIMARY KEY (`DateID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `java2_test`.`newsbackup`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java2_test`.`newsbackup` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`newsbackup` (
  `DateID` CHAR(30) NOT NULL,
  `Title` CHAR(30) NOT NULL,
  `Body` CHAR(80) NOT NULL,
  `Likes` INT(11),
  PRIMARY KEY (`DateID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `java2_test`.`zakupka`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java2_test`.`zakupka` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`zakupka` (
  `ZakupkaID` INT(11) NOT NULL AUTO_INCREMENT,
  `ProductID` INT(11) NOT NULL,
  `SkladID` INT(11) NOT NULL,
  `DateZakupka` DATE NOT NULL,
  `Quantity` INT(11) NOT NULL,
  PRIMARY KEY (`ZakupkaID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `java2_test`.`carts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java2_test`.`carts` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`carts` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `ProductID` INT(11) NOT NULL,
  `UserID` INT(11) NOT NULL,
  `Count` INT UNSIGNED NULL DEFAULT 0,
  `IsOrdered` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`ProductID`) REFERENCES `java2_test`.`products` (`ProductID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`UserID`) REFERENCES `java2_test`.`users` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
USE `java2_test`;

DELIMITER $$

USE `java2_test`$$
DROP TRIGGER IF EXISTS `java2_test`.`delete_newItem` $$
USE `java2_test`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `java2_test`.`delete_newItem`
BEFORE DELETE ON `java2_test`.`news`
FOR EACH ROW
BEGIN
  INSERT INTO newsBackup Set DateId = OLD.DateId, Title = OLD.Title, Body = OLD.Body, Likes = OLD.Likes;
END$$


DELIMITER ;