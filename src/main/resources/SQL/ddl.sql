DROP TABLE IF EXISTS thread_file CASCADE;
DROP TABLE IF EXISTS thread CASCADE;
DROP TABLE IF EXISTS job CASCADE;
DROP TABLE IF EXISTS board CASCADE;


create table board (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name varchar(100),
	last_update datetime,
	key (id)
);

create table thread (
	id BIGINT NOT NULL AUTO_INCREMENT,
	id_thread BIGINT,
	id_board BIGINT NOT NULL,
	name varchar(200),
	description varchar(5000),
	replies int,
	images int,
	active bit,
	scheduled bit,
	creation_date datetime,
	update_date datetime,
	first_post_date datetime,
	last_post_date datetime,
	key (id)
);
ALTER TABLE thread ADD CONSTRAINT FK_thread
	FOREIGN KEY (id_board) REFERENCES board (id)
;

create table thread_file (
	id BIGINT NOT NULL AUTO_INCREMENT,
	id_thread_file varchar(50),
	id_thread BIGINT NOT NULL,
	creation_date datetime,
	file_name varchar(200),
	file_extension varchar(10),
	file_size decimal,
	file_size_str varchar(50),
	file_dimension_str varchar(50),
	file_location_url varchar(500),
	status varchar(50),
	key (id)
);
ALTER TABLE thread_file ADD CONSTRAINT FK_thread_file
	FOREIGN KEY (id_thread) REFERENCES thread (id)
;

create table job (
	id BIGINT NOT NULL AUTO_INCREMENT,
	id_board BIGINT NOT NULL,
	refresh_time int,
	job_status varchar(50),
	job_mode varchar(50), 
	creation_date datetime,
	last_run_date datetime,
	key (id)
);
ALTER TABLE job ADD CONSTRAINT FK_job
	FOREIGN KEY (id_board) REFERENCES board (id)
;

create table job_filter (
	id BIGINT NOT NULL AUTO_INCREMENT,
	id_job BIGINT NOT NULL,
	id_thread BIGINT NOT NULL,
	key (id)
);
ALTER TABLE job_filter ADD CONSTRAINT FK_job_filter
	FOREIGN KEY (id_job) REFERENCES job (id)
;

select * from board;
select * from thread;
select * from thread_file;
select * from job;

insert into board (name, last_update) values ('g', null);
insert into job (id_board, refresh_time, job_status, job_mode, creation_date, last_run_date) values (1, 60, 'ACTIVE', 'ALL', now(), null);

