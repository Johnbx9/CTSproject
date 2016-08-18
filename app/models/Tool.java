package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

// Tool is for submitting Tool
@Entity
public class Tool extends Model
{
    @Id
    public Long id;
    public String name;
    public String toolDescription;
    // String tool category id
    public String stc;
    @ManyToOne
    public User toolOwner;
    @ManyToOne
    public Category tc;
    public boolean isBorrowable;
    @ManyToOne
    public User borrower;
    @Lob
    public byte [] imageFile;

    // A finder object for easier querying
    public static Model.Finder<Long, Tool> find = new Finder<Long, Tool>(Tool.class);
}
