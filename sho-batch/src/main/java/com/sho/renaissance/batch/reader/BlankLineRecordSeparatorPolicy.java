package com.sho.renaissance.batch.reader;

import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;

/**
 * @author gdhimate
 * 
 *         This is policy to remove blank lines from file
 *
 */
public class BlankLineRecordSeparatorPolicy extends SimpleRecordSeparatorPolicy {

	@Override
	public boolean isEndOfRecord(String line) {
		if (line.trim().length() == 0) {
			return false;
		}
		return super.isEndOfRecord(line);
	}

	@Override
	public String postProcess(String record) {
		if (record == null || record.trim().length() == 0) {
			return null;
		}
		return super.postProcess(record);
	}
}
