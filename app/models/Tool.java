package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

// Tool is for submitting Tool

@Entity
public class Tool extends Model
{
    @Id
    public Long id;

    public String name;
    public String toolDescription;

    @OneToOne
    public String toolOwner;
    @OneToMany
    public String toolCategory;

    @Lob
    public byte [] image;

    // A finder object for easier querying
    public static Finder<Long, Tool> find = new Finder<Long, Tool>(Tool.class);
}
