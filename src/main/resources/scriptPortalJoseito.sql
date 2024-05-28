drop database portalacademico;
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
    loc varchar(50),
    mapLink longtext
);

create table if not exists course (
	id int primary key auto_increment,
    course_name varchar(255),
    acronime varchar(5),
    description_course longtext
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


create table if not exists accenture (
	id int not null primary key,
    user_id int, -- check ((select user_type from user_obj where id = user_id) = "01"),
    
    constraint foreign_key_user_id foreign key (user_id) references user_obj(id),
    constraint foreign_key_accenture_userId foreign key (id) references user_obj(id)
);

create table if not exists _subject (
	id int primary key auto_increment,
	subject_name varchar(255) not null,
    weekly_hours int,
    total_hours int
    
);

create table if not exists appointment (
	id char(2) not null primary key,
	teacher_id int not null,
    student_id int,
    day varchar(15) not null,
    hour varchar(15) not null,
    
    constraint foreign_key_appointment_teacherId foreign key (teacher_id) references user_obj(id),
    constraint foreign_key_appointment_studentId foreign key (student_id) references user_obj(id)
);

create table if not exists news(
	id int primary key auto_increment,
    title longtext not null,
    caption longtext,
    content longtext not null,
    img_url longtext,
    caption_img longtext,
    date_new timestamp
);

create table if not exists news_school(
	id_school int,
    id_new int,
    
    PRIMARY KEY(id_school, id_new),
	constraint foreign_key_new_school foreign key (id_school) references school(id),
    constraint foreign_key_idnew foreign key (id_new) references news(id)
);

create table if not exists course_subject(
     course_id int not null,
     subject_id int not null,
     
     PRIMARY KEY (course_id, subject_id),
     constraint foreign_key_idCourse foreign key (course_id) references course(id),
     constraint foreign_key_idSubject foreign key (subject_id) references _subject(id)
);

create table if not exists grades(
	teacher int not null,
    student int not null,
    subject_id int not null,
    grade decimal(3,2) constraint check_grade check (grade >= 0 and grade <= 10),
    
    constraint foreign_key_userTypeTeacher foreign key (teacher) references user_obj(id),
    constraint foreign_key_userTypeStudent foreign key (student) references user_obj(id)
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

delimiter //
create procedure insertNew(titleInput longtext, captionInput longtext, contentInput longtext, imgUrl longtext, captionImg longtext)
begin
		insert into news (title, caption, content, img_url, caption_url, date_new) values (titleInput, captionInput, contentInput, imgUrl, captionImg , current_timestamp());
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
create procedure insertSchool(school_name varchar(100), tel_input char(12), email_input varchar(255), secretarySchedule_input varchar(40), location_input varchar(50), mapLink longtext)
begin
	declare isInTable bool;
    set isInTable := (select checkIfSchoolExists(school_name, location_input));
    if not isInTable then
		insert into school(school_name, tel, email, secretarySchedule, loc, mapLink) values (school_name, tel_input, email_input, secretarySchedule_input, location_input, mapLink);
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


delimiter //
create trigger user_subjects
after insert on user_obj
for each row
begin
declare teacher_id int;
declare numSubject int;
declare cont int;
declare subjectBuilder int;
set cont = 0;
 if (new.user_type = "01") then
	set teacher_id = (select id from user_obj where (course_id=new.course_id and school_id=new.school_id and user_type="02"));
	set numSubject = (select count(*) from course_subject where course_id=new.course_id);
	while (cont<numSubject) do
		set subjectBuilder = (select subject_id from course_subject where course_id=new.course_id limit cont,1);
		INSERT INTO GRADES(teacher, student, subject_id, grade) values (teacher_id, new.id, subjectBuilder, null);
        set cont = cont + 1;
	end while;
  end if;
end//


delimiter ;

/* Creation of Schools */
call insertSchool("Cesur Málaga Este", "+34952598720", "info@cesurformacion.com", "08:00-14:00", "Málaga","https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10757.374130614628!2d-4.372041717464043!3d36.71808277803187!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd7259120bfc4db3%3A0xec0ecedd8dc61902!2sCESUR%20M%C3%A1laga%20Este%20Formaci%C3%B3n%20Profesional!5e0!3m2!1ses!2ses!4v1715334512514!5m2!1ses!2ses");
call insertSchool("IES Pablo Picasso", "+34951298666", "info@fpiespablopicasso.es", "8:30-14:30", "Málaga","https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6395.717928363966!2d-4.455162806420868!3d36.725948300000006!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f70c3d574e37%3A0x67343146876c734b!2sIES%20Pablo%20Picasso!5e0!3m2!1ses!2ses!4v1715335018709!5m2!1ses!2ses");
call insertSchool("IES Belén", "+34951298425", "29010201.edu@juntadeandalucia.es", "08:15-14:45", "Málaga","https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3199.124902411609!2d-4.459761523439527!3d36.69553637227712!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f9dee2ea3131%3A0xe00a7d745fb8b2e3!2sIES%20Bel%C3%A9n!5e0!3m2!1ses!2ses!4v1715335056310!5m2!1ses!2ses");
call insertSchool("CPIFP Alan Turing", "+34951040449", "29020231.info@g.educaand.es", "09:00-14:00", "Málaga","https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3197.2394272377824!2d-4.554430616275409!3d36.740823696739334!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f10963ce0f3d%3A0x310ae7d4bb2e8f7b!2sCPIFP%20Alan%20Turing!5e0!3m2!1ses!2ses!4v1715335096355!5m2!1ses!2ses");
call insertSchool("IES San José", "+34952305100", "sanjose@fundacionloyola.es", "09:00-13:00", "Málaga","https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d60854.997797710945!2d-4.459410649332534!3d36.715431468654785!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f711c56e8bed%3A0x6de2361e88593aeb!2sColegio%20Diocesano%20San%20Jos%C3%A9%20Obrero!5e0!3m2!1ses!2ses!4v1715335137121!5m2!1ses!2ses");
/* Creation of subjects */
INSERT INTO `_subject` VALUES 
(1,'Programacion',8,256),
(2,'Entornos de Desarrollo',3,96),
(3,'Bases de Datos',6,192),
(4,'Formacion y Orientacion Laboral',3,96),
(5,'Lenguaje de Marcas',4,128),
(6,'Sistemas Informaticos',6,192),
(7, "Montaje y mantimientos de equipos",6,106),
(8, "Sistemas operativos monopuestos", 8, 140),
(9, "Introduccion a Programación", 4, 120);

/* Creacion de cursos */
INSERT INTO `course` VALUES
 (1,'Desarrollo de aplicaciones Multiplataforma', "DAM", 'La competencia general de este título consiste en desarrollar, implantar, documentar y mantener aplicaciones informáticas multiplataforma, utilizando tecnologías y entornos de desarrollo específicos, garantizando el acceso a los datos de forma segura y cumpliendo los criterios de «usabilidad» y calidad exigidas en los estándares establecidos.'),
 (2,'Desarrollo de Aplicaciones Web',"DAW",'La competencia general de este título consiste en desarrollar, implantar, y mantener aplicaciones web, con independencia del modelo empleado y utilizando tecnologías específicas, garantizando el acceso a los datos de forma segura y cumpliendo los criterios de accesibilidad, usabilidad y calidad exigidas en los estándares establecidos.'),
 (3,'Administración de sistemas infomrmáticos y redes',"ASIR",'La competencia general de este título consiste en instalar, configurar y mantener sistemas microinformáticos, aislados o en red, así como redes locales en pequeños entornos, asegurando su funcionalidad y aplicando los protocolos de calidad, seguridad y respeto al medio ambiente establecidos.');
/* Procedures for user_credentials and user_obj inserts */
call insertUserCredentials("jon@gmail.com", "$2a$10$JgcLiA0DonrhMyyQj3w.U.rKCsGPFLntCBzguLpJDH6nvRek1I5Le"); /* Pass12345 */
call insertUser("Jon", "Garcia Gimenez", "1985-04-29", "83745924U", "02" , "jon@gmail.com", "$2a$10$JgcLiA0DonrhMyyQj3w.U.rKCsGPFLntCBzguLpJDH6nvRek1I5Le",1,1);
call insertUserCredentials("Francisco@gmail.com", "$2a$10$JPLdWVEyoNhQhlaU8MsNvOmIVPgVuvv4W0TA6f5.jKihNpGK8knXq"); /* Fran0987 */
call insertUser("Francisco", "Perez Trabuco", "1982-11-12", "98736410L", "02", "Francisco@gmail.com", "$2a$10$JPLdWVEyoNhQhlaU8MsNvOmIVPgVuvv4W0TA6f5.jKihNpGK8knXq",2,1);
call insertUserCredentials("guille@yahoo.es", "$2a$10$rkNrx7AVnB1HgEcFRhyAU.FQ/wmjOlbyk13WoSWzC5DyAMBcwGaDK"); /* Guille1990 */
call insertUser("Guillermo", "Huertas Romero", "1990-05-01", "47739814W", "02", "guille@yahoo.es", "$2a$10$rkNrx7AVnB1HgEcFRhyAU.FQ/wmjOlbyk13WoSWzC5DyAMBcwGaDK",3,1);
call insertUserCredentials("rrcastro@hotmail.com", "$2a$10$gHN0/ptYuLs2Z3HfUhL43uZNBiHze1sqmgdjoKxb4c3RKoI3uU0Wi"); /* CasRiv4114 */
call insertUser("Rosa", "Rivas Castro", "1985-05-01", "75047168P", "02", "rrcastro@hotmail.com", "$2a$10$gHN0/ptYuLs2Z3HfUhL43uZNBiHze1sqmgdjoKxb4c3RKoI3uU0Wi",4,1);
call insertUserCredentials("lorenaDoc@gmail.es", "$2a$10$6tYROejoBQbaj5yO7NnxUe96iPfQ7wl2VWf/q3QyMeNaeYoezCEMW"); /* lorenaDoc1234 */
call insertUser("Lorena", "Montiel Frias", "1987-03-19", "68797512G", "02", "lorenaDoc@gmail.es", "$2a$10$6tYROejoBQbaj5yO7NnxUe96iPfQ7wl2VWf/q3QyMeNaeYoezCEMW", 5, 1);
/* Inserts de credenciales y usuarios de type acc */
call insertUserCredentials("Laura@gmail.com", "$2a$10$GuYsBZYvEDr18OapLN58WuqjriUtoz/SIiB4n3hhxUCWShszjvYCu"); /* Lau10295 */
call insertUser("Laura", "Gasset Vargas", "1990-04-23", "12345678A", "03", "Laura@gmail.com", "$2a$10$GuYsBZYvEDr18OapLN58WuqjriUtoz/SIiB4n3hhxUCWShszjvYCu",null,null);
call insertUserCredentials("Tania@gmail.com", "$2a$10$CQE2rHI0O21C2AOD3nsi/uYBfIDidxajgGFnnqd3yzrDhsOmcLuCe"); /* TanTan007 */
call insertUser("Tania", "La Rubia Alvarez", "1975-04-29", "09876543D", "03", "Tania@gmail.com", "$2a$10$CQE2rHI0O21C2AOD3nsi/uYBfIDidxajgGFnnqd3yzrDhsOmcLuCe",null,null);
/* INSERT DE RELACIONES DE CURSOS */
INSERT INTO `course_subject` VALUES 
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),
(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),
(3,3),(3,4),(3,6),(3,7),(3,8),(3,9);

/* Selects */
select * from credentials;
select * from user_obj;
select * from school;
select * from _subject;
select * from course_subject;
select * from news;
select * from grades;
select cs.id_subject from user_obj as ub inner join course_subject as cs on ub.course_id = cs.id_course where ub.course_id=1 and ub.name=?; 


/* Drop Functions */
drop function checkIfSubjectExists;
drop function checkIfExistsUserCredentials;
drop function checkIfSchoolExists;

/* Drop Procedures */
drop procedure insertUserCredentials;
drop procedure insertUser;
drop procedure insertSchool;
drop procedure insertSubject;
drop procedure subjectCourse;

/* Drop database*/
drop database portalacademico;

drop trigger user_subjects;



