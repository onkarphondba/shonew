package com.sho.renaissance.batch.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

/**
 * @author gdhimate
 * 
 *         Custom AWS credentials provider to return static encrypted access and
 *         secret key
 *
 */
public class CustomAWSCredentialsProvider implements AWSCredentialsProvider {

	private final AWSCredentials credentials;

	public CustomAWSCredentialsProvider(AWSCredentials credentials) {
		this.credentials = credentials;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.amazonaws.auth.AWSCredentialsProvider#getCredentials()
	 */
	public AWSCredentials getCredentials() {
		return credentials;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.amazonaws.auth.AWSCredentialsProvider#refresh()
	 */
	public void refresh() {
	}

}
