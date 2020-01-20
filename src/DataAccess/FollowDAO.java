package DataAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import models.request.FollowRequest;
import models.response.FollowResponse;

public class FollowDAO {

    private static final String tableName = "Follower";
    private static final String FOLLOWER_ATTR = "follower";
    private static final String FOLLOWEE_ATTR = "followee";

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard().withRegion("us-east-2").build();
    private static DynamoDB db = new DynamoDB(client);

    public FollowDAO() {}

    public FollowResponse follow(FollowRequest request) {
        FollowResponse response = new FollowResponse();

        Table table = db.getTable(tableName);

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(FOLLOWER_ATTR, request.follower)
                    .with(FOLLOWEE_ATTR, request.followee));
            response.message = "Successfully created follower relationship for " +
                    request.follower + " and " + request.followee + ".";

        } catch (Exception e) {
            response.message = "Unable create follower relationship for " + request.follower
                    + " and " + request.followee + ".";
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
