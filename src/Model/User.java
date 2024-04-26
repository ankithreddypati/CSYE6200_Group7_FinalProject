package Model;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private int phone;

    private String bankTransactionsPath;

    private String assetsLiabilitiesPath;

    private String investmentsPath;


    // Constructor
    public User(int id, String username, String password, String email, int phone, String bankTransactionsPath,
                String assetsLiabilitiesPath, String investmentsPath) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.bankTransactionsPath= bankTransactionsPath;
        this.assetsLiabilitiesPath= assetsLiabilitiesPath;
        this.investmentsPath = investmentsPath;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String bankTransactionsPath() {
        return bankTransactionsPath;
    }

    public User setBankTransactionsPath(String bankTransactionsPath) {
        this.bankTransactionsPath=bankTransactionsPath;
        return this;
    }

    public String getAssetsLiabilitiesPath() {
        return assetsLiabilitiesPath;
    }

    public User setAssetsLiabilitiesPath(String assetsLiabilitiesPath) {
        this.assetsLiabilitiesPath=assetsLiabilitiesPath;
        return this;
    }

    public String investmentsPath() {
        return investmentsPath;
    }

    public User setInvestmentsPath(String investmentsPath) {
        this.investmentsPath=investmentsPath;
        return this;
    }


    public String getBankTransactionsPath() {
        return bankTransactionsPath;
    }

    public String getInvestmentsPath(){
        return investmentsPath;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
