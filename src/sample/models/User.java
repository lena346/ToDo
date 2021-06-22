package sample.models;

public class User {
    private Long id;

    private String FirstName;

    private String SecondName;

    private String email;

    private String password;

    private Long score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", FirstName='" + FirstName + '\'' +
                ", SecondName='" + SecondName + '\'' +
                ", password='" + password + '\'' +
                ", score=" + score +
                '}';
    }
}
