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
        List<Category> options = Category.find.all();
        return ok(views.html.toolDescription.uploadTool.render(options) );
    }

    @Security.Authenticated(UserAuth.class)
    public Result upload()
    {
        User user = User.find.byId(Long.parseLong(session().get("user_id")) );


        Form<Tool> toolForm = form(Tool.class).bindFromRequest();
        Tool tool = toolForm.get();
        tool.toolOwner = user;

        // gets tool category from the form and save to tool model tc
        String tcid = toolForm.data().get("id");
        Category option = Category.find.byId(Long.parseLong(tcid));
        tool.tc = option;

        tool.save();

        // tool owner is the Username of the person who is uploading
        flash("success", "Tool was saved");

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
