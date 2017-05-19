package controllers;

import models.Tool;
import models.User;
import models.UserLoginForm;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John V on 11/30/2015.
 */
public class UserController extends Controller
{
    private Logger.ALogger log = Logger.of(UserController.class);
    private FormFactory formFactory;

    @Inject
    public UserController(FormFactory formFactory)
    {
        this.formFactory = formFactory;
    }

    public Result signUp()
    {
        Form<User> userForm = formFactory.form(User.class);
        return ok(views.html.user.signUp.render(userForm));
    }

    public Result save()
    {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if (userForm.hasErrors())
        {
            log.debug("Form has errors: " + userForm.errors());
            System.out.println("Form has errors: " + userForm.errors());
            return badRequest(views.html.user.signUp.render(userForm));
        }

        User user = userForm.get();

        if (user != null)
        {
            user.save();
            user.password = User.hashPassword(user.password);
            user.update();

//            List<Tool> userToolList = Tool.findUserTool(user.id);
            List<Tool> userToolList = new ArrayList<>();
//            List<Tool> userBorrowList = Tool.findBorrowTool(user.id);
            List<Tool> userBorrowList = new ArrayList<>();

            session("user_id", user.id.toString());
            return ok(views.html.user.profile.render(userToolList, userBorrowList, user));
        }

        return badRequest(views.html.user.signUp.render(userForm));
    }

    public Result login()
    {
        Form<UserLoginForm> login = formFactory.form(UserLoginForm.class).bindFromRequest();

        if (login.hasErrors())
            return badRequest(views.html.index.render("ok"));

        UserLoginForm loginForm = login.get();

        if (User.exists(loginForm.username))
        {
            User user = User.retrieve(loginForm.username);

            if (user.authenticate(loginForm.password))
            {
                session("user_id", user.id.toString());
                flash("success", "Welcome back " + user.username);

                return redirect(routes.UserProfileController.index(user.username));
            }

            flash("error", "Invalid login. Check your username or password");
            return badRequest(views.html.index.render("ok"));
        }

        flash("error", "Invalid login. Check your username or password");
        return badRequest(views.html.index.render("ok"));
    }

    public Result logout()
    {
        session().clear();
        return redirect(routes.Application.index());
    }

}
