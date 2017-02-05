CREATE TABLE batch_item (
	id BIGINT NOT NULL,
	correlation_id VARCHAR(100),
	item_xml_data VARCHAR(2147483647),
	isValid INTEGER,
	error_message VARCHAR(2147483647),
	entity_type VARCHAR(100) NOT NULL,
	sent_to_target INTEGER,
	sent_to_target_at TIMESTAMP NOT NULL,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
);