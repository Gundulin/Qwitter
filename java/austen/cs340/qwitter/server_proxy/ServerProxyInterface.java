package austen.cs340.qwitter.server_proxy;

import java.util.List;

import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.model.User;
import austen.cs340.qwitter.model.requests_and_responses.request.FollowerRequest;
import austen.cs340.qwitter.model.requests_and_responses.request.GetUserByAliasRequest;
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

public interface ServerProxyInterface {

    public RegisterResponse register(RegisterRequest request);

    public LoginResponse login(LoginRequest request);

    public FeedResponse getFeed(String authToken);

    public GetUserByAliasResponse getUser(String authToken);

    public StoryResponse getStory(String authToken);

    public void post(Status status);

    public FollowerResponse getSubscriptions();

    public void changeProfileImage(ProfileImageRequest request);

    public UploadImageResponse uploadImage(UploadImageRequest request);


}
