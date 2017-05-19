package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "tool")
public class Tool extends Model
{
    // A finder object for easier querying
    public static Model.Finder<Long, Tool> find = new Finder<>(Tool.class);
    @Id
    public Long id;
    @Constraints.Required(message = "required.message")
    public String name;
    public String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id")
    public User borrower;
    @Lob
    public byte[] imageFile;

    /**
     * The list of tools owned by a user.
     *
     * @param id the id of the user
     * @return a list of tools
     */
    public static List<Tool> userList(Long id)
    {
        return find.where().eq("user_id", id).orderBy("id").findList();
    }

    /**
     * The list of tools that a user is borrowing.
     *
     * @param id the user's id
     * @return a list of tools
     */
    public static List<Tool> borrowList(Long id)
    {
        return find.where().eq("borrower_id", id).orderBy("id").findList();
    }
}
