#stored proc

delimiter $$

DROP PROCEDURE IF EXISTS populate_rpcm_clusterfeed_enriched_proc$$

CREATE PROCEDURE populate_rpcm_clusterfeed_enriched_proc(IN p_cluster_id INT, OUT p_status INT, OUT p_message VARCHAR(500))
BEGIN

                DECLARE EXIT HANDLER FOR SQLEXCEPTION
                BEGIN
      GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, 
      @errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
      SET p_message = CONCAT("ERROR ", @errno, " (", @sqlstate, "): ", @text);
      SET p_status = 1;
                END;
   
   SET GLOBAL innodb_io_capacity = 2000;
   SET GLOBAL innodb_fast_shutdown = 0;

   TRUNCATE TABLE temp_dataload_rpcm_clusterfeed_enriched;

   INSERT INTO temp_dataload_rpcm_clusterfeed_enriched
   ( location_name, location_id, cluster_name, cluster_id, 
     input_data_error, lookup_failure, sent_to_netsuite
   )
   SELECT drc.location_name, lnl.location_id, drc.cluster_name, IFNULL(lnc.cluster_id, p_cluster_id),
          CASE WHEN drc.location_name IS NULL OR drc.cluster_name IS NULL THEN 1 ELSE 0 END input_data_error,
          CASE WHEN lnl.location_id IS NULL OR lnc.cluster_id IS NULL THEN 1 ELSE 0 END lookup_failure,
          0 sent_to_netsuite
     FROM temp_dataload_rpcm_clusterfeed drc
          LEFT OUTER JOIN temp_lookup_netsuite_location lnl
            ON drc.location_name = lnl.location_name
          LEFT OUTER JOIN temp_lookup_netsuite_cluster lnc
            ON drc.cluster_name = lnc.cluster_name;
   
   COMMIT;
   
   SET p_status = 0;

END $$

DELIMITER ;