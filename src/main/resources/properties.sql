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
		id varchar(10) not null primary key,
		teacher_id int not null,
		student_id int,
		day varchar(10) not null,
		hour varchar(10),
		
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
		date_new timestamp,
		id_school int,
		constraint foreing_key_news foreign key (id_school) references school(id)
 
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
    
    create table if not exists teacher_subject(
		user_id int not null,
        subject_id int not null,
        
        constraint fk_user_id_teacher_subject foreign key (user_id) references user_obj(id),
        constraint fk_subject_id_teacher_subject foreign key (subject_id) references _subject(id)
    );
    
    create table if not exists school_course(
		school_id int not null,
        course_id int not null,
        
        constraint fk_school_course_school_id foreign key (school_id) references school(id),
        constraint fk_school_course_course_id foreign key (course_id) references course(id)
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
	create procedure insertNew(titleInput longtext, captionInput longtext, contentInput longtext, imgUrl longtext, captionImg longtext, id_school_inp int)
	begin
			insert into news (title, caption, content, img_url, caption_img, date_new, id_school) values (titleInput, captionInput, contentInput, imgUrl, captionImg , current_timestamp(), id_school_inp);
	end//


	delimiter //
	create trigger user_subjects
	after insert on user_obj
	for each row
	begin
	declare teacher_id int;
	declare numSubject int;
	declare cont int;
    declare cont2 int;
	declare subjectBuilder int;
    declare numTeachers int;
    
	set cont = 0;
	set cont2 = 0;
	 if (new.user_type = "01") then
		set numSubject = (select count(*) from course_subject where course_id=new.course_id);
        set numTeachers = (select count(*) from user_obj where (course_id=new.course_id and school_id=new.school_id and user_type="02"));
        while (cont2<numTeachers) do
			set teacher_id = (select id from user_obj where (course_id=new.course_id and school_id=new.school_id and user_type="02") limit cont2,1);
			while (cont<numSubject) do
				set subjectBuilder = (select subject_id from course_subject where course_id=new.course_id limit cont,1);
				INSERT INTO GRADES(teacher, student, subject_id, grade) values (teacher_id, new.id, subjectBuilder, null);
				set cont = cont + 1;
			end while;
            set cont2 = cont2 + 1;
        end while;
	  end if;
	end//

	delimiter //
	create function getAllCoursesFromTeacherId(teacherId int) returns longtext no sql
	begin
		declare courseId int;
		declare courseName varchar(255) default "";
		declare coursesList longtext default "";
		declare done bool default false;
		declare coursesIds cursor for select course_id from user_obj where id = teacherId;
		declare continue handler for not found set done = true;
		open coursesIds;
		set coursesList := "";
		loopingCourses: loop
			fetch coursesIds into courseId;
			if done = true then
				leave loopingCourses;
			end if;
			set courseName := (select course_name from course where id = courseId);
			set coursesList = CONCAT(courseName,";",coursesList);
		end loop;
		return coursesList;
	end//

	delimiter //
	create function getAllStudentsByTeacherId(teacherId int) returns longtext no sql
	begin
		declare studentId int;
		declare studentName, studentSurname varchar(255);
		declare fullname longtext default "";
		declare namesList longtext default "";
		declare done bool default false;
		declare studentsId cursor for select student from grades where teacher = teacherId;
		declare continue handler for not found set done = true;
		open studentsId;
		loopingIds: loop
			fetch studentsId into studentId;
			if done = true then
				leave loopingIds;
			end if;
			set studentName := (select user_name from user_obj where id = studentId);
			set studentSurname := (select user_surname from user_obj where id = studentId);
			set fullname := concat(studentName, " ", studentSurname);
			set namesList := concat(fullname, ";", namesList);
		end loop;
		return namesList;
	end//
    
    delimiter //
    create trigger tutorias
    after insert on user_obj
    for each row
    begin
		if new.user_type = "02" then
        INSERT INTO appointment values 
			(CONCAT('L5_', NEW.id), NEW.id, NULL, "20", "17:00"),
            (CONCAT('M5_', NEW.id), NEW.id, NULL, "21", "17:00"),
            (CONCAT('X5_', NEW.id), NEW.id, NULL, "22", "17:00"),
            (CONCAT('J5_', NEW.id), NEW.id, NULL, "23", "17:00"),
            (CONCAT('V5_', NEW.id), NEW.id, NULL, "24", "17:00"),
            (CONCAT('L6_', NEW.id), NEW.id, NULL, "20", "18:00"),
            (CONCAT('M6_', NEW.id), NEW.id, NULL, "21", "18:00"),
            (CONCAT('X6_', NEW.id), NEW.id, NULL, "22", "18:00"),
            (CONCAT('J6_', NEW.id), NEW.id, NULL, "23", "18:00"),
            (CONCAT('V6_', NEW.id), NEW.id, NULL, "24", "18:00"),
            (CONCAT('L8_', NEW.id), NEW.id, NULL, "20", "19:00"),
            (CONCAT('M8_', NEW.id), NEW.id, NULL, "21", "19:00"),
            (CONCAT('X8_', NEW.id), NEW.id, NULL, "22", "19:00"),
            (CONCAT('J8_', NEW.id), NEW.id, NULL, "23", "19:00"),
            (CONCAT('V8_', NEW.id), NEW.id, NULL, "24", "19:00");
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
	(9, "Implantacion de sistemas operativos", 8, 256),
	(10, "Planificacion y administracion de redes", 6, 192),
	(11, "Fundamentos de hardware", 8, 256);
	/* Creacion de cursos */
	INSERT INTO `course` VALUES
	 (1,'Desarrollo de aplicaciones Multiplataforma', "DAM", 'La competencia general de este título consiste en desarrollar, implantar, documentar y mantener aplicaciones informáticas multiplataforma, utilizando tecnologías y entornos de desarrollo específicos, garantizando el acceso a los datos de forma segura y cumpliendo los criterios de «usabilidad» y calidad exigidas en los estándares establecidos.'),
	 (2,'Desarrollo de Aplicaciones Web',"DAW",'La competencia general de este título consiste en desarrollar, implantar, y mantener aplicaciones web, con independencia del modelo empleado y utilizando tecnologías específicas, garantizando el acceso a los datos de forma segura y cumpliendo los criterios de accesibilidad, usabilidad y calidad exigidas en los estándares establecidos.'),
	 (3,'Administración de sistemas infomrmáticos y redes',"ASIR",'La competencia general de este título consiste en instalar, configurar y mantener sistemas microinformáticos, aislados o en red, así como redes locales en pequeños entornos, asegurando su funcionalidad y aplicando los protocolos de calidad, seguridad y respeto al medio ambiente establecidos.');
	/* Procedures for user_credentials and user_obj inserts */
    /* Profesores de Cesur */ -- Insertar 1 profesor mas por modulo
	call insertUserCredentials("jon@gmail.com", "$2a$10$JgcLiA0DonrhMyyQj3w.U.rKCsGPFLntCBzguLpJDH6nvRek1I5Le"); /* Pass12345 */
	call insertUser("Jon", "Garcia Gimenez", "1985-04-29", "83745924U", "02" , "jon@gmail.com", "$2a$10$JgcLiA0DonrhMyyQj3w.U.rKCsGPFLntCBzguLpJDH6nvRek1I5Le",1,1);
    call insertUserCredentials("victorCesur@cesurformacion.com", "$2a$10$rqjQSe7URnzLlnO/tcZj.eS8Z3rnBzgu0dqsbaSkFo/4GDNQln76m"); /* Victor1234 */
	call insertUser("Victor", "Rivaldo Azpilicueta", "1986-10-21", "Z7539006W", "02" , "victorCesur@cesurformacion.com", "$2a$10$rqjQSe7URnzLlnO/tcZj.eS8Z3rnBzgu0dqsbaSkFo/4GDNQln76m",1,1);
    call insertUserCredentials("luciano@yahoo.com", "$2a$10$4ba1di0N6oXENsPpogAYAuicQSgSCmRWOCB/EIcQw8JXEuaF7PlBK"); /* Luciano1234 */
    call insertUser("Luciano", "Morentes Ruiz", "1989-01-02", "60566746H", "02", "luciano@yahoo.com","$2a$10$4ba1di0N6oXENsPpogAYAuicQSgSCmRWOCB/EIcQw8JXEuaF7PlBK", 1, 2);
    call insertUserCredentials("rigobert@yahoo.es", "$2a$10$bFfvVh9xq8bpC/HVmsq2ael7f9BsOoVPhIgsJ9voewhw7vL1Athpa"); /* Rigo12345 */
    call insertUser("Rigoberto", "Velazquez Ruiz", "1985-01-01", "Z6740559T", "02", "rigobert@yahoo.es","$2a$10$bFfvVh9xq8bpC/HVmsq2ael7f9BsOoVPhIgsJ9voewhw7vL1Athpa", 1, 2);
    call insertUserCredentials("Marta@hotmail.com", "$2a$10$0qOvOB897HwUHZDrzsxX4ORsBfT0dNg6g.VmgeWvh9wI2ArL.zvRa"); /* Marta1234 */
    call insertUser("Marta", "Cardona Severo", "1987-12-22", "74475917P", "02", "Marta@hotmail.com", "$2a$10$0qOvOB897HwUHZDrzsxX4ORsBfT0dNg6g.VmgeWvh9wI2ArL.zvRa", 1, 3);
    call insertUserCredentials("soniaMon@gmail.com", "$2a$10$Fjhzw1Wjt7nd9hqMrete1OTOtFuP1lLKphsrfWVOv4ANP31RChUi."); /* Sonia1234 */
    call insertUser("Sonia", "Montero Silvina", "1983-12-22", "61274427N", "02", "soniaMon@gmail.com", "$2a$10$Fjhzw1Wjt7nd9hqMrete1OTOtFuP1lLKphsrfWVOv4ANP31RChUi.", 1, 3);
    /* Profesores de Picasso */
	call insertUserCredentials("Francisco@gmail.com", "$2a$10$JPLdWVEyoNhQhlaU8MsNvOmIVPgVuvv4W0TA6f5.jKihNpGK8knXq"); /* Fran0987 */
	call insertUser("Francisco", "Perez Trabuco", "1982-11-12", "98736410L", "02", "Francisco@gmail.com", "$2a$10$JPLdWVEyoNhQhlaU8MsNvOmIVPgVuvv4W0TA6f5.jKihNpGK8knXq",2,1);
    call insertUserCredentials("marco.rivera@gmail.com", "$2a$10$GVhNjO9qMhDGPg.m6A6K7.wgrXKR8kpSUvs2.Ts/EVF.mTO6UoGfe"); /* Marco1234 */
	call insertUser("Marco", "Rivera González", "1990-05-23", "51587689T", "02", "marco.rivera@gmail.com", "$2a$10$GVhNjO9qMhDGPg.m6A6K7.wgrXKR8kpSUvs2.Ts/EVF.mTO6UoGfe", 2, 1);
    call insertUserCredentials("manolito@gmail.es", "$2a$10$gdYhX1FMCnqgkQuiqD5ZR.v2BKqQkMC1FieI4QQ/54TlBbbHs.boW"); /* Manolo1234 */
	call insertUser("Manolo", "Romeo Santos", "1980-11-12", "31905928Y", "02", "manolito@gmail.es", "$2a$10$gdYhX1FMCnqgkQuiqD5ZR.v2BKqQkMC1FieI4QQ/54TlBbbHs.boW",2,2);
    call insertUserCredentials("ana.martinez@yahoo.com", "$2a$10$Ht4WgvEVXwzSVZvOcN7q9.Qbt9fLK3wajJMREHvwAwDLH8ZCHjzNK"); /* Ana56781 */
	call insertUser("Ana", "Martínez López", "1985-03-14", "95392883S", "02", "ana.martinez@yahoo.com", "$2a$10$Ht4WgvEVXwzSVZvOcN7q9.Qbt9fLK3wajJMREHvwAwDLH8ZCHjzNK", 2, 2);
    call insertUserCredentials("ignacio@hotmail.com", "$2a$10$ICiXygfTa5Hn5ep.8gwmzuTsj.nn0bi3I8WwVSPhGNCTB7RfUZlPa"); /* Ignacio1234 */
	call insertUser("Ignacio", "Martinez Orozco", "1985-07-20", "50793526G", "02", "ignacio@hotmail.com", "$2a$10$ICiXygfTa5Hn5ep.8gwmzuTsj.nn0bi3I8WwVSPhGNCTB7RfUZlPa",2,3);
    call insertUserCredentials("juan.gomez@hotmail.com", "$2a$10$F5JhHafm/ayj2zx6.dvEKOpHnS6sUuNP/wHOXdj6eKulQdscUv3iK"); /* Juan0912 */
	call insertUser("Juan", "Gómez Pérez", "1978-07-19", "03403804B", "02", "juan.gomez@hotmail.com", "$2a$10$F5JhHafm/ayj2zx6.dvEKOpHnS6sUuNP/wHOXdj6eKulQdscUv3iK", 2, 3);
     /* Profesores de Belen */
	call insertUserCredentials("guille@yahoo.es", "$2a$10$rkNrx7AVnB1HgEcFRhyAU.FQ/wmjOlbyk13WoSWzC5DyAMBcwGaDK"); /* Guille1990 */
	call insertUser("Guillermo", "Huertas Romero", "1990-05-01", "47739814W", "02", "guille@yahoo.es", "$2a$10$rkNrx7AVnB1HgEcFRhyAU.FQ/wmjOlbyk13WoSWzC5DyAMBcwGaDK",3,1);
    call insertUserCredentials("laura.rodriguez@outlook.com", "$2a$10$nsFn7WlAVWYk.J400a3MqeX3fDCNRgyWPAlPzYXswRFAxzQJVlYD6"); /* Laura3456 */
	call insertUser("Laura", "Rodríguez Sánchez", "1992-11-05", "15805637Z", "02", "laura.rodriguez@outlook.com", "$2a$10$nsFn7WlAVWYk.J400a3MqeX3fDCNRgyWPAlPzYXswRFAxzQJVlYD6", 3, 1);
    call insertUserCredentials("ramon@gmail.es", "$2a$10$CJqxxqcjHTc77ZSKhtUUqeyoXbiaZQMB9dzZvcDbnZmEt/XNS3L3u"); /* Ramon1234 */
	call insertUser("Ramon", "Pepito Porras", "1991-05-01", "X3873655H", "02", "ramon@gmail.es", "$2a$10$CJqxxqcjHTc77ZSKhtUUqeyoXbiaZQMB9dzZvcDbnZmEt/XNS3L3u",3,2);
    call insertUserCredentials("david.torres@gmail.com", "$2a$10$yVG6hkHnfxX0CjCA1ACtmudpqvxvAdESHWWIT5AokGkmhWOBUWFsm"); /* David7890 */
	call insertUser("David", "Torres Ramírez", "1983-02-28", "50072435D", "02", "david.torres@gmail.com", "$2a$10$yVG6hkHnfxX0CjCA1ACtmudpqvxvAdESHWWIT5AokGkmhWOBUWFsm", 3, 2);
    call insertUserCredentials("lucia@yahoo.es", "$2a$10$0cfXiCE/1Sr5vBoVoQSDauWq8ZrvjBaE/DBSpu5NCWhgCIxfd1msC"); /* Lucia1234 */
	call insertUser("Lucia", "Montiel Peña", "1990-03-09", "82335304L", "02", "lucia@yahoo.es", "$2a$10$0cfXiCE/1Sr5vBoVoQSDauWq8ZrvjBaE/DBSpu5NCWhgCIxfd1msC",3,3);
    call insertUserCredentials("maria.garcia@hotmail.com", "$2a$10$O/gWDiKLyOxChwoulVT.Q.Ad40wWtT5UwkjZGuy7gHB0tusRR5riK"); /* Maria2345 */
	call insertUser("Maria", "García Fernández", "1991-08-17", "66927674C", "02", "maria.garcia@hotmail.com", "$2a$10$O/gWDiKLyOxChwoulVT.Q.Ad40wWtT5UwkjZGuy7gHB0tusRR5riK", 3, 3);
    /* Profesores de CPIFP */
	call insertUserCredentials("rrcastro@hotmail.com", "$2a$10$gHN0/ptYuLs2Z3HfUhL43uZNBiHze1sqmgdjoKxb4c3RKoI3uU0Wi"); /* CasRiv4114 */
	call insertUser("Rosa", "Rivas Castro", "1985-05-01", "75047168P", "02", "rrcastro@hotmail.com", "$2a$10$gHN0/ptYuLs2Z3HfUhL43uZNBiHze1sqmgdjoKxb4c3RKoI3uU0Wi",4,1);
    call insertUserCredentials("jose.martin@outlook.com", "$2a$10$bUXDAbjmLDw292XWDiLbwueyzMMux/pHG2uOaUm2FkfQvyIMklFxm"); /* Jose6789 */
	call insertUser("Jose", "Martin Domínguez", "1987-12-30", "80427671Y", "02", "jose.martin@outlook.com", "$2a$10$bUXDAbjmLDw292XWDiLbwueyzMMux/pHG2uOaUm2FkfQvyIMklFxm", 4, 1);
    call insertUserCredentials("mariam@cpifp.io", "$2a$10$TPGXBFxX8rCDQlIYHIpGTuVuJEZCNx.o1LjUrYLeUHGwTXsCnLPqG"); /* Mariam1234 */
	call insertUser("Mariam", "Davila Rivas", "1980-10-01", "81851351D", "02", "mariam@cpifp.io", "$2a$10$TPGXBFxX8rCDQlIYHIpGTuVuJEZCNx.o1LjUrYLeUHGwTXsCnLPqG",4,2);
    call insertUserCredentials("pablo.hernandez@gmail.com", "$2a$10$zFk9uXUsqxSGzX.7KXvI/.XYYbNSvdSqvwAgWbY1nsEbM/05zCbh2"); /* Pablo1234 */
	call insertUser("Pablo", "Hernández Jiménez", "1994-06-10", "34912595K", "02", "pablo.hernandez@gmail.com", "$2a$10$zFk9uXUsqxSGzX.7KXvI/.XYYbNSvdSqvwAgWbY1nsEbM/05zCbh2", 4, 2);
    call insertUserCredentials("moi@cpifp.io", "$2a$10$Facmvf0RDUxts/zze6Neqe7GCjdWvAxJ5IgcfYKGn1ulgNKvAVtdK"); /* Moi12345 */
	call insertUser("Moises", "Navarro Peral", "1985-05-01", "45754439X", "02", "moi@cpifp.io", "$2a$10$Facmvf0RDUxts/zze6Neqe7GCjdWvAxJ5IgcfYKGn1ulgNKvAVtdK",4,3);
    call insertUserCredentials("lucia.diaz@yahoo.com", "$2a$10$b133JhplwK0lG6z7Z3BOJeN.XnzJiLjj.Z1EGHPIvE8EMm41vmooO"); /* Lucia0000 */
	call insertUser("Lucia", "Díaz Ruiz", "1989-04-18", "53021104D", "02", "lucia.diaz@yahoo.com", "$2a$10$b133JhplwK0lG6z7Z3BOJeN.XnzJiLjj.Z1EGHPIvE8EMm41vmooO", 4,3); 
    /* Profesores de SanJose */
	call insertUserCredentials("lorenaDoc@gmail.com", "$2a$10$6tYROejoBQbaj5yO7NnxUe96iPfQ7wl2VWf/q3QyMeNaeYoezCEMW"); /* lorenaDoc1234 */
	call insertUser("Lorena", "Montiel Frias", "1987-03-19", "68797512G", "02", "lorenaDoc@gmail.com", "$2a$10$6tYROejoBQbaj5yO7NnxUe96iPfQ7wl2VWf/q3QyMeNaeYoezCEMW", 5, 1);
    call insertUserCredentials("fernando.santos@hotmail.com", "$2a$10$iGfUJDCvZF8SsrcrCNtAdudRCR0RaJibromJ.7SQDWTCfyaqq8u7C"); /* Fer78901 */
	call insertUser("Fernando", "Santos Morales", "1986-09-25", "27065670Y", "02", "fernando.santos@hotmail.com", "$2a$10$iGfUJDCvZF8SsrcrCNtAdudRCR0RaJibromJ.7SQDWTCfyaqq8u7C", 5, 1);
    call insertUserCredentials("jules@gmail.com", "$2a$10$14GHq3E81STLFVntY6vU1O3uCEKLRBJoq0lWHa1OD2drQk.zDraZy"); /* Jules1234 */
	call insertUser("Julian", "Ferrer Salcedo", "1987-06-25", "34588381S", "02", "jules@gmail.com", "$2a$10$14GHq3E81STLFVntY6vU1O3uCEKLRBJoq0lWHa1OD2drQk.zDraZy", 5, 2);
    call insertUserCredentials("marta.suarez@gmail.com", "$2a$10$bVBQ3NVRjKdpLOItFJwpJ.VaCfnzpJAONb/jAYC738CnvM8WEuB.a"); /* Marta0987 */
	call insertUser("Marta", "Suárez Gómez", "1993-01-21", "97227922E", "02", "marta.suarez@gmail.com", "$2a$10$bVBQ3NVRjKdpLOItFJwpJ.VaCfnzpJAONb/jAYC738CnvM8WEuB.a", 5, 2);
    call insertUserCredentials("jacinto@hotmail.com", "$2a$10$dWhbVGmsZRbom2ECWRr4.ecSA6Gi8mcU4jpNn3m3aRFPuQ3IPoQ/2"); /* Jacin1234 */
	call insertUser("Jacinto", "Peris Stivaliz", "1992-03-03", "38825794T", "02", "jacinto@hotmail.com", "$2a$10$dWhbVGmsZRbom2ECWRr4.ecSA6Gi8mcU4jpNn3m3aRFPuQ3IPoQ/2", 5, 3);
    call insertUserCredentials("alejandro.lopez@yahoo.com", "$2a$10$nmwsvMCczldfdlnMvBp.QuwLrvt.hqKS.aH7pEXiBaIiA6JWI.9Dm"); /* Alex5678 */
	call insertUser("Alejandro", "López Vega", "1984-10-03", "64834083G", "02", "alejandro.lopez@yahoo.com", "$2a$10$nmwsvMCczldfdlnMvBp.QuwLrvt.hqKS.aH7pEXiBaIiA6JWI.9Dm", 5, 3);
	/* Inserts de credenciales y usuarios de type acc */
	call insertUserCredentials("Laura@gmail.com", "$2a$10$GuYsBZYvEDr18OapLN58WuqjriUtoz/SIiB4n3hhxUCWShszjvYCu"); /* Lau10295 */
	call insertUser("Laura", "Gasset Vargas", "1990-04-23", "12345678A", "03", "Laura@gmail.com", "$2a$10$GuYsBZYvEDr18OapLN58WuqjriUtoz/SIiB4n3hhxUCWShszjvYCu",null,null);
	call insertUserCredentials("Tania@gmail.com", "$2a$10$CQE2rHI0O21C2AOD3nsi/uYBfIDidxajgGFnnqd3yzrDhsOmcLuCe"); /* TanTan007 */
	call insertUser("Tania", "La Rubia Alvarez", "1995-04-29", "09876543D", "03", "Tania@gmail.com", "$2a$10$CQE2rHI0O21C2AOD3nsi/uYBfIDidxajgGFnnqd3yzrDhsOmcLuCe",null,null);
	/* INSERT DE RELACIONES DE CURSOS */
	INSERT INTO `course_subject` VALUES 
	(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),
	(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),
	(3,3),(3,4),(3,6),(3,9),(3,10),(3,11);
	/* INSERT DE RELACIONES DE PROFESORES CON ASIGNATURAS CESUR */
    INSERT INTO teacher_subject values (1,3),(2,6),(3,5),(4,4),(5,1),(6,2);
    /* INSERT DE RELACIONES DE PROFESORES CON ASIGNATURAS PICASO */
    INSERT INTO teacher_subject values (7,3),(8,6),(9,5),(10,4),(11,1),(12,2);
    /* INSERT DE RELACIONES DE PROFESORES CON ASIGNATURAS BELEN */
    INSERT INTO teacher_subject values (13,3),(14,6),(15,5),(16,4),(17,1),(18,2);
    /* INSERT DE RELACIONES DE PROFESORES CON ASIGNATURAS CPIFP */
    INSERT INTO teacher_subject values (19,3),(20,6),(21,5),(22,4),(23,1),(24,2);
    /* INSERT DE RELACIONES DE PROFESORES CON ASIGNATURAS SAN JOSE */
    INSERT INTO teacher_subject values (25,3),(26,6),(27,5),(29,4),(30,1),(31,2);
    /* INSERT DE LAS NOTICIAS */
    call insertNew("Cesur detecta una alta demanda de formación en informática",
	'Son alumnos del ciclo de grado Superior en Desarrollo de Aplicaciones Multiplataforma',
    "Cesur ha detectado una alta demanda de formación en líneas relacionadas con la informática.<br/><br/>
    Hace unos días se celebraba el día internacional de la informática, concretamente el 9 de diciembre, en honor al nacimiento de Grace Murray Hopper, pionera en el mundo de las Ciencias de la Computación. La informática ha influenciado en el avance de la transmisión de datos e información, manteniendo a personas de cualquier parte del mundo conectadas entre sí.<br/><br/>
    Los estudios en torno a la informática son de los más demandados no solo a nivel académico, sino también en el ámbito laboral debido a las nuevas tecnologías que están en auge y en constante evolución. De hecho, la Informática es la tercera familia profesional con más estudiantes matriculados, después de Sanidad, y Administración y Gestión, según las estadísticas del alumnado de FP del curso 2021-2022, publicadas recientemente por el MEFP.<br/><br/>
    El Desarrollador de Aplicaciones Web (DAW) se destaca como un experto en la creación y optimización de aplicaciones informáticas específicamente diseñadas para entornos web. Este profesional, ya sea como empleado en empresas públicas o privadas o como trabajador independiente, se especializa en programación web, abordando lenguajes como PHP, JavaScript, entre otros.<br/><br/>
    Desarrollo de Aplicaciones Multiplataforma (DAM) abarca una gama más amplia de plataformas, incluyendo aplicaciones para dispositivos móviles y ordenadores. Aunque comparten una base común, DAM se enfoca más intensamente en el desarrollo de aplicaciones móviles a partir del segundo año de formación, utilizando herramientas específicas y continuando con el uso de JAVA.",
    "../images/imagenNoticiaCesur.png", "Centro de Cesur. / CESUR", 1);
    
	call insertNew("Estudiante del IES Pablo Picasso, segundos clasificados en un campeonato de talento empresarial",
	'Los estudiantes tienen la posibilidad de especializarse en disciplinas altamente demandadas, como ciberseguridad y "cloud computing"',
    "Tres estudiantes del instituto Pablo Picasso de la capital han quedado segundos clasificados en un campeonato sobre talento empresarial celebrado en Madrid. Se trata de los jóvenes Javier de la Torre, Jonathan Ruiz y Pablo Jiménez, que han participado en la II edición de Business Talent, un campeonato educativo organizado por Herbalife Nutrition y Praxis MMT. Son alumnos del ciclo Superior de Desarrollo de Aplicaciones Multiplataforma y durante el curso han estado desarrollando en clase con su profesor de Empresa e Iniciativa Emprendedora David Nicolás Ros este proyecto que ha merecido un segundo premio.<br/><br/>
    En primer lugar, han resultado ganadores Iria Piñeiro, Ismael Martínez, Jorge Fernández y Sara Gómez, alumnos del IES A Guía de Vigo; mientras que en tercer puesto han quedado tres alumnos de la Universidad de Vigo.<br/><br/>
    El profesor David Nicolás Ros explicó que este curso incluyó el simulador empresarial de Business Talent para hacer la materia de Empresa «lo más práctica posible». Y encontró en este grupo de alumnos, que se denominaron a sí mismos RSS, una muy buena disposición a trabajar en el proyecto. Después de pasar por octavos, cuartos y semifinales se clasificaron para la final nacional. Han quedado segundo de España, pero campeones de su grupo de simulación. El profesor destacó que, incluso cuando estaban en las prácticas de empresa, «seguían tomándose el trabajo muy en serio».",
    "../images/imagenNoticiaPabloPicasso1.png", "De izquierda a derecha, los alumnos Jonathan Ruiz Oliva, Pablo Jiménez Aguayo y Javier de la Torre Barranco, con su profesor de Empresa e Iniciativa Emprendedora David Nicolás Ros. SUR", 2);
    
    call insertNew("Juan rescata a Fernando de la mafia del IES Belén",
	'Un acto heróico que marcó un antes y un después en los estudiantes',
    "Juan Esteban Arboleda, un estudiante del Ciclo de Grado Superior en Diseño de Aplicaciones Multiplataforma ha realizado una azaña inolvidable para todos los estudiantes y personal del centro.<br/><br/>
    Su compañero de clase, Fernando, se vio forzado hace unos años a enrolarse en la mafia más peligrosa de la región, la temida mafia del IES Belén. Este, ahogado por las deudas y en acto de desespero, decidió ir y pedir ayuda a estos.<br/><br/>
    Para su mala suerte, estos tenían otros planes para él hasta que su compañero Juan, con el corazón en la mano, salvó a su amigo de las garras de la mafia.",
    "../images/imagenNoticiaBelen1.png", "Juan, icono actual del IES Belén",3);
    
    call insertNew("Charla de Ciberseguridad",
	'Son alumnos del master de ciberseguridad explicando a sus compañeros de FP',
    "Como fruto del grupo de trabajo del CEP de Málaga titulado «PROTGT: Por un mundo más ciberseguro»  que ha sido conformado por profesorado del curso de CE en Ciberseguridad del CPIFP Alan Turing, la Residencia Escolar Andalucía y el IES La Orden se ha celebrado en nuestro Centro el Taller de Concienciación en Ciberseguridad titulado «(In)seguridad en nuestros datos» que ha sido preparado e impartido en dos turnos por nuestro alumnado del CE en Ciberseguridad.<br/><br/>
    Asistieron más de 100 alumnos/as repartidos entre los cursos de 1º DAM, 1º DAW y 1º ASIR de nuestro centro así como alumnado de la etapa de formación profesional de la Residencia Escolar Andalucía y del IES La Orden.<br/><br/>
    El alumnado asistente pudo comprobar de manera práctica e interactiva la relativa facilidad con la que los atacantes pueden hacerse con nuestros datos, así como herramientas para evitarlo basándose principalmente en el uso de hábitos ciberseguros.<br/><br/>
    Nuestros visitantes de Huelva que eran en su mayoría del Ciclo Superior de Integración Social pudieron contar también con una pequeña charla a cargo de Inmaculada Reina, Marta Ariza y Francisco Javier Alcántara del CPFPE Remedios Rojo con las posibilidades e Itinerarios formativos que ofrece Empleo para ayudar a personas en situaciones desfavorecidas.", "../images/noticiaAlanTuring.jpg","",4);
	
    call insertNew("Semana Ignaciana y Fiestas Patronales 2024",
	'Semana de fiesta llena de juegos, concursos y eventos especiales',
    "Un año hicimos coincidir en fecha la Semana Ignaciana y las que son ya nuestras 49ª Fiestas Patronales, cercanos a la fiesta de San José. Así se está haciendo desde el COVID. En esta ocasión, a nivel de EDUCSI se ha vuelto a pedir la celebración de la Semana Ignaciana en toda España desde el 11 de marzo.<br/><br/>
    Por ello, como eje central e importante estos días para mostrar nuestra identidad, desde Pastoral se dispondrá de una Carpa Ignaciana donde poder conocer la vida de San Ignacio de una manera lúdica. Se complementarán con actividades festivas desde el martes 12 con el pregón de bachillerato y las prolongamos hasta el viernes día 15.<br/><br/>
    El martes 12 a las 13:30h se realiza el pregón de Bachillerato. Y el miércoles a las 11h se realizará una introducción a las Fiestas Patronales y Semana Ignaciana por parte del profesor jesuita del centro Crisanto Abeso y el coordinador de Pastoral, Antonio J. Reyes antes de vivir el cañonazo de inicio de los días de fiesta.",
    "../images/sanJoseNoticia.jpg", "",5);
SELECT distinct * FROM user_obj WHERE (user_type = '02' and course_id = 1 and school_id = 4);
    /* Selects */
	select * from credentials;
	select * from user_obj;
	select * from school;
	select * from course;
	select * from _subject;
	select * from course_subject;
	select * from news;
	select * from grades;
	select * from teacher_subject;
    select * from appointment;

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


