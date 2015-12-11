package controllers;


import play.data.DynamicForm;
import play.mvc.*;

import static play.data.Form.form;

/**
 * Created by Alienware Grunt on 11/30/2015.
 */
public class Users extends Controller
{
    public Result index()
    {
        return ok(views.html.signup.index.render("ok") );
    }

    public Result login()
    {
        DynamicForm userForm = form().bindFromRequest();
        String username = userForm.data().get("username");
        String password = userForm.data().get("password");

        models.User user = models.User.find.where().eq("username", username).findUnique();


        if(user != null && user.authenticate(password))
        {
            session("user_id", user.id.toString());
            flash("success", "Welcome back " + user.username);
            return redirect(routes.userProfile.index(user.id, user.username));
        }
        else
        {
            flash("error", "Invalid login. Check your username and password.");
        }

        return redirect(routes.Application.index());
    }

    public Result signup()
    {
        DynamicForm userform = form().bindFromRequest();
        String username = userform.data().get("username");
        String password = userform.data().get("password");
        String email = userform.data().get("email");

        models.User user = models.User.createNewUser(username, password, email);


        if(user == null)
        {
            flash("error", "Invalid User Name");
            return redirect(routes.Application.index() );
        }

        user.save();

        flash("success", "Welcome to our community! " + user.username);
        session("user_id", user.id.toString() );
        return redirect(routes.userProfile.index(user.id, user.username) );
    }

    public Result logout()
    {
        for (int i = 0; i <= session().size(); i++)
        {
            session().remove("user_id");

        }

        return redirect(routes.Application.index() );
    }

}
