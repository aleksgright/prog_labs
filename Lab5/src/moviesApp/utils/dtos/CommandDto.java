package moviesApp.utils.dtos;

import moviesApp.moviesAppServer.entities.Movie;

import java.io.Serializable;

public class CommandDto implements Serializable {
    private String command;
    private String argument = null;
    private Movie movie = null;

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getArgument() {
        return argument;
    }

    public String getCommand() {
        return command;
    }
}
