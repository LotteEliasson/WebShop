
DROP DATABASE IF EXISTS webshop;
CREATE SCHEMA webshop;
USE webshop;
CREATE TABLE `webshop`.`products` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `name` VARCHAR(45) NOT NULL,
                                      `price` DECIMAL(10,2) NULL,
                                      PRIMARY KEY (`id`));

INSERT INTO `webshop`.`products` (`name`, `price`) VALUES ('Tuborg', '10');