package controllers;

import play.mvc.*;
import views.html.index;

/**
 * Created by Alienware Grunt on 11/30/2015.
 */
public class User extends Controller
{
    public Result index()
    {
        return ok(views.html.signup.index.render("ready") );
    }
}
