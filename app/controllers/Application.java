package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller
{

    public Result index()
    {
        if (session().containsKey("user_id"))
        {
            User user = User.retrieve(Long.parseLong(session().get("user_id")));
            if (user != null)
                return redirect(routes.UserProfileController.index(user.username));
            session().clear();
        }

        return ok(index.render("Your new application is ready."));
    }

    public Result aboutPage()
    {
        return ok(views.html.common.about.render("ok"));
    }
}
