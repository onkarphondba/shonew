CREATE TABLE temp_dataload_rpcm_clusterfeed (
	id INTEGER NOT NULL,
	location_name VARCHAR(60),
	cluster_name VARCHAR(60),
	created TIMESTAMP NOT NULL,
	last_modified TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
);
INSERT INTO temp_dataload_rpcm_clusterfeed(id, location_name, cluster_name, created, last_modified) VALUES (1, '1234', 'HAS202', '2016-09-07 16:46:00', '2016-09-07 16:46:00');
INSERT INTO temp_dataload_rpcm_clusterfeed(id, location_name, cluster_name, created, last_modified) VALUES (2, '1235', 'HDW202', '2016-09-07 16:46:00', '2016-09-07 16:46:00');
INSERT INTO temp_dataload_rpcm_clusterfeed(id, location_name, cluster_name, created, last_modified) VALUES (3, '1236', 'HTS201', '2016-09-07 16:46:00', '2016-09-07 16:46:00');
INSERT INTO temp_dataload_rpcm_clusterfeed(id, location_name, cluster_name, created, last_modified) VALUES (4, '1237', 'HTS202', '2016-09-07 16:46:00', '2016-09-07 16:46:00');
INSERT INTO temp_dataload_rpcm_clusterfeed(id, location_name, cluster_name, created, last_modified) VALUES (5, '1239', 'HAS202', '2016-09-07 16:46:00', '2016-09-07 16:46:00');
INSERT INTO temp_dataload_rpcm_clusterfeed(id, location_name, cluster_name, created, last_modified) VALUES (6, '1240', 'HDW202', '2016-09-07 16:46:00', '2016-09-07 16:46:00');
INSERT INTO temp_dataload_rpcm_clusterfeed(id, location_name, cluster_name, created, last_modified) VALUES (7, '1241', 'HTS201', '2016-09-07 16:46:00', '2016-09-07 16:46:00');
INSERT INTO temp_dataload_rpcm_clusterfeed(id, location_name, cluster_name, created, last_modified) VALUES (8, '1242', 'HTS202', '2016-09-07 16:46:00', '2016-09-07 16:46:00');
