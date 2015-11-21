package models;


import com.avaje.ebean.Model;
import play.data.validation.Constraints;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.Constraint;

@Table(name = "users")
@Entity
public class User extends Model
{
    @Id
    public Integer id;

    @Constraints.Required
    @Column(unique = true)
    public String username;

    public String password_hash;

    // finder object for easier querying
    public static Finder<Long, User> find =  new Finder<Long, User>(User.class);

    public boolean authenticate(String password)
    {
        return BCrypt.checkpw(password, this.password_hash);
    }

    public static User createNewUser(String username, String password)
    {
        if (password == null || username == null || password.length() < 8)
        {
            return null;
        }

        // creating new password hash
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.id = 1;
        user.username = username;
        user.password_hash = passwordHash;
        return user;
    }
}
