package controllers;

import play.*;
import play.mvc.*;
import models.User;
import play.data.DynamicForm;
import play.data.Form;

import views.html.*;
import static play.data.Form.form;

public class Application extends Controller {

    public Result index()
    {
        return ok(index.render("Your new application is ready."));
    }

    public Result login()
    {
        DynamicForm userForm = Form.form().bindFromRequest();
        String username = userForm.data().get("username");
        String password = userForm.data().get("password");

        User user = User.find.where().eq("username", username).findUnique();

        if(user != null && user.authenticate(password))
        {
            session("user_id", user.id.toString());
            flash("success", "Welcome back " + user.username);
            return redirect(routes.Application.index() );
        }
        else
        {
            flash("error", "Invalid login. Check your username and password.");
        }

        return redirect(routes.Application.index());
    }

    public Result signup()
    {
        DynamicForm userform = Form.form().bindFromRequest();
        String username = userform.data().get("username");
        String  password = userform.data().get("password");

        User user = User.createNewUser(username, password);

        if(user == null)
        {
            flash("error", "Invalid User Name");
            return redirect(routes.Application.index() );
        }

        user.save();

        flash("success", "Welcome! " + user.username);
        session("user_id: ", user.id.toString() );
        return redirect(routes.Application.index() );
    }

}
