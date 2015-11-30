package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Tool extends Model
{
    @Id
    public Long id;

    @Constraints.Required

    public String summary;

       // A finder object for easier querying
    public static Finder<Long, Tool> find = new Finder<Long, Tool>(Tool.class);
}
