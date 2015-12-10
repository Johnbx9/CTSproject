package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.List;

/**
 * Created by Alienware Grunt on 12/9/2015.
 */
@Entity
public class ToolCategory extends Model
{
    @Id
    public Long id;

    @Constraints.Required
    public String cName;

    public List<Tool> toolC;

    public static Finder<Long, ToolCategory> find = new Finder<Long, ToolCategory>(ToolCategory.class);

}
