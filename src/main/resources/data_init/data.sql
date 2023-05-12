insert into users (id, full_name, email, password, role)
values (1, 'Admin', 'admin@gmail.com', '$2a$12$eJPgDybKQRMGWIWDy4HctOJlTivV1ycy5O25m1aDZlLki/Ay9chk.', 'ADMIN'),
    (2, 'Kulen', 'kulen@gmail.com', '$2a$12$B0a6q5omRDN9ILBg6fNha.2U.D9cncdzgysLn06BZuAtRNQKhyvCS', 'USER'),
    (3, 'Buju', 'buju@gmail.com', '$2a$12$PtlmxWlio3OojiElE9CvsexbS7O/Iw3gNXqzV/UAd.wB6P1pW2Wc2', 'USER'),
    (4, 'Musa', 'musa@gmail.com', '$2a$12$knEdGlPqijYwTl2n9X.y5.i5nsudsdN/Zd.6QbTYGV4qqyre.M6p.', 'USER');

insert into habit (id, name, description, goal, frequency, habit_status, start_date, end_date)
values (1, 'Running', 'Running every day', 3, 'DAILY', 'TO_DO', '2023-05-01', '2023-05-01'),
       (2, 'Reading', 'Reading every month', 3, 'MONTHLY', 'TO_DO', '2023-05-01', '2023-05-01'),
       (3, 'Learning', 'Learning every week', 3, 'WEEKLY', 'TO_DO', '2023-05-01', '2023-05-01'),
       (4, 'YOGA', 'YOGA every day', 3, 'DAILY', 'TO_DO', '2023-05-01', '2023-05-01');

