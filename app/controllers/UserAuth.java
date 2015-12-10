package controllers;

import play.mvc.*;
import models.User;

/**
 * Created by Alienware Grunt on 12/8/2015.
 */
public class UserAuth extends Security.Authenticator
{
    // When return is null, Authentication failed
    @Override
    public String getUsername(final Http.Context ctx)
    {
        String userIdStr = ctx.session().get("user_id");

        if(userIdStr == null) return null;

        User user = User.find.byId(Long.parseLong(userIdStr));
        return (user != null ? user.id.toString() : null);
    }

    @Override
    public Result onUnauthorized(final Http.Context ctx)
    {
        ctx.flash().put("error", "Nice try, but you need to log in first!");
        return redirect(routes.Application.index());
    }

}
