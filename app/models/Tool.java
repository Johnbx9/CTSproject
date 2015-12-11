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


    public String name;
    @Constraints.Required
    public String toolDescription;


    @ManyToOne
    public User toolOwner;


    @ManyToOne
    public Category tc;

    @OneToMany
    public List<Borrow> borrowBy;

    public int isborrow;
    @ManyToOne
    public User borrower;


    // A finder object for easier querying
    public static Model.Finder<Long, Tool> find = new Finder<Long, Tool>(Tool.class);
}
