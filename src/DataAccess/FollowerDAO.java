package DataAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import models.request.FollowerRequest;
import models.response.FollowerResponse;

import java.nio.file.attribute.AttributeView;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FollowerDAO {

    private static final String tableName = "Follower";
    private static final String FOLLOWER_ATTR = "follower";
    private static final String FOLLOWEE_ATTR = "followee";
    private static final String INDEX_NAME = "followee-index";

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard().withRegion("us-east-2").build();
    private static DynamoDB db = new DynamoDB(client);

    public FollowerDAO() {}

    public FollowerResponse getFollowers(FollowerRequest request) {
        FollowerResponse response = new FollowerResponse();

        Table table = db.getTable(tableName);

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#followee", FOLLOWEE_ATTR);
        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followee", new AttributeValue().withS(request.follower));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression("#followee = :followee")
                .withIndexName(INDEX_NAME)
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.page_size);

        if (request.key != null && request.key.length() > 0) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FOLLOWEE_ATTR, new AttributeValue().withS(request.follower));
            startKey.put(FOLLOWER_ATTR, new AttributeValue().withS(request.key));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult result = client.query(queryRequest);
        List<Map<String, AttributeValue>> items = result.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                response.aliases.add(item.get(FOLLOWER_ATTR).getS());
            }
        }

        Map<String, AttributeValue> lastKey = result.getLastEvaluatedKey();
        if (lastKey != null) {
            response.lastkey = lastKey.get(FOLLOWER_ATTR).getS();
        }


        return response;
    }

    public FollowerResponse getSubscriptions(FollowerRequest request) {
        FollowerResponse response = new FollowerResponse();

        Table table = db.getTable(tableName);

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#follower", FOLLOWER_ATTR);
        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":follower", new AttributeValue().withS(request.follower));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression("#follower = :follower")
//                .withIndexName(INDEX_NAME)
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.page_size);

        if (request.key != null && request.key.length() > 0) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FOLLOWER_ATTR, new AttributeValue().withS(request.follower));
            startKey.put(FOLLOWEE_ATTR, new AttributeValue().withS(request.key));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult result = client.query(queryRequest);
        List<Map<String, AttributeValue>> items = result.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                response.aliases.add(item.get(FOLLOWEE_ATTR).getS());
            }
        }

        Map<String, AttributeValue> lastKey = result.getLastEvaluatedKey();
        if (lastKey != null) {
            response.lastkey = lastKey.get(FOLLOWEE_ATTR).getS();
        }

        return response;
    }

    public FollowerResponse getAllFollowers(FollowerRequest request) {
        FollowerResponse response = new FollowerResponse();

        Table table = db.getTable(tableName);

        Map<String, Object> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":follower", request.follower);

        ItemCollection<ScanOutcome> items = table.scan("follower = :follower",
                "follower, followee", null, expressionAttributeValues);

        Iterator<Item> iter = items.iterator();

        while (iter.hasNext()) {
            Item item = iter.next();
            response.aliases.add(item.getString(FOLLOWER_ATTR));
        }
        return response;
    }


}
