// Admin.java
public class Admin {
    private String idAdmin;
    private String password;

    // Constructor
    public Admin(String idAdmin, String password) {
        this.idAdmin = idAdmin;
        this.password = password;
    }

    // Getters
    public String getIdAdmin() {
        return idAdmin;
    }

    public String getPassword() {
        return password;
    }
}