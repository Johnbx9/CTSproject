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
        User user = User.find.byId(Long.parseLong(session().get("user_id")) );

        Form<Tool> toolForm = form(Tool.class).bindFromRequest();
        Tool tool = toolForm.get();
        tool.toolOwner = user;

        tool.save();

        // tool owner is the Username of the person who is uploading
        flash("success", "saved Tool for " + tool.toolOwner.username + " and your summary: " + tool.toolDescription);

        return redirect(routes.userProfile.index( Long.parseLong(session().get("user_id")) , user.username) );
    }

    public Result show(Long id)
    {
        Tool tool = Tool.find.byId(id);


        if(tool == null)
        {
            return notFound("not found");
        }
        else
        {
            return ok(views.html.toolDescription.show.render(tool) );
        }
    }

    public Result showAll()
    {
        List<Tool> allTool = Tool.find.all();

        return ok(views.html.toolDescription.showAllTool.render(allTool) );
    }
}
