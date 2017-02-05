package defaultPack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.math.RandomUtils;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.sho.renaissance.batch.constants.BatchConstants;

public class test {

	public static void main(String[] args)
	{
		sendMessage();
	}
	
	public static void sendMessage() {

		String queueUrl="https://sqs.us-east-1.amazonaws.com/219923555337/sho-pos-cnvcustinfo-shc-ns-process-queue-dev";//sho-pricefeed-ecomm-ns-process-queue-dev
	       
        ClasspathPropertiesFileCredentialsProvider asdsd = new ClasspathPropertiesFileCredentialsProvider("awscred.properties");
        AmazonSQSClient client = new AmazonSQSClient(asdsd);
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.withMessageBody("Start Next Job");

        Map<String, MessageAttributeValue> messageAttributes = new HashMap<String, MessageAttributeValue>();
        messageAttributes.put(BatchConstants.CORRELATION_ID,
                     new MessageAttributeValue().withDataType("String").withStringValue(UUID.randomUUID().toString()));
        messageAttributes.put("operationFlag",
                new MessageAttributeValue().withDataType("String").withStringValue("U"));
/*        messageAttributes.put(BatchConstants.FILE_NAME,
                new MessageAttributeValue().withDataType("String").withStringValue("999"));
        messageAttributes.put(BatchConstants.IS_RESTARTABLE,
                new MessageAttributeValue().withDataType("String").withStringValue("999"));*/
        
        

        sendMessageRequest.withMessageAttributes(messageAttributes);
        sendMessageRequest.setQueueUrl(queueUrl);

        client.sendMessage(sendMessageRequest);
        System.out.println("Message sent");
        
   } 
}
