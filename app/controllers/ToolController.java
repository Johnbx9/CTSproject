package controllers;

import models.Tool;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.io.*;
import java.util.List;

/**
 * Created by Alienware Grunt on 12/2/2015.
 */
public class ToolController extends Controller
{
    private FormFactory formFactory;

    @Inject
    public ToolController(FormFactory formFactory)
    {
        this.formFactory = formFactory;
    }

    public Result upload()
    {
        Form<Tool> toolForm = formFactory.form(Tool.class);
        return ok(views.html.tool.uploadTool.render(toolForm));
    }

    @Security.Authenticated(UserAuth.class)
    public Result save() throws FileNotFoundException
    {
        Form<Tool> toolForm = formFactory.form(Tool.class).bindFromRequest();

        if (toolForm.hasErrors())
        {
            System.out.println("Errors: " + toolForm.errors());
            return redirect(routes.ToolController.upload());
        }

        Tool tool = toolForm.get();

        if (tool != null)
        {
            tool.save();
            tool.user = User.find.byId(Long.parseLong(session().get("user_id")));
            tool.update();
            flash("success", "Tool was saved");
            return redirect(routes.UserProfileController.index(tool.user.username));
        }

        return badRequest(views.html.tool.uploadTool.render(toolForm));

//        User user = User.find.byId(Long.parseLong(session().get("user_id")) );
//        // Getting from input named "image"
//        Http.MultipartFormData body = request().body().asMultipartFormData();
//        Http.MultipartFormData.FilePart picture = body.getFile("image");
//
//        Form<Tool> toolForm = form(Tool.class).bindFromRequest();
//        Tool tool = toolForm.get();
//        tool.toolOwner = user;
//
//        // gets tool category from the form and save to tool model tc
////        tool.tc = Category.find.byId(Long.parseLong(tool.stc));
//        tool.isBorrowable = true;
//
//        if (picture != null)
//        {
//            File file = (File) picture.getFile();
//            tool.imageFile = imageToBytes(file);
//        }
//        else
//        {
//            // If no image is uploaded then use "no image" image
//            File noImage = Play.application().getFile("public/images/noImage.jpg");
//            tool.imageFile = imageToBytes(noImage);
//            tool.save();
//            return redirect(routes.UserProfileController.about( Long.parseLong(session().get("user_id")) , user.username) );
//        }
//
//        tool.save();
//        flash("success", "Tool was saved");
//
//        return redirect(routes.UserProfileController.about( Long.parseLong(session().get("user_id")) , user.username) );
    }

    public Result getImage(Long id) throws FileNotFoundException
    {
        Tool tool = Tool.find.byId(id);

        if (tool.imageFile != null)
        {
            // The real magic to get byte [] to display as image in Play
            return ok(tool.imageFile).as("image");
        } else
        {
            flash("Error", "picture wasn't found or didn't work");
            return redirect(routes.Application.index());
        }
    }

    private byte[] imageToBytes(File file) throws FileNotFoundException
    {
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];

        try
        {
            for (int readNum; (readNum = fileInputStream.read(buff)) != -1; )
            {
                byteArrayOutputStream.write(buff, 0, readNum);
            }
        } catch (IOException e)
        {
            System.out.println("Error: " + e);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public Result show(Long id)
    {
        Tool tool = Tool.find.byId(id);

        if (tool == null)
        {
            return notFound();
        }

        return ok(views.html.tool.show.render(tool));
    }

    public Result showAll()
    {
        List<Tool> allTool = Tool.find.all();

        return ok(views.html.tool.showAllTool.render(allTool));
    }

    public Result borrow(Long id)
    {
        Tool borrowTool = Tool.find.byId(id);

        if (borrowTool != null)
        {
            if (borrowTool.borrower != null)
            {
                flash("error", "Can't be borrowed at the moment");
                return redirect(routes.ToolController.show(id));
            }

            User user = User.find.byId(Long.parseLong(session().get("user_id")));
            borrowTool.borrower = user;
            borrowTool.update();

            flash("success", "The tool is on your way");
            return redirect(routes.UserProfileController.index(user.username));
        }

        return redirect(routes.ToolController.show(id));
    }

    public Result giveback(Long id)
    {
        Tool borrowTool = Tool.find.byId(id);

        if (borrowTool != null)
        {
            borrowTool.borrower = null;
            borrowTool.update();

            User user = User.retrieve(Long.parseLong(session().get("user_id")));
            flash("success", "Thanks for borrowing!");
            return redirect(routes.UserProfileController.index(user.username));
        }

        flash("error", "Something unexpected happen");
        return redirect(routes.ToolController.show(id));
    }
}
