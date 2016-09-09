package controllers;

import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public Result index()
    {
        if (!session().containsKey("user_id") )
            return ok(index.render("Your new application is ready."));
        else
            return redirect(routes.userProfile.index(Long.parseLong(session().get("user_id")), session().get("user_id") ) );
    }



}
