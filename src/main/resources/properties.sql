create database if not exists portalAcademico;

use portalAcademico;

/* TABLES */

create table if not exists credentials (
	id int primary key auto_increment,
    email varchar(255) unique not null,
    pass varchar(255) not null
);

/*
* USER CODES (user_type)
* 01 -> Student
* 02 -> Teacher
* 03 -> Accenture Employee
*/

create table if not exists school (
	id int primary key auto_increment,
    school_name varchar(100) not null,
    tel char(12) not null,
    email varchar(255) not null,
    secretarySchedule varchar(40),
    loc varchar(50)
);

create table if not exists course (
	id int primary key auto_increment,
    course_name varchar(50)
);

create table if not exists user_obj (
	id int primary key,
    user_name varchar(255) not null,
    user_surname varchar(255) not null,
    birthDate date not null,
    dnie CHAR(9) UNIQUE NOT NULL ,

	user_type varchar(3) default "01",
    school_id int,
    course_id int,
    
    constraint foreign_key_course_id foreign key (course_id) references course(id),
    constraint foreign_key_school_id foreign key (school_id) references school(id),
    constraint foreign_key_id foreign key (id) references credentials(id)
);


/* CHECK PROBLEMA DEL FUTURO */
create table if not exists accenture (
	id int not null primary key,
    user_id int, -- check ((select user_type from user_obj where id = user_id) = "01"),
    
    constraint foreign_key_user_id foreign key (user_id) references user_obj(id),
    constraint foreign_key_accenture_userId foreign key (id) references user_obj(id)
);

create table if not exists _subject (
	id int primary key auto_increment,
	subject_name varchar(255) not null unique,
    weekly_hours int,
    total_hours int
);

create table if not exists appointment (
	id int not null primary key,
	teacher_id int not null,
    student_id int not null,
    day date not null,
    hour time,
    
    constraint foreign_key_appointment_teacherId foreign key (teacher_id) references user_obj(id),
    constraint foreign_key_appointment_studentId foreign key (student_id) references user_obj(id)
);

/* Procedures and functions */
delimiter //
create function checkIfExistsUserCredentials(user_email varchar(255), user_pass varchar(255)) returns boolean no sql
begin
	declare isInTable bool;

	if (select id from credentials where email = user_email and pass = user_pass) then
		set isInTable := true;
	else
		set isInTable := false;
	end if;

	return isInTable;
end//

delimiter //
create function checkIfSchoolExists(school varchar(100), location varchar(50)) returns bool no sql
begin
	declare isInTable bool;
    
    if (select id from school where school_name = school and loc = location) then
		set isInTable := true;
	else 
		set isInTable := false;
    end if;
    
    return isInTable;
end//

delimiter //
create function checkIfSubjectExists(sub varchar(255)) returns bool no sql
begin
	declare isInTable bool;
    
    if (select id from _subject where subject_name = sub) then
		set isInTable := true;
	else 
		set isInTable := false;
    end if;
    
    return isInTable;
end//

delimiter //
create function checkIfStudentExists(user_dnie varchar(10)) returns bool no sql
begin
	declare isInTable bool;
    
    if (select id from student where id = (select id from user_obj where dnie = user_dnie)) then
		set isInTable := true;
	else 
		set isInTable := false;
    end if;
    
    return isInTable;
end//

delimiter //
create procedure insertUserCredentials(user_email varchar(255), user_pass varchar(255))
begin
	declare isInTable bool;
	set isInTable = (select checkIfExistsUserCredentials(user_email, user_pass));

	if not isInTable then
		insert into credentials (email, pass) values (user_email, user_pass);
	end if;  

end//

/* SI EN SCHOOL_ID INTRODUCES UN VALOR NEGATIVO TOMA QUE NO EXISTEE INTRODUCE EL REGISTRO SIN EL */
delimiter //
create procedure insertUser(user_name_input varchar(255), user_surname_input varchar(255), birthDate_input date, dnie_input char(9), user_type varchar(3) , user_email varchar(255), user_pass varchar(255), school_id int, course_id int)
begin
	declare user_id int;
	declare credentials_exists bool;
    
    set credentials_exists := (select checkIfExistsUserCredentials(user_email, user_pass));
    
    
	if credentials_exists then 
		set user_id := (select id from credentials where email = user_email and pass = user_pass);
		insert into user_obj values (user_id, user_name_input, user_surname_input, birthDate_input, dnie_input, user_type, school_id, course_id);
	end if;
end//

delimiter // 
create procedure insertSchool(school_name varchar(100), tel_input char(12), email_input varchar(255), secretarySchedule_input varchar(40), location_input varchar(50))
begin
	declare isInTable bool;
    set isInTable := (select checkIfSchoolExists(school_name, location_input));
    if not isInTable then
		insert into school(school_name, tel, email, secretarySchedule, loc) values (school_name, tel_input, email_input, secretarySchedule_input, location_input);
    end if;
end//

delimiter // 
create procedure insertSubject(sub_name varchar(255), wHours int, tHours int)
begin
	declare isInTable bool;
	set isInTable := (select checkIfSubjectExists(sub_name));
	if not isInTable then
		insert into  _subject (subject_name, weekly_hours, total_hours) values (sub_name, wHours, tHours);
	end if;
end//

/* Procedures for user_credentials and user_obj inserts */
call insertUserCredentials("jon@gmail.com", "$2a$10$JgcLiA0DonrhMyyQj3w.U.rKCsGPFLntCBzguLpJDH6nvRek1I5Le"); /* Pass12345 */
call insertUser("Jon", "Garcia Gimenez", "1985-04-29", "83745924U", "02" , "jon@gmail.com", "$2a$10$JgcLiA0DonrhMyyQj3w.U.rKCsGPFLntCBzguLpJDH6nvRek1I5Le",1,null);
call insertUserCredentials("Francisco@gmail.com", "$2a$10$JPLdWVEyoNhQhlaU8MsNvOmIVPgVuvv4W0TA6f5.jKihNpGK8knXq"); /* Fran0987 */
call insertUser("Francisco", "Perez Trabuco", "1982-11-12", "98736410L", "02", "Francisco@gmail.com", "$2a$10$JPLdWVEyoNhQhlaU8MsNvOmIVPgVuvv4W0TA6f5.jKihNpGK8knXq",2,null);
call insertUserCredentials("guille@yahoo.es", "$2a$10$rkNrx7AVnB1HgEcFRhyAU.FQ/wmjOlbyk13WoSWzC5DyAMBcwGaDK"); /* Guille1990 */
call insertUser("Guillermo", "Huertas Romero", "1990-05-01", "47739814W", "02", "guille@yahoo.es", "$2a$10$rkNrx7AVnB1HgEcFRhyAU.FQ/wmjOlbyk13WoSWzC5DyAMBcwGaDK",3,null);
call insertUserCredentials("rrcastro@hotmail.com", "$2a$10$gHN0/ptYuLs2Z3HfUhL43uZNBiHze1sqmgdjoKxb4c3RKoI3uU0Wi"); /* CasRiv4114 */
call insertUser("Rosa", "Rivas Castro", "1985-05-01", "75047168P", "02", "rrcastro@hotmail.com", "$2a$10$gHN0/ptYuLs2Z3HfUhL43uZNBiHze1sqmgdjoKxb4c3RKoI3uU0Wi",4,null);
call insertUserCredentials("lorenaDoc@gmail.es", "$2a$10$6tYROejoBQbaj5yO7NnxUe96iPfQ7wl2VWf/q3QyMeNaeYoezCEMW"); /* lorenaDoc1234 */
call insertUser("Lorena", "Montiel Frias", "1987-03-19", "68797512G", "02", "lorenaDoc@gmail.es", "$2a$10$6tYROejoBQbaj5yO7NnxUe96iPfQ7wl2VWf/q3QyMeNaeYoezCEMW", 5, null);
/* Creation of Schools */
call insertSchool("Cesur Málaga Este", "+34952598720", "info@cesurformacion.com", "08:00-14:00", "Málaga");
call insertSchool("IES Pablo Picasso", "+34951298666", "info@fpiespablopicasso.es", "8:30-14:30", "Málaga");
call insertSchool("IES Belén", "+34951298425", "29010201.edu@juntadeandalucia.es", "08:15-14:45", "Málaga");
call insertSchool("CPIFP Alan Turing", "+34951040449", "29020231.info@g.educaand.es", "09:00-14:00", "Málaga");
call insertSchool("IES San José", "+34952305100", "sanjose@fundacionloyola.es", "09:00-13:00", "Málaga");
call insertSchool("IES San Pepito", "+34123456789", "sanPepito@fundacion.es", "09:00-13:00", "Málaga");
/* Creation of subjects */
call insertSubject("Programacion", 8, 256);
call insertSubject("Entornos de Desarrollo", 3, 96);
call insertSubject("Bases de Datos", 6, 192);
call insertSubject("Formacion y Orientacion Laboral", 3, 96);
call insertSubject("Lenguaje de Marcas", 4, 128);
call insertSubject("Sistemas Informaticos", 6, 192);
/* Creacion de cursos */
insert into course (course_name) values ("DAM");
insert into course (course_name) values ("DAW");
/* Inserts de credenciales y usuarios de type acc */
call insertUserCredentials("Laura@gmail.com", "$2a$10$GuYsBZYvEDr18OapLN58WuqjriUtoz/SIiB4n3hhxUCWShszjvYCu"); /* Lau10295 */
call insertUser("Laura", "Gasset Vargas", "1990-04-23", "12345678A", "03", "Laura@gmail.com", "$2a$10$GuYsBZYvEDr18OapLN58WuqjriUtoz/SIiB4n3hhxUCWShszjvYCu",null,null);
call insertUserCredentials("Tania@gmail.com", "$2a$10$CQE2rHI0O21C2AOD3nsi/uYBfIDidxajgGFnnqd3yzrDhsOmcLuCe"); /* TanTan007 */
call insertUser("Tania", "La Rubia Alvarez", "1975-04-29", "09876543D", "03", "Tania@gmail.com", "$2a$10$CQE2rHI0O21C2AOD3nsi/uYBfIDidxajgGFnnqd3yzrDhsOmcLuCe",null,null);

/* Selects */
select * from credentials;
select * from user_obj;
select * from school;
select * from _subject;
select * from course;

/* Drop Functions */
drop function checkIfSubjectExists;
drop function checkIfExistsUserCredentials;
drop function checkIfSchoolExists;

/* Drop Procedures */
drop procedure insertUserCredentials;
drop procedure insertUser;
drop procedure insertSchool;
drop procedure insertSubject;

/* Drop database*/
drop database portalacademico;