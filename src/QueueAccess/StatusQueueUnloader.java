package QueueAccess;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import models.Status;
import models.request.FollowerRequest;
import models.request.StatusAndFollowerBatchRequest;
import models.response.FollowerResponse;
import services.FollowerService;
import services.PostalService;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class StatusQueueUnloader implements RequestHandler<SQSEvent, Void> {

    private static final String QUEUE_URL =
            "https://sqs.us-east-2.amazonaws.com/608158267266/post_qwitter";
    private static final Gson gson = new Gson();

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {

        /* First step: get the Status from the queue */

        Status status = new Status();

//        System.out.println("Pulling Status from queue....");

        for (SQSEvent.SQSMessage message : sqsEvent.getRecords()) {

//            System.out.println(message.getBody());
            status = gson.fromJson(message.getBody(), Status.class);
//            System.out.println(message.getBody());
        }
//        System.out.println("Status alias: " + status.getAlias());
//        System.out.println("Loading status to queue...");
        loadToQueue(status);

        return null;


    }

    /* Second step: get all the followers in batches and send them to the follower queue */
    private void loadToQueue(Status status) {
        FollowerRequest followerRequest = new FollowerRequest();
        FollowerResponse response;
        FollowerService service = new FollowerService();

        int delay = 0;
        followerRequest.page_size = 25; // TODO: test and make sure this number is reasonable
        StatusAndFollowerBatchRequest sAFBRequest = new StatusAndFollowerBatchRequest();
        sAFBRequest.status = status;
        followerRequest.follower = status.getAlias();
//        System.out.println("Pulling batches...");

        while (true) {
            response = service.getFollowers(followerRequest); // Get a batch of followers
            followerRequest.key = response.lastkey; // Update the key
            sAFBRequest.aliases = response.aliases; // Update the page of aliases
            String input = gson.toJson(sAFBRequest);
            SendMessageRequest loadQueueRequest = new SendMessageRequest()
                    .withQueueUrl(QUEUE_URL)
                    .withMessageBody(input)
                    .withDelaySeconds(delay);

            AmazonSQS queue = AmazonSQSClientBuilder.defaultClient();
            queue.sendMessage(loadQueueRequest);
            if (response.lastkey == null) {
                break;
            }
        }
    }
}

/*
* This one is supposed to grab the status then send batches of the followers to the second queue for the
* FollowerBatchQueueUnloader.
* */
