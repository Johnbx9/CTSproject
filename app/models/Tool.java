package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

// Tool is for submitting Tool

@Entity
public class Tool extends Model
{
    @Id
    public Long id;

    @Constraints.Required
    public String name;
    @Constraints.Required
    public String toolDescription;


    @ManyToOne
    public User toolOwner;


    @ManyToOne
    public ToolCategory tc;

    @OneToMany
    public List<Borrow> borrowBy;

    public int isborrow;
    @ManyToOne
    public User borrower;


    // A finder object for easier querying
    public static Finder<Long, Tool> find = new Finder<>(Tool.class);
}
