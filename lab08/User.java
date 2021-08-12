import java.util.Arrays;
import java.util.Objects;
import java.util.Comparator;

public class User implements Comparable<User>{
    //private Comparator<User> compUser;
    private static int nextId = 1;

    private int id;
    private int age;
    private String name;
    private String email;

    public User(String name, String email) {
        this(nextId, name, email);
        nextId += 1;
    }

    /** Force assign an id to a created user **/
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        setAge();
    }

    /** For this assignment, age is just an automatically assigned field. */
    void setAge() {
        age = (id % 13) + 20;
    }

    int getAge() {
        return age;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User other = (User) o;
        if (id != other.id) {
            return false;
        } else if (!Objects.equals(name, other.name)) {
            return false;
        } else {
            return Objects.equals(email, other.email);
        }
    }

    @Override
    public int compareTo(User user){
        if (this.id > user.id) return 1;
        else if (this.id < user.id) return -1;
        else {
            String[] names = {this.getName(), user.getName()};
            Arrays.sort(names);
            if (this.name.equals(names[0])) return -1;
            else return 1;
        }
    }


    public static void main(String[] args) {
        User[] users = {
            new User(2, "Matt", ""),
            new User(4, "Zoe", ""),
            new User(5, "Alex", ""),
            new User(1, "Shreya", ""),
            new User(1, "Connor", "")
        };

        Arrays.sort(users);
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
}
