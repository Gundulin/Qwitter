package QueueAccess;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import models.Status;
import services.PostalService;

public class QueueLoader {

    private static final String QUEUE_URL =
            "https://sqs.us-east-2.amazonaws.com/608158267266/qwitter_status";

    public QueueLoader() {}

    public void loadToQueue(Status status) {

        Gson gson = new Gson();
        String input = gson.toJson(status);
        int delay = 1;
        PostalService service = new PostalService();
        service.postToStory(status);

        SendMessageRequest loadQueueRequest = new SendMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMessageBody(input)
                .withDelaySeconds(delay);

        AmazonSQS queue = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult result = queue.sendMessage(loadQueueRequest);
    }
}
