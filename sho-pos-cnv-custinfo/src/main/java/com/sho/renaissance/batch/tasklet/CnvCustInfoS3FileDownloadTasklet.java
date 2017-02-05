package com.sho.renaissance.batch.tasklet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.amazonaws.services.dynamodbv2.datamodeling.marshallers.ObjectToMapMarshaller;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

public class CnvCustInfoS3FileDownloadTasklet implements Tasklet, StepExecutionListener{

	
	private String fileName;
	private AmazonS3Client amazonS3Client;
	public final static String BUCKET_NAME = "shc-sterling/shc-inbound/test";
	public String filePath;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//download file from s3
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		 	
		String fileData="";
		String line="";
		
		File file = new File(filePath+fileName);
		ClassLoader loader = CnvCustInfoS3FileDownloadTasklet.class.getClassLoader();
	     
		 System.out.println(loader.getResource("CnvCustInfoS3FileDownloadTasklet.class")+"   ---------------   " +file.getAbsolutePath());
		
	     
		 if (!file.exists()) {
			file.createNewFile();
	     }
		
		 System.out.println(new Date());
		
		ObjectMetadata object = amazonS3Client.getObject(new GetObjectRequest(BUCKET_NAME,fileName), file);
		System.out.println(new Date());		
		chunkContext.getStepContext().setAttribute("resourcePath", file.getAbsolutePath());;
		return RepeatStatus.FINISHED;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public AmazonS3Client getAmazonS3Client() {
		return amazonS3Client;
	}

	public void setAmazonS3Client(AmazonS3Client amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
