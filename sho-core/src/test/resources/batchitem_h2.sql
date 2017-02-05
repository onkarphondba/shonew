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

INSERT INTO batch_item(id, correlation_id, item_xml_data, isValid, error_message, entity_type, sent_to_target, sent_to_target_at, created) VALUES (2, '1212', '<ItemMaster>
    <ItemCode>12677712</ItemCode>
    <Description></Description>
    <BarCode></BarCode>
    <ExternalID>2677712</ExternalID>
    <SizeName>--No Size</SizeName>
</ItemMaster>', 0, null, 'syncnsitem', null, '2016-09-09 09:43:12', '2016-09-09 09:43:12');
INSERT INTO batch_item(id, correlation_id, item_xml_data, isValid, error_message, entity_type, sent_to_target, sent_to_target_at, created) VALUES (3, '1212', '<ItemMaster>
    <ItemCode>12688212</ItemCode>
    <Description></Description>
    <BarCode></BarCode>
    <ExternalID>2688212</ExternalID>
    <SizeName>--No Size</SizeName>
</ItemMaster>', 0, null, 'syncnsitem', null, '2016-09-09 09:43:12', '2016-09-09 09:43:12');
INSERT INTO batch_item(id, correlation_id, item_xml_data, isValid, error_message, entity_type, sent_to_target, sent_to_target_at, created) VALUES (4, '1212', '<ItemMaster>
    <ItemCode>12677713</ItemCode>
    <Description></Description>
    <BarCode></BarCode>
    <ExternalID>2677713</ExternalID>
    <SizeName>--No Size</SizeName>
</ItemMaster>', 1, null, 'syncnsitem', null, '2016-09-09 09:43:12', '2016-09-09 09:43:12');
INSERT INTO batch_item(id, correlation_id, item_xml_data, isValid, error_message, entity_type, sent_to_target, sent_to_target_at, created) VALUES (5, '1212', '<ItemMaster>
    <ItemCode>12673983</ItemCode>
    <Description></Description>
    <BarCode></BarCode>
    <ExternalID>2673983</ExternalID>
    <SizeName>--No Size</SizeName>
</ItemMaster>', 1, null, 'syncnsitem', null, '2016-09-09 09:43:12', '2016-09-09 09:43:12');

INSERT INTO batch_item(id, correlation_id, item_xml_data, isValid, error_message, entity_type, sent_to_target, sent_to_target_at, created) VALUES (6, '1213', '<ItemMaster>
    <ItemCode>12647712</ItemCode>
    <Description></Description>
    <BarCode></BarCode>
    <ExternalID>2477712</ExternalID>
    <SizeName>--No Size</SizeName>
</ItemMaster>', 1, null, 'syncnsstore', null, '2016-09-09 09:43:12', '2016-09-09 09:43:12');
INSERT INTO batch_item(id, correlation_id, item_xml_data, isValid, error_message, entity_type, sent_to_target, sent_to_target_at, created) VALUES (7, '1213', '<ItemMaster>
    <ItemCode>12677412</ItemCode>
    <Description></Description>
    <BarCode></BarCode>
    <ExternalID>2477712</ExternalID>
    <SizeName>--No Size</SizeName>
</ItemMaster>', 1, null, 'syncnsstore', null, '2016-09-09 09:43:12', '2016-09-09 09:43:12');
INSERT INTO batch_item(id, correlation_id, item_xml_data, isValid, error_message, entity_type, sent_to_target, sent_to_target_at, created) VALUES (8, '1213', '<ItemMaster>
    <ItemCode>12677714</ItemCode>
    <Description></Description>
    <BarCode></BarCode>
    <ExternalID>2674712</ExternalID>
    <SizeName>--No Size</SizeName>
</ItemMaster>', 1, null, 'syncnsstore', null, '2016-09-09 09:43:12', '2016-09-09 09:43:12');
