package austen.cs340.qwitter.model.requests_and_responses.request;

public class RegisterRequest implements Request {

    private String firstName;
    private String lastName;
    private String alias;
    private String password;
    private String profileImage;

    public RegisterRequest(String firstName, String lastName, String alias,
                           String password, String profilePic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.password = password;
        this.profileImage = profilePic;

    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getProfileImage() {
        return profileImage;
    }
}
