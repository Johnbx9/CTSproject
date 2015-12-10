package controllers;


import models.Tool;
import models.User;
import play.mvc.*;

import java.util.List;

/**
 * Created by Alienware Grunt on 12/2/2015.
 */
public class userProfile extends Controller
{
    public Result index()
    {
        List<Tool> tool = Tool.find.all();

        return ok(views.html.user.profile.render(tool));
    }
}