begin;

delete from slot;
delete from appointment;
delete from doctor_infos;
delete from user;
delete from address;

insert into address (id, city, postal_code, road) values
	(1, "talence", 33400, "avenue de collégno"),
    (2, "pessac", 33600, "rue du succés"),
    (3, "talence", 33600, "rue du heineken");

insert into user (id, first_name, last_name, email, password, user_type, address_id, tel) values 
(1, "elhadj amadou", "bah", "frontd@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 1, "0514232273"),
(2, "ibrahima", "bah", "frontd1@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 2, "0514232273"),
(3, "ibrahima", "diallo", "frontd2@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 3, "0514232273"),
(4, "elhadj mamadou", "balde", "frontd3@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 1, "0514232273"),
(5, "elhadj amadou", "sow", "frontd4@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 1, "0514232273");

insert into doctor_infos (user_id, id, speciality, presentation) values
	(1, 1, "medecin generaliste", ""),
    (2, 2, "medecin generaliste", ""),
    (3, 3, "medecin", ""),
    (4, 4, "psychiatre", ""),
    (5, 5, "psychiatre", "");
    
insert into slot (id, doctor_id, date, start_time, is_used) values
				(1, 1, "2021-6-28", "10:00", 0),
				(2, 1, "2021-6-28", "11:00", 0),
				(3, 1, "2021-6-28", "12:00", 0),
				(4, 1, "2021-6-28", "13:00", 0),
				(5, 1, "2021-6-28", "14:00", 0),
                (6, 1, "2021-6-28", "15:00", 0),
                (7, 1, "2021-6-29", "14:00", 0),
                (8, 1, "2021-6-29", "15:00", 0),
                (9, 1, "2021-6-30", "14:00", 0),
                (10, 1, "2021-6-30", "14:30", 0),
                (11, 1, "2021-6-30", "14:40", 0),
                (12, 1, "2021-6-30", "8:00", 0),
                (13, 1, "2021-6-30", "9:00", 0);
                
    
insert into user (id, first_name, last_name, email, tel, password, user_type, address_id) values 
(6, "elhadj amadou", "bah", "front@gmail.com", "0000000p","$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 0, 2);
commit;