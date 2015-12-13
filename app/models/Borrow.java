package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alienware Grunt on 12/9/2015.
 */

@Entity
public class Borrow extends Model
{
    @Id
    public Long id;

    // save the name (maybe id) of the person who is borrowing a tool
    public String name;



    public static Model.Finder<Long, Borrow> find = new Finder<Long, Borrow>(Borrow.class);


}
