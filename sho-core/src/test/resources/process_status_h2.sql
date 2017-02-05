drop table IF EXISTS process_status;
 
CREATE TABLE process_status (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) DEFAULT NULL,
  status varchar(100) DEFAULT NULL,
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id));

INSERT INTO process_status(id, name, status, created) VALUES (1, 'SYNCNSCLUSTERFEED', 'COMPLETED', '2016-09-08 12:35:46');
INSERT INTO process_status(id, name, status, created) VALUES (2, 'SYNCNSSTOREINVFEED', 'COMPLETED', '2016-09-08 12:38:00');

INSERT INTO process_status(id, name, status, created) VALUES (3, 'SYNCNSITEMINVFEED', 'FAILED', '2016-09-08 12:38:00');
INSERT INTO process_status(id, name, status, created) VALUES (4, 'SYNCNSHISTINVFEED', 'FAILED', '2016-09-08 12:38:00');