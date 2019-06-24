DROP TABLE IF EXISTS list_song;
DROP TABLE IF EXISTS songlists;
DROP TABLE IF EXISTS songs;
DROP TABLE IF EXISTS users;

--
-- Table structure for table `list_song`
--
CREATE TABLE list_song (
  list_id int NOT NULL,
  song_id int NOT NULL
);

--
-- Table structure for table `songlists`
--
CREATE TABLE songlists (
                           id int NOT NULL,
                           private bit(1) NOT NULL,
                           name varchar(255) DEFAULT NULL,
                           owner_userId varchar(255) DEFAULT NULL,
                           PRIMARY KEY (id),
                           FOREIGN KEY (owner_userId) REFERENCES users (userId)
);

--
-- Table structure for table `songs`
--
CREATE TABLE songs (
                       id int NOT NULL,
                       album varchar(255) DEFAULT NULL,
                       artist varchar(255) DEFAULT NULL,
                       released int,
                       title varchar(255) DEFAULT NULL,
                       PRIMARY KEY (id)
);

--
-- Table structure for table `users`
--
CREATE TABLE users (
                       userId varchar(255) NOT NULL,
                       firstName varchar(255) DEFAULT NULL,
                       lastName varchar(255) DEFAULT NULL,
                       password varchar(255) DEFAULT NULL,
                       PRIMARY KEY (userId)
);