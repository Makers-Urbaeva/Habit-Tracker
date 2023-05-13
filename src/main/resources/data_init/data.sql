insert into users(id, email, full_name, icon, password, role)
values (1,'burulai@gmail.com','Burulai','icon1','burulai','USER'),
       (2,'kulen@gmail.com','Kulen','icon1','kulen','USER'),
       (3,'musa@gmail.com','Muslima','icon1','musa','USER'),
       (4,'buju@gmail.com','Buajar','icon1','buju','USER');

insert into calendar(id, end_date, start_date, user_id)
values (1,'2023-05-31','2023-05-11',1),
       (2,'2023-05-30','2023-05-10',2),
       (3,'2023-05-29','2023-05-09',3),
       (4,'2023-05-29','2023-05-09',4);

insert into measurement(id, date, measure_type)
values  (1,'2023-05-11','STEPS'),
        (2,'2023-05-05','MINUTES'),
        (3,'2023-05-24','ML'),
        (4,'2023-05-24','MINUTES');

insert into habit(id, description, frequency, goal, is_done, name, calendar_id, measurement_id)
values  (1,'Running every day','DAILY',3,false,'Running',1,1),
        (2,'Reading every week','WEEKLY',3,false,'Reading',1,2),
        (3,'YOGA every month','MONTHLY',3,false,'YOGA',1,2),
        (4,'Listenning podcast every day','DAILY',3,false,'Podcast',1,2);

