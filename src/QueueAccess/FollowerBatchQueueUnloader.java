package QueueAccess;

import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import models.Status;
import models.request.StatusAndFollowerBatchRequest;
import services.PostalService;

public class FollowerBatchQueueUnloader implements RequestHandler<SQSEvent, Void> {

    private static final Gson gson = new Gson();
    private static final PostalService service = new PostalService();

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {

        StatusAndFollowerBatchRequest request = new StatusAndFollowerBatchRequest();

        for (SQSEvent.SQSMessage message : sqsEvent.getRecords()) {
            request = gson.fromJson(message.getBody(), StatusAndFollowerBatchRequest.class);
        }
        service.postToFeed(request);

        return null;
    }
}
