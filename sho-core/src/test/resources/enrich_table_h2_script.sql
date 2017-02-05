CREATE TABLE temp_dataload_rpcm_clusterfeed_enriched (
	id INTEGER NOT NULL,
	location_name VARCHAR(60),
	location_id INTEGER,
	cluster_name VARCHAR(60),
	cluster_id INTEGER,
	input_data_error INTEGER,
	lookup_failure INTEGER,
	sent_to_netsuite INTEGER,
	created TIMESTAMP NOT NULL,
	last_modified TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
);
INSERT INTO temp_dataload_rpcm_clusterfeed_enriched(id, location_name, location_id, cluster_name, cluster_id, input_data_error, lookup_failure, sent_to_netsuite, created, last_modified) VALUES (1, '1236', 1630, 'HTS201', 1376, 0, 0, 1, '2016-09-08 19:01:42', '2016-09-08 19:01:51');
INSERT INTO temp_dataload_rpcm_clusterfeed_enriched(id, location_name, location_id, cluster_name, cluster_id, input_data_error, lookup_failure, sent_to_netsuite, created, last_modified) VALUES (2, '1235', null, 'HDW202', 1375, 0, 1, 0, '2016-09-08 19:01:42', '2016-09-08 19:01:51');
INSERT INTO temp_dataload_rpcm_clusterfeed_enriched(id, location_name, location_id, cluster_name, cluster_id, input_data_error, lookup_failure, sent_to_netsuite, created, last_modified) VALUES (3, '1240', null, 'HDW202', 1375, 0, 1, 0, '2016-09-08 19:01:42', '2016-09-08 19:01:51');
INSERT INTO temp_dataload_rpcm_clusterfeed_enriched(id, location_name, location_id, cluster_name, cluster_id, input_data_error, lookup_failure, sent_to_netsuite, created, last_modified) VALUES (4, '1241', null, 'HTS201', 1376, 0, 1, 0, '2016-09-08 19:01:42', '2016-09-08 19:01:51');
INSERT INTO temp_dataload_rpcm_clusterfeed_enriched(id, location_name, location_id, cluster_name, cluster_id, input_data_error, lookup_failure, sent_to_netsuite, created, last_modified) VALUES (5, '1237', null, 'HTS202', 1373, 0, 1, 0, '2016-09-08 19:01:42', '2016-09-08 19:01:51');
INSERT INTO temp_dataload_rpcm_clusterfeed_enriched(id, location_name, location_id, cluster_name, cluster_id, input_data_error, lookup_failure, sent_to_netsuite, created, last_modified) VALUES (6, '1242', null, 'HTS202', 1373, 0, 1, 0, '2016-09-08 19:01:42', '2016-09-08 19:01:51');
INSERT INTO temp_dataload_rpcm_clusterfeed_enriched(id, location_name, location_id, cluster_name, cluster_id, input_data_error, lookup_failure, sent_to_netsuite, created, last_modified) VALUES (7, '1234', null, 'HAS202', 1374, 0, 1, 0, '2016-09-08 19:01:42', '2016-09-08 19:01:51');
INSERT INTO temp_dataload_rpcm_clusterfeed_enriched(id, location_name, location_id, cluster_name, cluster_id, input_data_error, lookup_failure, sent_to_netsuite, created, last_modified) VALUES (8, '1239', null, 'HAS202', 1374, 0, 1, 0, '2016-09-08 19:01:42', '2016-09-08 19:01:51');
