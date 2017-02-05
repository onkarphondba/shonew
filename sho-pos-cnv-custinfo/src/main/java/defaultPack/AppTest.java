package defaultPack;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;

public class AppTest {
	
	public final static String BUCKET_NAME = "shc-sterling"; //"sho-muleesb-dev";
	public final static String KEY = "shc-inbound/test/CashSale_Outlet_CustomerInfo_dev_20170202.txt";//"inbound/supplychain/cnvsaleshistory/cnvSalesHistory.txt";
	
	public static void main(String[] args) throws IOException {
		
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJMW2LHTDIH3MNCEQ","mgyRDnZDAGlq3ziIfF22f6HDq7i5gcJ9WHDX+jsh");
		AmazonS3 con = new AmazonS3Client(credentials);
		String csvFile = "D:/Users/ophondba/Downloads/SHO/CNV-089 split customer/CashSale_Outlet_CustomerInfo_dev_20170202.txt";//"D:/Users/ophondba/Downloads/cnvSalesHistory.txt";
		BufferedReader buffRead = new BufferedReader(new FileReader(csvFile));
		String line = "";
		String fileData="";
		//line = buffRead.readLine();
		
		while((line = buffRead.readLine())!=null)
		{
			fileData=fileData+line+"\n";
		}
				
		ByteArrayInputStream input = new ByteArrayInputStream(fileData.getBytes());
		PutObjectResult putResult = con.putObject(BUCKET_NAME, KEY, input, new ObjectMetadata());	
		System.out.println("done");
	}
}
