public class Admin implements User{

    private String username;
    private String password;
    public Admin(String username, String password) {
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
