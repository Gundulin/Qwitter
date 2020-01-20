package DataAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Attribute;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import models.Hashtag;
import models.Status;
import models.request.StoryRequest;
import models.response.StoryResponse;

import java.util.*;

public class StoryDAO {

    private static final String tableName = "Story";
    private static final String AliasAttr = "alias";
    private static final String MESSAGE_ATTR = "message";
    private static final String TAGGED_USERS_ATTR = "taggedUsers";
    private static final String HASHTAG_ATTR = "hashtags";
    private static final String ATACHMENT_ATTR = "attachment";
//    private static final int PAGE_SIZE = 10;
    private static final String DateAttr = "timestamp";

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard().withRegion("us-east-2").build();
    private static DynamoDB db = new DynamoDB(client);
    private static DynamoDBMapper mapper = new DynamoDBMapper(client);

    public StoryDAO() {}

    public StoryResponse getStory(StoryRequest request) {

        Table table = db.getTable(tableName);
        StoryResponse response = new StoryResponse();
        Map<String, String> alias = new HashMap<>();
        alias.put("#alias", AliasAttr); // Dependent on the alias
        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(request.userAlias));

        QueryRequest qRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression("#alias = :alias")
                .withExpressionAttributeNames(alias)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.page_size)
                .withScanIndexForward(false);

        if (request.key != null && request.key.length() > 0) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(AliasAttr, new AttributeValue().withS(request.userAlias));
            startKey.put(DateAttr, new AttributeValue().withS(request.key));

            qRequest = qRequest.withExclusiveStartKey(startKey);
        }

        QueryResult qResult = client.query(qRequest);
        List<Map<String, AttributeValue>> items = qResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                Status status = new Status();
                status.setAlias(item.get(AliasAttr).getS());
                status.setMessage(item.get(MESSAGE_ATTR).getS());
                status.setTimestamp(item.get(DateAttr).getS());
                status.setHashtags(item.get(HASHTAG_ATTR).getSS());
                status.setTaggedUsers(item.get(TAGGED_USERS_ATTR).getSS());
                status.setAttachment(item.get(ATACHMENT_ATTR).getS());
                response.statuses.add(status);
            }
        }

        Map<String, AttributeValue> lastKey = qResult.getLastEvaluatedKey();
        if (lastKey != null) {
            response.lastkey = lastKey.get(DateAttr).getS();
        }

        return response;
    }

    public void post(Status status) {
        mapper.save(status);
    }
}
