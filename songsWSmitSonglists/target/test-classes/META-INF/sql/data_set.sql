--
-- Dumping data for table `list_song`
--
INSERT INTO list_song VALUES (1,1),(1,2);

--
-- Dumping data for table `songlists`
--
INSERT INTO songlists VALUES (1, TRUE,'ElenasPrivate','mmuster');

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

--
-- Dumping data for table `users`
--
INSERT INTO users VALUES ('mmuster','Maxime','Muster','passwd123'),('eschuler','Elena','Schuler','passwd123');
