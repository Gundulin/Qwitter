package DataAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import models.request.GetUserByAliasRequest;
import models.request.LoginRequest;
import models.request.RegisterRequest;
import models.response.GetUserByAliasResponse;
import models.response.LoginResponse;
import models.response.RegisterResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDAO {

    private static  AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard().withRegion("us-east-2").build();
    private static DynamoDB db = new DynamoDB(client);
    private static final String tableName = "Users";
    private static final String FOLLOWER_TABLE = "Follower";
    private static final String ALIAS_ATTR = "alias";
    private static final String PROFILE_IMG_ATTR = "profileImage";

    public UserDAO() {}

    public void createUserTable() {
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder
//                        .EndpointConfiguration("http://localhost:8000", "us-east-2"))
//                .build();
//
//        DynamoDB db = new DynamoDB(client);

//        String tableName = "Users";

        try {
            System.out.println("Creating table: \"Users\"...");

            Table table = db.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("alias", KeyType.HASH)),
                        Arrays.asList(new AttributeDefinition("alias", ScalarAttributeType.S),
                            new AttributeDefinition("password", ScalarAttributeType.S),
                            new AttributeDefinition("profileImage", ScalarAttributeType.S),
                            new AttributeDefinition("name", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Successfully created \"Users\" table");
        } catch (Exception e) {
            System.err.println("Unable to create Users table :");
            System.err.println(e.getMessage());
        }
    }

    public LoginResponse login(LoginRequest request) {

        Table table = db.getTable(tableName);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias",request.alias);

        try {
            Item outcome = table.getItem(spec);
            LoginResponse response = new LoginResponse();
            response.alias = outcome.get("alias").toString();
            response.authToken = UUID.randomUUID().toString();
            return response;
        } catch (Exception e) {
            System.err.println("unable to read item");
        }
        return null;
    }

    public RegisterResponse register(RegisterRequest request) {

        RegisterResponse response = null;
        Table table = db.getTable(tableName);

        try {
            System.out.println("Registering user \"" + request.alias + "\"...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("alias", request.alias)
                            .with("password", request.password)
                            .with("profileImage", request.profileImage)
                            .with("name", request.firstName + " " + request.lastName));
            System.out.println("User successfully registered: " + outcome.getPutItemResult());
            response = new RegisterResponse();
            response.alias = request.alias;
            response.authToken = UUID.randomUUID().toString();
        } catch (Exception e) {
            System.err.println("Unable to register user " + request.alias);
            System.err.println(e.getMessage());
        }
        table = db.getTable(FOLLOWER_TABLE);

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("follower", request.alias)
                    .withPrimaryKey("followee", request.alias));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return response;
    }

    public GetUserByAliasResponse getUser(GetUserByAliasRequest request) {
        Table table = db.getTable(tableName);
        GetItemSpec alias = new GetItemSpec().withPrimaryKey("alias", request.alias);
        GetUserByAliasResponse response = null;
        try {
            System.out.println("Fetching user...");
            Item item = table.getItem(alias);
            response = new GetUserByAliasResponse();
            response.alias = item.get("alias").toString();
            response.profileImage = item.get("profileImage").toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return response;
    }

    public String changeProfileImage(String alias, String profileImage) {
        Table table = db.getTable(tableName);

        try {
            Map<String, String> attrNames = new HashMap<>();
            attrNames.put("#profileImage", PROFILE_IMG_ATTR);
            Map<String, Object> attrValues = new HashMap<>();
            attrValues.put(":profileImage", profileImage);

            table.updateItem(ALIAS_ATTR, alias,
                    "set #profileImage = :profileImage", attrNames, attrValues);
            return "successfully changed profile image";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return "error!";
    }
}
