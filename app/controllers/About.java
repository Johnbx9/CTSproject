package controllers;

import play.mvc.*;

/**
 * Created by Alienware Grunt on 11/25/2015.
 */
public class About extends Controller
{
    public Result index()
    {
        return ok(views.html.about.index.render("ready") );
    }
}
