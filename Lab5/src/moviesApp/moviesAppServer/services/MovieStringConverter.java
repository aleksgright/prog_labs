package moviesApp.moviesAppServer.services;

import moviesApp.moviesAppServer.entities.Movie;
import moviesApp.moviesAppServer.entities.Person;
import moviesApp.utils.enums.Color;
import moviesApp.utils.enums.Country;
import moviesApp.utils.enums.MovieGenre;
import moviesApp.utils.enums.MpaaRating;
import moviesApp.utils.Coordinates;
import moviesApp.utils.exceptions.MoviesAppException;

import java.util.Date;
import java.util.Hashtable;

public class MovieStringConverter {
    public void addMovieToTable(Movie movie, Hashtable<Integer, Movie> movieHashtable) {
        if (movieHashtable.containsKey(movie.getId())) {
            throw new MoviesAppException("Two movies with same id");
        }
        movieHashtable.put(movie.getId(), movie);
    }

    public String movieToJsonFormat(Movie movie) {
        // TODO use SB
        String result = "";
        result = result + "\t\t\"id\": \"" + movie.getId() + "\",\n";
        result = result + "\t\t\"name\": \"" + movie.getName() + "\",\n";
        result = result + "\t\t\"coordinates\": \"" + movie.getCoordinates() + "\",\n";
        result = result + "\t\t\"creationDate\": \"" + movie.getCreationDate().getDate() + "/"
                + (movie.getCreationDate().getMonth() + 1) + "/"
                + (movie.getCreationDate().getYear() + 1900) + "\",\n";
        result = result + "\t\t\"oscarsCount\": \"" + movie.getOscarsCount() + "\",\n";
        result = result + "\t\t\"genre\": \"" + movie.getGenre() + "\",\n";
        result = result + "\t\t\"mpaaRating\": \"" + movie.getMpaaRating() + "\",\n";
        result = result + "\t\t\"director\": \"" + movie.getDirector() + "\"\n";
        return result;
    }

    public Movie convertDataArrayToMovie(String[] data) {
        try {
            final Movie movie = new Movie();
            movie.setId(Integer.parseInt(data[0]));
            movie.setName(data[1]);
            movie.setCoordinates(stringToCoordinates(data[2]));
            movie.setCreationDate(stringToDate(data[3]));
            movie.setOscarsCount(Integer.parseInt(data[4]));
            movie.setGenre(MovieGenre.valueOf(data[5]));
            movie.setMpaaRating(MpaaRating.valueOf(data[6]));
            movie.setDirector(stringToPerson(data[7]));
            return movie;
        } catch (Throwable e) {
            System.out.println("Invalid data or data format");
            System.exit(0);
        }
        return null;
    }

    private Person stringToPerson(String line) {
        String[] data = line.split(" ");
        return new Person(
                data[0],
                data[1],
                Color.valueOf(data[2]),
                Color.valueOf(data[3]),
                Country.valueOf(data[4]));
    }

    private Coordinates stringToCoordinates(String line) {
        return new Coordinates(Double.parseDouble(line.split(" ")[0]),
                Double.parseDouble(line.split(" ")[1]));
    }

    private Date stringToDate(String line) {
        return new Date(Integer.parseInt(line.split("/")[0]) - 1900,
                Integer.parseInt(line.split("/")[1]) - 1,
                Integer.parseInt(line.split("/")[2]));
    }
}
