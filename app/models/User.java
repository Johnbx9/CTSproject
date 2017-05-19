package models;

import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Model
{
    // Finder object for easier querying
    public static Model.Finder<Long, User> find = new Finder<>(User.class);
    @Id
    public Long id;
    @Constraints.Required
    @Column(unique = true)
    public String username;
    @Constraints.Email
    @Constraints.Required
    public String email;
    @Constraints.Required
    public String password;
    public String address;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Tool> toolList = new ArrayList<>();
    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    public List<Tool> borrowList = new ArrayList<>();

    public static String hashPassword(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static User retrieve(Long id)
    {
        return find.byId(id);
    }

    public static User retrieve(String username)
    {
        return find.where().eq("username", username).findUnique();
    }

    public static boolean exists(String username)
    {
        return find.where().eq("username", username).findRowCount() == 1;
    }

    public boolean authenticate(String password)
    {
        return BCrypt.checkpw(password, this.password);
    }
}
