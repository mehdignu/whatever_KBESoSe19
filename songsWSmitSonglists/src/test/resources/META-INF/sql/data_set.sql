DROP TABLE IF EXISTS list_song;
DROP TABLE IF EXISTS songlists;
DROP TABLE IF EXISTS songs;
DROP TABLE IF EXISTS users;

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
-- Table structure for table `list_song`
--
CREATE TABLE list_song (
                           list_id int NOT NULL,
                           song_id int NOT NULL,
                           PRIMARY KEY (list_id, song_id),
                           FOREIGN KEY (list_id) REFERENCES songlists(id),
                           FOREIGN KEY (song_id) REFERENCES songs (id)
);
--
-- Dumping data for table `list_song`
--
INSERT INTO list_song VALUES (2, 4),
                             (2, 5),
                             (3, 4),
                             (3, 5),
                             (4, 1),
                             (4, 2),
                             (7, 1),
                             (7, 2);

--
-- Dumping data for table `songlists`
--
INSERT INTO songlists VALUES (2, b'0', 'MmusterPublic', 'mmuster'),
                             (3, b'0', 'ElenaPublic', 'eschuler'),
                             (4, b'1', 'EschulerPrivate', 'eschuler'),
                             (7, b'1', 'MmusterPrivate', 'mmuster');

--
-- Dumping data for table `users`
--
INSERT INTO users VALUES ('mmuster','Maxime','Muster','passwd123'),('eschuler','Elena','Schuler','passwd123');

--
-- Dumping data for table `songs`
--
INSERT INTO songs (id, album, artist, released, title) VALUES
(1, 'Trolls', 'Justin Timberlake', 2016, 'Can''t Stop the Feeling'),
(2, 'Thank You', 'Meghan Trainor, Kelli Trainor', 2016, 'Mom'),
(3, NULL, 'Iggy Azalea', 2016, 'Team'),
(4, 'Ghostbusters', 'Fall Out Boy, Missy Elliott', 2016, 'Ghostbusters (I''m not a fraid)'),
(5, 'Bloom', 'Camila Cabello, Machine Gun Kelly', 2017, 'Bad Things'),
(6, 'At Night, Alone.', 'Mike Posner', 2016, 'I Took a Pill in Ibiza'),
(7, 'Top Hits 2017', 'Gnash', 2017, 'i hate u, i love u'),
(8, 'Thank You', 'Meghan Trainor', 2016, 'No'),
(9, 'Glory', 'Britney Spears', 2016, 'Private Show'),
(10, 'Lukas Graham (Blue Album)', 'Lukas Graham', 2015, '7 Years');
