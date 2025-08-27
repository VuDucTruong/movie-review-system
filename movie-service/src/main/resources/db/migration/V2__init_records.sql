-- ============================================
-- Reset all tables
-- ============================================
TRUNCATE TABLE
    genre,
    movie,
    movie_genre
    RESTART IDENTITY CASCADE;

-- Genres
INSERT INTO genre ( name) VALUES
                                 ( 'Action'),
                                 ( 'Comedy'),
                                 ( 'Drama'),
                                 ( 'Horror'),
                                 ( 'Romance'),
                                 ( 'Sci-Fi'),
                                 ( 'Thriller'),
                                 ( 'Fantasy'),
                                 ( 'Animation'),
                                 ( 'Documentary');

-- Movies
INSERT INTO movie ( title, description, director, poster_url, trailer_url, release_date, created_at, modified_at, average_rating) VALUES
                                                                                                                                         ( 'The Last Stand', 'An action-packed thriller.', 'John Smith', 'poster1.jpg', 'trailer1.mp4', '2020-01-01', now(), now(), 7.8),
                                                                                                                                         ( 'Laugh Out Loud', 'A hilarious comedy movie.', 'Jane Doe', 'poster2.jpg', 'trailer2.mp4', '2021-02-14', now(), now(), 8.1),
                                                                                                                                         ( 'Tears of the Sun', 'A touching drama story.', 'Michael Bay', 'poster3.jpg', 'trailer3.mp4', '2019-05-20', now(), now(), 7.5),
                                                                                                                                         ( 'Haunted Night', 'A terrifying horror movie.', 'Sarah Lee', 'poster4.jpg', 'trailer4.mp4', '2022-10-31', now(), now(), 6.9),
                                                                                                                                         ( 'Love Forever', 'A romantic love story.', 'Chris Evans', 'poster5.jpg', 'trailer5.mp4', '2018-02-14', now(), now(), 8.0),
                                                                                                                                         ( 'Galactic Wars', 'Epic sci-fi adventure.', 'George Lucas', 'poster6.jpg', 'trailer6.mp4', '2015-12-18', now(), now(), 9.0),
                                                                                                                                         ( 'Mind Games', 'Thrilling psychological mystery.', 'David Fincher', 'poster7.jpg', 'trailer7.mp4', '2017-09-10', now(), now(), 8.2),
                                                                                                                                         ( 'Dragon Realm', 'A fantasy world of dragons.', 'Peter Jackson', 'poster8.jpg', 'trailer8.mp4', '2016-07-21', now(), now(), 7.7),
                                                                                                                                         ( 'Toy Adventure', 'Animated family fun.', 'Pixar Studio', 'poster9.jpg', 'trailer9.mp4', '2021-11-20', now(), now(), 8.5),
                                                                                                                                         ( 'Planet Earth', 'A nature documentary.', 'BBC Earth', 'poster10.jpg', 'trailer10.mp4', '2008-03-02', now(), now(), 9.3);

-- Movie - Genre relationships
INSERT INTO movie_genre (movie_id, genre_id) VALUES
                                                 (1, 1), -- Action
                                                 (2, 2), -- Comedy
                                                 (3, 3), -- Drama
                                                 (4, 4), -- Horror
                                                 (5, 5), -- Romance
                                                 (6, 6), -- Sci-Fi
                                                 (7, 7), -- Thriller
                                                 (8, 8), -- Fantasy
                                                 (9, 9), -- Animation
                                                 (10, 10), -- Documentary
-- thêm 1 số phim nhiều thể loại
                                                 (6, 1),  -- Galactic Wars cũng là Action
                                                 (7, 3),  -- Mind Games cũng là Drama
                                                 (8, 5),  -- Dragon Realm cũng có Romance
                                                 (9, 2);  -- Toy Adventure cũng có Comedy