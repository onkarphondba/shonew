package com.sho.renaissance.batch.writer;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

/**
 * Abstract class for all header and footer writer
 * 
 * @author gdhimate
 *
 */
public class BatchHeaderFooterWriter implements FlatFileHeaderCallback, FlatFileFooterCallback {

	private String delimiter;
	private String headerColumns;
	private String footer;

	@Override
	public void writeHeader(Writer writer) throws IOException {
		StringBuilder header = new StringBuilder();
		String[] splittedColumns = StringUtils.split(getHeaderColumns(), ",");
		for (String column : splittedColumns) {
			header.append(column);
			if (splittedColumns[splittedColumns.length - 1] != column) {
				header.append(getDelimiter());
			}
		}
		writer.write(header.toString());
	}

	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.write(getFooter());

	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getHeaderColumns() {
		return headerColumns;
	}

	public void setHeaderColumns(String headerColumns) {
		this.headerColumns = headerColumns;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

}
