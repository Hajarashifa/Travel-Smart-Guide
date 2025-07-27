package models;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String preferences;

    public User(String name, String email, String password, String preferences) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.preferences = preferences;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPreferences() { return preferences; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
}
