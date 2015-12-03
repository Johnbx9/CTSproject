package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

// Tool is for submitting Tool

@Entity
public class Tool extends Model
{
    @Id
    public Long id;

    public String summary;
    public String toolOwner;
    public String toolCategory;

    @Lob
    public byte [] image;

       // A finder object for easier querying
    public static Finder<Long, Tool> find = new Finder<Long, Tool>(Tool.class);
}
