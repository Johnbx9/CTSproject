package controllers;

import models.Tool;
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
        Form<Tool> toolform = form(Tool.class).bindFromRequest();

        Tool tool = toolform.get();
        tool.save();
        flash("success", "saved Tool for " + tool.toolOwner + " and your summary: " + tool.summary);
        return redirect(routes.userProfile.index() );

    }
}
