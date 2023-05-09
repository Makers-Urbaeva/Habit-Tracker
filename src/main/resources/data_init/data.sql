insert into users (id, full_name, email, password)
values  (1, 'Admin', 'admin@gmail.com', 'Admin@1'),
        (2, 'Cinnamon', 'mskuce1@un.org', 'Cinnamon@1'),
        (3, 'Kasey', 'jkidney2@nydailynews.com', 'Kasey@1'),
        (4, 'Wiatt', 'sknappitt3@imdb.com', 'Wiatt@1');

insert into habit (id, name, description, goal, frequency, start_date, end_date)
values (1, 'Running', 'running every day', 3, 'day', '2023-05-01', '2023-05-01'),
       (2, 'Reading', 'running every day', 3, 'day', '2023-05-01', '2023-05-01'),
       (3, 'Learning', 'running every day', 3, 'day', '2023-05-01', '2023-05-01'),
       (4, 'Running', 'running every day', 3, 'day', '2023-05-01', '2023-05-01');

insert into users_habits(user_id, habits_id)
values (1,1),
       (2,2),
       (3,3),
       (4,4);