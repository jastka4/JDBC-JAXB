DROP DATABASE IF EXISTS `jdbc_java`;

CREATE DATABASE IF NOT EXISTS `jdbc_java`;
USE `jdbc_java`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `author`;
CREATE TABLE `author`
(
    `id`         int auto_increment primary key,
    `first_name` varchar(50) not null,
    `last_name`  varchar(50) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Inserting data for table `users`
--

INSERT INTO `author`(first_name, last_name)
VALUES ('Suzanne', 'Collins'),
       ('George R. R.', 'Martin'),
       ('Bram', 'Stoker'),
       ('Stieg', 'Larsson');

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`
(
    `id`        int primary key auto_increment,
    `title`     varchar(50) not null,
    `author_id` int         not null,
    FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `book`(title, author_id)
VALUES ('Hunger Games', 1),
       ('Catching Fire', 1),
       ('Mockingjay', 1),
       ('A Game of Thrones', 2),
       ('A Clash of Kings', 2),
       ('A Storm of Swords', 2),
       ('A Feast for Crows', 2),
       ('A Dance with Dragons', 2),
       ('The Winds of Winter', 2),
       ('A Dream of Spring', 2),
       ('Dracula', 3),
       ('Dracula\'s Guest', 3),
       ('Men Who Hate Women', 4),
       ('The Girl Who Played with Fire', 4),
       ('The Air Castle That Was Blown Up', 4);
