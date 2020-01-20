package austen.cs340.qwitter.server_proxy;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.model.requests_and_responses.request.FollowerRequest;
import austen.cs340.qwitter.model.requests_and_responses.request.LoginRequest;
import austen.cs340.qwitter.model.requests_and_responses.request.ProfileImageRequest;
import austen.cs340.qwitter.model.requests_and_responses.request.RegisterRequest;
import austen.cs340.qwitter.model.requests_and_responses.request.UploadImageRequest;
import austen.cs340.qwitter.model.requests_and_responses.response.FeedResponse;
import austen.cs340.qwitter.model.requests_and_responses.response.FollowerResponse;
import austen.cs340.qwitter.model.requests_and_responses.response.GetUserByAliasResponse;
import austen.cs340.qwitter.model.requests_and_responses.response.LoginResponse;
import austen.cs340.qwitter.model.requests_and_responses.response.RegisterResponse;
import austen.cs340.qwitter.model.requests_and_responses.response.StoryResponse;
import austen.cs340.qwitter.model.requests_and_responses.response.UploadImageResponse;

public class ServerProxy implements ServerProxyInterface {

    private URL url;

    public ServerProxy(URL url) {
        this.url = url;
    }

    public RegisterResponse register(RegisterRequest request) {

        RegisterResponse response = null;

        try {
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            /* Convert to JSON */
            Gson gson = new Gson();
            String reqData = gson.toJson(request);

            /* Write it to a request body */
            OutputStream os = http.getOutputStream();
            os.write(reqData.getBytes());
            os.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Register successful!");
                System.out.println("Retrieving register response...");

                InputStream in = http.getInputStream();
                Reader reader = new InputStreamReader(in);

                response = gson.fromJson(reader, RegisterResponse.class);
                return response;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return null;
    }

    public LoginResponse login(LoginRequest request) {

        LoginResponse response = null;

        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            /* Convert to JSON */
            Gson gson = new Gson();
            String reqData = gson.toJson(request);

            /* Write to a request body */
            OutputStream os = http.getOutputStream();
            os.write(reqData.getBytes());
            os.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = http.getInputStream();
                Reader reader = new InputStreamReader(in);

                response = gson.fromJson(reader, LoginResponse.class);
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return response;
    }

    public FeedResponse getFeed(String authToken) {

        FeedResponse response = null;

        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);
            //http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            System.out.println("Fetching Feed...");
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                InputStream in = http.getInputStream();
                Reader reader = new InputStreamReader(in);

                response = gson.fromJson(reader, FeedResponse.class);
                System.out.println("Feed Fetched");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return response;
    }

    public GetUserByAliasResponse getUser(String authToken) {
        GetUserByAliasResponse response = null;

        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            System.out.println("Getting User by Alias...");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                InputStream in = http.getInputStream();
                Reader reader = new InputStreamReader(in);

                response = gson.fromJson(reader, GetUserByAliasResponse.class);
                System.out.println("Got User by Alias");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public StoryResponse getStory(String authToken) {
        StoryResponse response = null;

        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            System.out.println("Getting Story...");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                InputStream in = http.getInputStream();
                Reader reader = new InputStreamReader(in);
//                response = new StoryResponse();
//                response.setStatuses(gson.fromJson(reader, List.class));
                response = gson.fromJson(reader, StoryResponse.class);
                Model.getInstance().setLastkey(response.getLastkey());
                System.out.println("Got Story!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return response;
    }

    public void post(Status status) {

        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            /* Write the status to the http request */
            Gson gson = new Gson();
            String request = gson.toJson(status);
//            System.out.println(request);
            OutputStream os = http.getOutputStream();
            os.write(request.getBytes());
            os.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Status posted");
            }
            else {
                System.out.println("***Unable to complete Post***");
                System.out.println(http.getResponseMessage());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public FollowerResponse getSubscriptions() {

        FollowerResponse response = null;

        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(false);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            System.out.println("Getting subscriptions...");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                InputStream in = http.getInputStream();
                Reader reader = new InputStreamReader(in);
                response = gson.fromJson(reader, FollowerResponse.class);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void changeProfileImage(ProfileImageRequest request) {
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            /* Write the status to the http request */
            Gson gson = new Gson();
            String reqData = gson.toJson(request);
            System.out.println(request);
            OutputStream os = http.getOutputStream();
            os.write(reqData.getBytes());
            os.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Profile Image changed!");
            }
            else {
                System.out.println("***Unable to change profile image!***");
                System.out.println(http.getResponseMessage());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public UploadImageResponse uploadImage(UploadImageRequest request) {

        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            /* Convert to JSON */
            Gson gson = new Gson();
            String reqData = gson.toJson(request);
            System.out.println(reqData);

            /* Write to a request body */
            OutputStream os = http.getOutputStream();
            os.write(reqData.getBytes());
            os.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = http.getInputStream();
                Reader reader = new InputStreamReader(in);
                UploadImageResponse response = gson.fromJson(reader, UploadImageResponse.class);
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
