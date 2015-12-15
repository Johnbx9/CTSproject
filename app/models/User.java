package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
public class User extends Model
{
    @Id
    public Long id;

    @Constraints.Required
    @Column(unique = true)
    public String username;

    public String email;

    public String password_hash;

    public String address;

    @OneToMany(mappedBy = "toolOwner")
    public List<Tool> toolList = new ArrayList<>();
    @OneToMany(mappedBy = "borrower")
    public List<Tool> borrowList = new ArrayList<>();


    // finder object for easier querying
    public static Model.Finder<Long,User> find = new Finder<Long, User>(User.class);


    public boolean authenticate(String password)
    {
        return BCrypt.checkpw(password, this.password_hash);
    }

    public static User createNewUser(String username, String password, String email)
    {
        if (password == null || username == null || password.length() < 8)
        {
            return null;
        }

        // creating new password hash
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();

        user.username = username;
        user.password_hash = passwordHash;
        user.email = email;
        return user;
    }
}
