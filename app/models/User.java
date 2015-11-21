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
    public Long id = 0L;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

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
        Long oldID = user.getId();
        user.id +=1L;
        if(user.id == oldID)
        {
            user.id += 1L;
        }
        user.username = username;
        user.password_hash = passwordHash;
        return user;
    }
}
