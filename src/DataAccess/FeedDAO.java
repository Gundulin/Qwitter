package DataAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import models.Attachment;
import models.Status;
import models.request.FeedRequest;
import models.response.FeedResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedDAO {

    private static final String tableName = "Feed";
    private static final String FOLLOWER_ATTR = "follower";
    private static final String ALIAS_ATTR = "alias";
    private static final String MESSAGE_ATTR = "message";
    private static final String TAGGED_USERS_ATTR = "taggedUsers";
    private static final String HASHTAG_ATTR = "hashtags";
    private static final String TIMESTAMP_ATTR = "timestamp";
    private static final String ATTACHMENT_ATTR = "attachment";

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard().withRegion("us-east-2").build();
    private static DynamoDB db = new DynamoDB(client);

    public FeedDAO() {}

    public FeedResponse getFeed(FeedRequest request) {

        FeedResponse response = new FeedResponse();
        Table table = db.getTable(tableName);
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#follower", FOLLOWER_ATTR);
        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":follower", new AttributeValue().withS(request.follower));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression("#follower = :follower")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.page_size);
//                .withScanIndexForward(false);

        if (request.key != null && request.key.length() > 0) {
            Map<String, AttributeValue> startKey = new HashMap<>();
//            startKey.put(FOLLOWER_ATTR, new AttributeValue().withS(request.follower));
            startKey.put(TIMESTAMP_ATTR, new AttributeValue().withS(request.key));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult qResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> items = qResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                Status status = new Status();
                status.setAlias(item.get(ALIAS_ATTR).getS());
                status.setMessage(item.get(MESSAGE_ATTR).getS());
                status.setTimestamp(item.get(TIMESTAMP_ATTR).getS());
                status.setHashtags(item.get(HASHTAG_ATTR).getSS());
                status.setTaggedUsers(item.get(TAGGED_USERS_ATTR).getSS());
                status.setAttachment(item.get(ATTACHMENT_ATTR).getS());
                response.statuses.add(status);
            }
        }

        Map<String, AttributeValue> lastKey = qResult.getLastEvaluatedKey();
        if (lastKey != null) {
            response.lastkey = lastKey.get(TIMESTAMP_ATTR).getS();
        }

        return response;
    }
}
