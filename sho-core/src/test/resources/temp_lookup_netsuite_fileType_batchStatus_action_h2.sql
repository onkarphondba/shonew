create table temp_lookup_ns_batch_status(
status_id varchar(100) not null,
status varchar(100) not null,
created TIMESTAMP NOT NULL,
last_modified TIMESTAMP NOT NULL,
primary key(status_id)
);
create table temp_lookup_netsuite_filetype(
filetype varchar(30),
filetype_id varchar(1),
primary key (filetype_id)
);
create table temp_lookup_netsuite_action(
action varchar(30),
action_id varchar(1),
primary key (action_id)
);

create table temp_delete_old_records(
status_id varchar(100) not null,
status varchar(100) not null,
created TIMESTAMP NOT NULL,
last_modified TIMESTAMP NOT NULL,
primary key(status_id)
);

insert into temp_lookup_ns_batch_status(status_id,status,created,last_modified) values('Failed','1','2016-09-07 15:37:07', '2016-09-07 15:37:07');
insert into temp_lookup_ns_batch_status(status_id,status,created,last_modified) values('File 1 Processed','4','2016-09-07 15:37:07', '2016-09-07 15:37:07');
insert into temp_lookup_ns_batch_status(status_id,status,created,last_modified) values('Partially Processed','3','2016-09-07 15:37:07', '2016-09-07 15:37:07');
insert into temp_lookup_ns_batch_status(status_id,status,created,last_modified) values('Processed','2','2016-09-07 15:37:07', '2016-09-07 15:37:07');

insert into temp_lookup_netsuite_filetype values('Item','1');
insert into temp_lookup_netsuite_filetype values('Item Descriptive Attributes','2');
insert into temp_lookup_netsuite_filetype values('Assets','3');
insert into temp_lookup_netsuite_filetype values('Images','4');
insert into temp_lookup_netsuite_filetype values('Related Items','5');
insert into temp_lookup_netsuite_filetype values('Site Categories','6');
insert into temp_lookup_netsuite_filetype values('DC Availability','7');

insert into temp_lookup_netsuite_action values('Update','1');
insert into temp_lookup_netsuite_action values('Create','2');
insert into temp_lookup_netsuite_action values('Delete','3');
insert into temp_lookup_netsuite_action values('Error','4');
insert into temp_lookup_netsuite_action values('Inactive Item','5');
insert into temp_lookup_netsuite_action values('Create Item','6');

insert into temp_delete_old_records(status_id,status,created,last_modified) values('Failed','1','2016-09-07 15:37:07', '2016-09-07 15:37:07');
insert into temp_delete_old_records(status_id,status,created,last_modified) values('File 1 Processed','4','2016-09-07 15:37:07', '2016-09-07 15:37:07');
insert into temp_delete_old_records(status_id,status,created,last_modified) values('Partially Processed','3','2016-09-07 15:37:07', '2016-09-07 15:37:07');
insert into temp_delete_old_records(status_id,status,created,last_modified) values('Processed','2','2016-09-07 15:37:07', '2016-09-07 15:37:07');
