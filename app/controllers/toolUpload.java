package controllers;

import models.*;
import models.User;
import play.data.Form;
import play.mvc.*;

import java.util.List;

import static play.data.Form.form;

/**
 * Created by Alienware Grunt on 12/2/2015.
 */
public class toolUpload extends Controller
{
    public Result index()
    {
        return ok(views.html.toolDescription.uploadTool.render("ready") );
    }

    @Security.Authenticated(UserAuth.class)
    public Result upload()
    {
        Form<Tool> toolForm = form(Tool.class).bindFromRequest();
        Tool tool = toolForm.get();
        tool.save();
        flash("success", "saved Tool for " + tool.toolOwner + " and your summary: " + tool.toolDescription);
        return redirect(routes.userProfile.index() );

    }
}
