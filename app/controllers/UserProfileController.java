package controllers;

import models.Tool;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;


/**
 * Created by Alienware Grunt on 12/2/2015.
 */
public class UserProfileController extends Controller
{
    @Security.Authenticated(UserAuth.class)
    public Result index(String username)
    {
        User user = User.retrieve(username);

        if (user != null)
        {
            List<Tool> userToolList = Tool.userList(user.id);
            List<Tool> userBorrowList = Tool.borrowList(user.id);
            return ok(views.html.user.profile.render(userToolList, userBorrowList, user));
        }

        return badRequest(views.html.index.render("ok"));
    }
}