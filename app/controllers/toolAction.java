package controllers;

import models.Category;
import models.Tool;
import models.User;
import play.Play;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.io.*;
import java.util.List;

import static play.data.Form.form;

/**
 * Created by Alienware Grunt on 12/2/2015.
 */
public class toolAction extends Controller
{
    public Result index()
    {
        List<Category> options = Category.find.all();

        return ok(views.html.toolDescription.uploadTool.render(options) );
    }

    @Security.Authenticated(UserAuth.class)
    public Result upload() throws FileNotFoundException
    {
        User user = User.find.byId(Long.parseLong(session().get("user_id")) );
        // Getting from input named "image"
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("image");

        // If user uploaded an image, then check its size
//        if (picture != null)
//        {
//            // 5MB in bytes
//            if (picture.getFile().length() >= 5000000)
//            {
//                flash("error", "Image is too large. Max is 5MB");
//                return redirect(routes.toolAction.upload());
//            }
//        }

        Form<Tool> toolForm = form(Tool.class).bindFromRequest();
        Tool tool = toolForm.get();
        tool.toolOwner = user;

        // gets tool category from the form and save to tool model tc
//        tool.tc = Category.find.byId(Long.parseLong(tool.stc));
        tool.isBorrowable = true;

        if (picture != null)
        {
            File file = (File) picture.getFile();
            tool.imageFile = imageToBytes(file);
        }
        else
        {
            // If no image is uploaded then use "no image" image
            File noImage = Play.application().getFile("public/images/noImage.jpg");
            tool.imageFile = imageToBytes(noImage);
            tool.save();
            return redirect(routes.userProfile.index( Long.parseLong(session().get("user_id")) , user.username) );
        }

        tool.save();
        flash("success", "Tool was saved");

        return redirect(routes.userProfile.index( Long.parseLong(session().get("user_id")) , user.username) );
    }

    public Result getImage(Long id) throws FileNotFoundException
    {
        Tool tool = Tool.find.byId(id);

        if (tool.imageFile != null)
        {
            // The real magic to get byte [] to display as image in Play
            return ok(tool.imageFile).as("image");
        }
        else
        {
            flash("Error", "picture wasn't found or didn't work");
            return redirect(routes.Application.index() );
        }
    }

    private byte [] imageToBytes(File file) throws FileNotFoundException
    {
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte [] buff = new byte[1024];

        try
        {
            for (int readNum; (readNum = fileInputStream.read(buff)) != -1;)
            {
                byteArrayOutputStream.write(buff, 0, readNum);
            }
        }catch (IOException e)
        {
            System.out.println("Error: " + e);
        }

        return byteArrayOutputStream.toByteArray();
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

        if (!borrowTool.isBorrowable)
        {
            //can't borrow the tool
            flash("error", "Can't be borrowed at the moment");
            return redirect(routes.toolAction.show(borrowTool.id));
        }
        else
        {
            // create a new borrower instance and save the username to the borrower
            borrowTool.borrower = User.find.byId(Long.parseLong(session().get("user_id") ));

            // change isBorrow to false to know that it isn't available
            borrowTool.isBorrowable = false;

            // save
            borrowTool.save();
            flash("success", "The tool is on your way");
            return redirect(routes.userProfile.index(Long.parseLong(session().get("user_id")),
                                                User.find.byId(Long.parseLong(session().get("user_id")) ).username) );
        }
    }

    public Result giveback(Long id)
    {
        Tool borrowTool = Tool.find.byId(id);

        borrowTool.isBorrowable = true;
        borrowTool.borrower = null;

        borrowTool.update();

        return redirect(routes.toolAction.show(borrowTool.id));
    }

}