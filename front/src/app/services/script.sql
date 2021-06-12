begin;

delete from doctor_infos;
delete from user;

insert into address (id, city, postal_code, road) values
	(1, "talence", 33400, "avenue de collégno"),
    (2, "pessac", 33600, "rue du succés"),
    (3, "talence", 33600, "rue du heineken");

insert into user (id, first_name, last_name, email, password, user_type, address_id) values 
(1, "elhadj amadou", "bah", "frontd@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 1),
(2, "ibrahima", "bah", "frontd1@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 2),
(3, "ibrahima", "diallo", "frontd2@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 3),
(4, "elhadj mamadou", "bah", "frontd3@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 1),
(5, "elhadj amadou", "sow", "frontd4@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 1, 1);

insert into doctor_infos (user_id, id, speciality, presentation) values
	(1, 1, "medecin generaliste", ""),
    (2, 2, "medecin generaliste", ""),
    (3, 3, "medecin", ""),
    (4, 4, "psychiatre", ""),
    (5, 5, "psychiatre", "");
    
insert into user (id, first_name, last_name, email, password, user_type) values 
(6, "elhadj amadou", "bah", "front@gmail.com", "$2a$10$29RdZoRT4kW7bteGKgVPEeV6nV9Y7TvMmyf1ed10eGYQnv/GoHtoa", 0);
commit;