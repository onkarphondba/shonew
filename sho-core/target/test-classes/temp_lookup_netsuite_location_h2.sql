CREATE TABLE temp_lookup_netsuite_location (
	id INTEGER NOT NULL,
	location_name VARCHAR(60) NOT NULL,
	location_id INTEGER,
	created TIMESTAMP NOT NULL,
	last_modified TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
);
INSERT INTO temp_lookup_netsuite_location(id, location_name, location_id, created, last_modified) VALUES (410, '1236', 1630, '2016-09-07 15:37:35', '2016-09-07 15:37:35');
