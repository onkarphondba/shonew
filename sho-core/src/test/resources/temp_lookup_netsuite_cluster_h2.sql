CREATE TABLE temp_lookup_netsuite_cluster (
	id INTEGER NOT NULL,
	cluster_name VARCHAR(60) NOT NULL,
	cluster_id INTEGER,
	created TIMESTAMP NOT NULL,
	last_modified TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
);
INSERT INTO temp_lookup_netsuite_cluster(id, cluster_name, cluster_id, created, last_modified) VALUES (1, 'HTS202', 1373, '2016-09-07 15:37:07', '2016-09-07 15:37:07');
INSERT INTO temp_lookup_netsuite_cluster(id, cluster_name, cluster_id, created, last_modified) VALUES (2, 'HTS201', 1376, '2016-09-07 15:37:07', '2016-09-07 15:37:07');
INSERT INTO temp_lookup_netsuite_cluster(id, cluster_name, cluster_id, created, last_modified) VALUES (3, 'Online Price', 5, '2016-09-07 15:37:07', '2016-09-07 15:37:07');
INSERT INTO temp_lookup_netsuite_cluster(id, cluster_name, cluster_id, created, last_modified) VALUES (4, 'HAS202', 1374, '2016-09-07 15:37:08', '2016-09-07 15:37:08');
INSERT INTO temp_lookup_netsuite_cluster(id, cluster_name, cluster_id, created, last_modified) VALUES (5, 'HDW202', 1375, '2016-09-07 15:37:08', '2016-09-07 15:37:08');
