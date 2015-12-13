package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


/**
 * Created by Alienware Grunt on 12/11/2015.
 */
@Entity
public class Category extends Model
{
    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @OneToMany(mappedBy = "tc")
    public List<Tool> toolList;


    public static Model.Finder<Long,Category> find = new Finder<Long, Category>(Category.class);

}

