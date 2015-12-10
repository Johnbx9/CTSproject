package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Alienware Grunt on 12/9/2015.
 */

@Entity
public class Borrow extends Model
{
    @Id
    public int id;

    @Constraints.Required
    @ManyToOne
    public User users;

    @Constraints.Required
    @ManyToOne
    public Tool tools;

    public static Finder<Integer, Borrow> find = new Finder<Integer, Borrow>(Borrow.class);

}
