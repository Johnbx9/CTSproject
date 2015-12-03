package controllers;

import models.*;
import play.data.Form;
import play.mvc.*;

import static play.data.Form.form;

/**
 * Created by Alienware Grunt on 12/2/2015.
 */
public class toolUpload extends Controller
{
    public Result index()
    {
        return ok(views.html.toolDescription.uploadTool.render("all set") );
    }

    public Result upload()
    {
        Form<Tool> toolForm = form(Tool.class).bindFromRequest();


        Tool tool = toolForm.get();
        tool.save();
        flash("success", "saved Tool for " + tool.toolOwner + " and your summary: " + tool.toolDescription);
        return redirect(routes.userProfile.index() );

    }
}
