package com.sho.renaissance.batch.writer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.capgemini.cif.core.util.DateUtils;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.util.CommonUtils;

/**
 * This class is used as File writer to amazon s3 in spring batch
 * It creates the input stream which is directly written into s3
 * 
 * @author gdhimate
 *
 * @param <T>
 */
public class CustomFileWriterToAmazonS3<T> extends AbstractItemStreamItemWriter<T>{

	private static Log logger = LogFactory.getLog(CustomFileWriterToAmazonS3.class);
	
	private String location;
	private String interfaceName;
	private String fileType;
	private String delimiter;
	private LineAggregator<T> lineAggregator;
	private String headerCallback;
	private String footerCallback;
	
	/*
	 * (non-Javadoc) This method Creates the data to be written into file
	 */
	@Override
	public void write(List<? extends T> items) {
		StringBuffer lines = new StringBuffer();
  
		lines.append(createHeader(headerCallback));
		for (T item : items) {
			lines.append(lineAggregator.aggregate(item));
            lines.append(BatchConstants.DEFAULT_LINE_SEPARATOR);
		}
		lines.append(createFooter(footerCallback));
		logger.debug("Starting to write file to s3.......");
		writeTos3(lines.toString());
		logger.debug("Finished writting file to s3.......");

	}
	
	public void writeTos3(String line) {
		try {

			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(line.getBytes()));
			AWSCredentials credentials = new BasicAWSCredentials(CommonUtils.getPropertyFromClassPath(BatchConstants.AWS_PROPERTY_FILE,BatchConstants.S3_ACCESS_KEY),
					CommonUtils.getPropertyFromClassPath(BatchConstants.AWS_PROPERTY_FILE,BatchConstants.S3_SECRET_KEY));
			AmazonS3 conn = new AmazonS3Client(credentials);

			conn.putObject(new PutObjectRequest(location, getFileName(), inputStream, getObjectMetadata()));
			
		} catch (AmazonServiceException ase) {
			logger.error("Caught an AmazonServiceException, which "
					+ "means request made to Amazon S3, but was rejected with an error response " + "for some reason.");
			logger.error("############## Error details ###########");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("HTTP Status Code: " + ase.getStatusCode());
			logger.error("AWS Error Code:   " + ase.getErrorCode());
			logger.error("Error Type:       " + ase.getErrorType());
			logger.error("Request ID:       " + ase.getRequestId());

		} catch (AmazonClientException ace) {
			logger.error("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			logger.error("############## Error details ###########");
			logger.error("Error Message: " + ace.getMessage());

		} catch (Exception e) {
			logger.error("Exception uploading file");
		}

	}

	/**
	 * @return
	 * 
	 * 		This method creates the file name for the file to be uploaded to
	 *      s3 File Name Template(exp) :InterfaceName_yyMMdd_hhmmssSSS.txt
	 *     
	 */
	private String getFileName() {

		StringBuilder fileName = new StringBuilder();
		fileName.append(interfaceName);
		fileName.append(BatchConstants.FILE_NAME_SEPARATOR);
		fileName.append(DateUtils.formatDate(new Date(), BatchConstants.DATE_TIME_FORMAT_S3_FILE));
		fileName.append(BatchConstants.FILE_SEPARATOR);
		fileName.append(fileType);

		logger.debug(
				"File with name [" + fileName.toString() + "] created to Upload it to s3 location [" + location + "] ");

		return fileName.toString().trim();
	}

	/**
	 * @return
	 * 
	 * 		This method provides metadata for file to be uploaded to s3
	 */
	private ObjectMetadata getObjectMetadata() {
		return new ObjectMetadata();
	}

	/**
	 * @param headerColumns
	 * @return This method creates the header for the file to be uploaded to s3
	 */
	private String createHeader(String headerColumns) {
		return headerColumns.replaceAll(",", delimiter).concat(BatchConstants.DEFAULT_LINE_SEPARATOR);
	}

	/**
	 * @param footer
	 * @return This method creates the footer for the file to be uploaded to s3
	 * 
	 */
	private String createFooter(String footer) {
		return footer;
	}

	public LineAggregator<T> getLineAggregator() {
		return lineAggregator;
	}

	public void setLineAggregator(LineAggregator<T> lineAggregator) {
		this.lineAggregator = lineAggregator;
	}

	public String getHeaderCallback() {
		return headerCallback;
	}

	public void setHeaderCallback(String headerCallback) {
		this.headerCallback = headerCallback;
	}

	public String getFooterCallback() {
		return footerCallback;
	}

	public void setFooterCallback(String footerCallback) {
		this.footerCallback = footerCallback;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

}
