package DataAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import models.Status;
import models.request.StatusAndFollowerBatchRequest;

import java.util.*;

public class PostDAO {

    /* For the Follower table Query */
    private static final String FOLLOWER_TABLE = "Follower";
    private static final String FOLLOWER_ATTR = "follower";
    private static final String FOLLOWEE_ATTR = "followee";
    private static final String INDEX_NAME = "followee-index";
    /* For posting the actual status */
    private static final String FEED_TABLE = "Feed";
    private static final String ALIAS_ATTR = "alias";
    private static final String MESSAGE_ATTR = "message";
    private static final String TAGGED_USERS_ATTR = "taggedUsers";
    private static final String HASHTAG_ATTR = "hashtags";
    private static final String TIMESTAMP_ATTR = "timestamp";
    private static final String ATTACHMENT_ATTR = "attachment";
    private static final String POST_RESPONSE = "Status successfully posted!";


    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard().withRegion("us-east-2").build();
    private static DynamoDB db = new DynamoDB(client);
    private static DynamoDBMapper mapper = new DynamoDBMapper(client);

    public PostDAO() {}

    public String postToStory(Status status) {

        mapper.save(status); // Save the status to the Story table
        return POST_RESPONSE;
    }

    public void postToFeed(StatusAndFollowerBatchRequest request) {

        List<Item> batch = new ArrayList<>();

        Status status = request.getStatus();
        String finalAlias = "nothing";
        for (String follower : request.getAliases()) {
            Item item = new Item()
                    .withPrimaryKey(FOLLOWER_ATTR, follower)
                    .with(ALIAS_ATTR, status.getAlias())
                    .with(ATTACHMENT_ATTR, status.getAttachment())
                    .with(TIMESTAMP_ATTR, status.getTimestamp())
                    .with(TAGGED_USERS_ATTR, status.getTaggedUsers())
                    .with(HASHTAG_ATTR, status.getHashtags())
                    .with(MESSAGE_ATTR, status.getMessage());
            finalAlias = follower;
            batch.add(item);
        }

        TableWriteItems batchWriter = new TableWriteItems(FEED_TABLE)
                .withItemsToPut(batch);
        System.out.println("writing to batch with size: " + batch.size() + ", and last follower: " + finalAlias);
        BatchWriteItemOutcome outcome = db.batchWriteItem(batchWriter);

        while (outcome.getUnprocessedItems().size() > 0) {
            System.out.println("getting unprocessed items...");
            outcome = db.batchWriteItemUnprocessed(outcome.getUnprocessedItems());
        }
    }

}
