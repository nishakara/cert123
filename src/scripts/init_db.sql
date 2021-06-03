
DROP DATABASE IF EXISTS certmonitor;
CREATE DATABASE certmonitor;

USE certmonitor;

CREATE TABLE users (
	userName	VARCHAR(50) NOT NULL PRIMARY KEY,
    pswd    	VARCHAR(50) NOT NULL,
    groupId     VARCHAR(50) NOT NULL
);

INSERT INTO users(userName,pswd,groupId) VALUES('sysadmin', 'test', 'groupA');

CREATE TABLE monitors (
	monitorName	    VARCHAR(50) NOT NULL PRIMARY KEY,
	enabled 	    BOOLEAN DEFAULT TRUE,
    hostName    	VARCHAR(100) NOT NULL,
    alertDays       VARCHAR(50) NOT NULL,
    groupEmail      VARCHAR(50) NOT NULL,
    port            VARCHAR(50),
    status 	        BOOLEAN DEFAULT TRUE
);