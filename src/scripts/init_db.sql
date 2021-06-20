
DROP DATABASE IF EXISTS certmonitor;
CREATE DATABASE certmonitor;

USE certmonitor;

CREATE TABLE users (
	userName    VARCHAR(50) NOT NULL PRIMARY KEY,
    pswd    	VARCHAR(50) NOT NULL,
    groupId     VARCHAR(50) NOT NULL
);

INSERT INTO users(userName,pswd,groupId) VALUES('sysadmin', 'test', 'groupA@gmail.com');

CREATE TABLE monitors (
    id              MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	monitorName	    VARCHAR(50) NOT NULL,
	enabled 	    BOOLEAN DEFAULT TRUE,
    hostName    	VARCHAR(100) NOT NULL,
    alertDays       VARCHAR(50) NOT NULL,
    groupEmail      VARCHAR(50) NOT NULL,
    port            VARCHAR(50),
    status 	        VARCHAR(50) DEFAULT 'NOT_YET_TESTED',
    alertSent       BOOLEAN DEFAULT FALSE
);

CREATE TABLE monitor_executions (
    id                MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    monitorId         MEDIUMINT      NOT NULL,
    executionDate     TIMESTAMP      NOT NULL,
	executionStatus   VARCHAR(50)    NOT NULL,
	isSuccess         int            NOT NULL
);
