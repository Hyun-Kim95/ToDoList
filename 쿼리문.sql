### user생성 및 권한 부여
CREATE USER 'toyuser'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON `to_do_list`.* TO 'toyuser'@'localhost';

### 데이터베이스 생성
DROP DATABASE IF EXISTS to_do_list;
CREATE DATABASE to_do_list;
USE to_do_list;

### 날짜별 할 일 테이블 생성
CREATE TABLE ToDoList(
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	doDate DATETIME NOT NULL,
	classification CHAR(30) NOT NULL,
	contents TEXT NOT NULL,
	success TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	`user` CHAR(30) NOT NULL
);

### 사이클 테이블 생성
CREATE TABLE CycleList(
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`number` INT(10) NOT NULL,
	contents TEXT NOT NULL,
	success TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	`user` CHAR(30) NOT NULL
);

### 회원 테이블 생성
CREATE TABLE `member`(
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(30) NOT NULL,
	loginPw VARCHAR(100) NOT NULL,
	`name` CHAR(30) NOT NULL,
	`nickname` CHAR(30) NOT NULL,
	`cellphone` CHAR(20) NOT NULL,
	`email` CHAR(100) NOT NULL
);