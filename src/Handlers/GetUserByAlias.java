package Handlers;

import models.request.GetUserByAliasRequest;
import models.response.GetUserByAliasResponse;
import services.UserService;

public class GetUserByAlias {

    public GetUserByAlias() {}

    public GetUserByAliasResponse handleRequest(GetUserByAliasRequest request) {

        UserService service = new UserService();
        return service.getUserByAlias(request);
    }
}
