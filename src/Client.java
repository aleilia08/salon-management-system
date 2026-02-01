import java.util.List;

public class Client implements User{
    private String username;
    private String password;
    public Client(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String setUsername() {
        return null;
    }

    @Override
    public String setPassword() {
        return null;
    }
}
