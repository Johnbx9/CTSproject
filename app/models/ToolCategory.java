package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "tc")
    public List<Tool> toolC = new ArrayList<>();

    public static Model.Finder<Long, ToolCategory> find = new Finder<Long, ToolCategory>(ToolCategory.class);

}
