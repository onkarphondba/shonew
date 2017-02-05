package com.sho.renaissance.batch.writer;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.capgemini.cif.core.util.DateUtils;
import com.sho.renaissance.batch.constants.BatchConstants;


/**
 * Custom File writer sets resource with custom file name creation
 * 
 * @author gdhimate
 * 
 * @param <T>
 */
public class CustomFileWriter<T> extends FlatFileItemWriter<T> {

	private static Log logger = LogFactory.getLog(CustomFileWriter.class);

	private static final String FILE_SEPARATOR = ".";
	private static final String FILE_NAME_SEPARATOR = "_";
	private String location;
	private String fileName;
	private String fileType;

	@Override
	public void setResource(Resource resource) {
		StringBuilder resourceUrl = new StringBuilder();
        
		Resource newResource = null;
		if (resource instanceof UrlResource) {

			resourceUrl.append(getLocation());
			resourceUrl.append(getFileName());
			resourceUrl.append(
					FILE_NAME_SEPARATOR + DateUtils.formatDate(new Date(), BatchConstants.DATE_TIME_FORMAT_FILE));
			resourceUrl.append(FILE_SEPARATOR + getFileType());

			newResource = new FileSystemResource(resourceUrl.toString());

			logger.debug("File created with path " + resourceUrl.toString());
		}
		super.setResource(newResource != null ? newResource : resource);
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
