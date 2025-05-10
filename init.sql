-- Create the database
CREATE DATABASE IF NOT EXISTS football_stat_web;

-- Create the user
CREATE USER IF NOT EXISTS 'test_user'@'%' IDENTIFIED BY 'superSecretPassword!123';

-- Grant all privileges on the epl database to the user
GRANT ALL PRIVILEGES ON football_stat_web.* TO 'test_user'@'%' WITH GRANT OPTION;

-- Flush privileges to ensure that the changes take effect
FLUSH PRIVILEGES;
