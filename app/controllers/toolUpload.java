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
        Category option = Category.find.byId(Long.parseLong(tool.stc));
        tool.tc = option;
        tool.isBorrow = true;

        tool.save();

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

    public Result borrow(Long id)
    {
        Tool borrowTool = Tool.find.byId(id);

        if (borrowTool.isBorrow == false)
        {
            //can't borrow the tool
            flash("error", "Can't be borrowed at the moment");
            return redirect(routes.toolUpload.show(borrowTool.id));
        }
        else
        {
            // create a new borrower instance and save the username to the borrower
            Borrow borrower = new Borrow();
            borrower.name = User.find.byId(Long.parseLong(session().get("user_id") )).username;

            // change isBorrow to false
            borrowTool.isBorrow = false;
            //saving the borrower in the tool model
            borrowTool.borrower = borrower;

            // save
            borrower.save();
            borrowTool.save();
            flash("success", "The tool is on your way");
            return redirect(routes.userProfile.index(Long.parseLong(session().get("user_id")),
                                                User.find.byId(Long.parseLong(session().get("user_id")) ).username) );
        }
    }

    public Result giveback(Long id)
    {
        Tool borrowTool = Tool.find.byId(id);

        borrowTool.isBorrow = true;
        Borrow borrower = Borrow.find.byId(borrowTool.borrower.id);
        borrowTool.borrower = null;
        borrowTool.update();

        borrower.delete();

        return redirect(routes.toolUpload.show(borrowTool.id));
    }

}
