package controllers;

/**
 * Created by Yusef on 11/28/2015.
 */

import models.Tool;
import play.data.Form;
import play.mvc.*;

import java.util.List;

import static play.data.Form.form;

public class toolDescription extends Controller
{
    public Result index()
    {
        List<Tool> movies = Tool.find.all();
        return ok(views.html.toolDescription.index.render(movies));
    }


    public Result create() {
        Form<Tool> movieForm = form(Tool.class).bindFromRequest();
        String genre_id = movieForm.data().get("genre_id");

       // Genre genre = Genre.find.byId(Long.parseLong(genre_id));
       // if (genre == null) {
       //     flash("error", "Invalid Tool Type: " + genre_id + " please try again.");
       //     return redirect(routes.Movies.index());
       // }

        Tool movie = movieForm.get();
      //  movie.genre = genre;
        movie.save();
        //flash("success", "Saved new Tool: " + movie.title);
        return redirect(routes.toolDescription.index());
    }
}