package controllers;


import models.Category;
import models.Tool;
import models.User;
import play.mvc.*;
import views.html.index;

import java.util.List;

/**
 * Created by Alienware Grunt on 12/2/2015.
 */
public class userProfile extends Controller
{
    public Result index(Long id, String username)
    {
        User user = User.find.byId(id);


        if (session().containsKey("user_id") && session().get("user_id").equals(user.id.toString() ) )
            return ok(views.html.user.profile.render(user.toolList, user) );
        else
            return ok(index.render("ready") );
    }
}