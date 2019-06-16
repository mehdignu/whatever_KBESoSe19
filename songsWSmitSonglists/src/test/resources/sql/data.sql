CREATE TABLE IF NOT EXISTS songsService (
songId INTEGER,
album  VARCHAR(50),
artist VARCHAR(50),
released INTEGER,
title  VARCHAR(50),
PRIMARY KEY (`songId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS usersService (
userId VARCHAR(50) NOT NULL ,
password VARCHAR(50) NOT NULL ,
firstName VARCHAR(50) NOT NULL ,
lastName VARCHAR(50) NOT NULL ,
PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `songsService` (`songId`, `album`, `artist`, `released`, `title`) VALUES
(1, 'Trolls', 'Justin Timberlake', 2016, 'Can''t Stop the Feeling'),
(2, 'Thank You', 'Meghan Trainor, Kelli Trainor', 2016, 'Mom'),
(3, 'Unknown', 'Iggy Azalea', 2016, 'Team'),
(4, 'Ghostbusters', 'Fall Out Boy, Missy Elliott', 2016, 'Ghostbusters (I''m not a fraid)'),
(5, 'Bloom', 'Camila Cabello, Machine Gun Kelly', 2017, 'Bad Things'),
(6, 'At Night, Alone.', 'Mike Posner', 2016, 'I Took a Pill in Ibiza'),
(7, 'Top Hits 2017', 'Gnash', 2017, 'i hate u, i love u'),
(8, 'Thank You', 'Meghan Trainor', 2016, 'No'),
(9, 'Glory', 'Britney Spears', 2016, 'Private Show'),
(10, 'Lukas Graham (Blue Album)', 'Lukas Graham', 2015, '7 Years');


INSERT INTO `usersService` (`userId`, `firstName`, `lastName`,`password`) VALUES
('eschuler', 'Elena', 'Schuler', 'passwd123'),
('mmuster', 'Maxime', 'Muster', 'passwd123');
