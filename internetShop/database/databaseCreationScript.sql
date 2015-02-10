SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `Java2_test` DEFAULT CHARACTER SET utf8 ;
USE `Java2_test` ;

-- -----------------------------------------------------
-- Table `Java2_test`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java2_test`.`users` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`users` (
  `UserID` INT(11) NOT NULL AUTO_INCREMENT,
  `FirstName` CHAR(32) NOT NULL,
  `LastName` CHAR(32) NOT NULL,
  PRIMARY KEY (`UserID`)
)

ENGINE = InnoDB
AUTO_INCREMENT = 1002;


DROP TABLE IF EXISTS `java2_test`.`addresses` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`addresses` (
  `AddressID` INT(11) NOT NULL AUTO_INCREMENT,
  `ClientID` INT(11) NOT NULL,
  `Country` CHAR(32) NOT NULL,
  `City` CHAR(32) NOT NULL,
  `Street` CHAR(32) NOT NULL,
  `House` INT(11) NOT NULL,
  `Flat` INT(11) NOT NULL,
  `Postcode` CHAR(7) NOT NULL,
  PRIMARY KEY (`AddressID`)
)

ENGINE = InnoDB
AUTO_INCREMENT = 1002;


DROP TABLE IF EXISTS `java2_test`.`clients` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`clients` (
  `ClientID` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` CHAR(32) NOT NULL,
  `Surname` CHAR(32) NOT NULL,
  `Personal_Code` CHAR(12) NOT NULL,
  `Gender` CHAR(6) NOT NULL,
  PRIMARY KEY (`ClientID`)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1002;

DROP TABLE IF EXISTS `java2_test`.`emails` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`emails` (
  `EmailID` INT(11) NOT NULL AUTO_INCREMENT,
  `ClientID` INT(11) NOT NULL,
  `Email_Address` CHAR(32) NOT NULL,
  PRIMARY KEY (`EmailID`)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1002;

DROP TABLE IF EXISTS `java2_test`.`phones` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`phones` (
  `PhoneID` INT(11) NOT NULL AUTO_INCREMENT,
  `ClientID` INT(11) NOT NULL,
  `PhoneNumber` CHAR(32) NOT NULL,
  PRIMARY KEY (`PhoneID`)
)
ENGINE = InnoDB
AUTO_INCREMENT = 1002;

DROP TABLE IF EXISTS `java2_test`.`products` ;

CREATE TABLE IF NOT EXISTS `java2_test`.`products` (
  `ProductID` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` CHAR(32) NOT NULL,
  `Description` TEXT NOT NULL,
  `Price` decimal(20,6) unsigned NOT NULL,
  PRIMARY KEY (`ProductID`)
)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;