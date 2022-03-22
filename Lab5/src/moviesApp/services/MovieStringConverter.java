package moviesApp.services;

import moviesApp.entities.Movie;
import moviesApp.entities.Person;
import moviesApp.enums.Color;
import moviesApp.enums.Country;
import moviesApp.enums.MovieGenre;
import moviesApp.enums.MpaaRating;
import moviesApp.utils.Coordinates;
import moviesApp.utils.MoviesAppException;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;

public class MovieStringConverter {
    public void addMovieToTable(Movie movie, Hashtable<Integer, Movie> movieHashtable) {
        //add validation here
        if (movieHashtable.containsKey(movie.getId())) {
            throw new MoviesAppException("Two movies with same id");
        }
        movieHashtable.put(movie.getId(), movie);
    }

    public String movieToJsonFormat(Movie movie) {
        String result = "";
        result = result + "\t\t\"id\": \"" + movie.getId() + "\",\n";
        result = result + "\t\t\"name\": \"" + movie.getName() + "\",\n";
        result = result + "\t\t\"coordinates\": \"" + movie.getCoordinates() + "\",\n";
        result = result + "\t\t\"creationDate\": \"" + movie.getCreationDate().getDate() + "/"
                                                     + (movie.getCreationDate().getMonth()+1) + "/"
                                                      + (movie.getCreationDate().getYear()+1900) + "\",\n";
        result = result + "\t\t\"oscarsCount\": \"" + movie.getOscarsCount() + "\",\n";
        result = result + "\t\t\"genre\": \"" + movie.getGenre() + "\",\n";
        result = result + "\t\t\"mpaaRating\": \"" + movie.getMpaaRating() + "\",\n";
        result = result + "\t\t\"director\": \"" + movie.getDirector() + "\"\n";
        return result;
    }

    public Movie convertDataArrayToMovie(String[] data, Hashtable<String, Person> personsTable) {
        Movie movie = new Movie();
        movie.setId(Integer.parseInt(data[0]));
        movie.setName(data[1]);
        movie.setCoordinates(stringToCoordinates(data[2]));
        movie.setCreationDate(stringToDate(data[3]));
        movie.setOscarsCount(Integer.parseInt(data[4]));
        movie.setGenre(MovieGenre.valueOf(data[5]));
        movie.setMpaaRating(MpaaRating.valueOf(data[6]));
        movie.setDirector(stringToPerson(data[7], personsTable));
        return movie;
    }

    private Person stringToPerson(String line, Hashtable<String, Person> personsTable) {
        List<String> data = List.of(line.split(" "));
        Person person = new Person(
                data.get(0),
                data.get(1),
                Color.valueOf(data.get(2)),
                Color.valueOf(data.get(3)),
                Country.valueOf(data.get(4)));
        var foundPerson = personsTable.get(person.getPassportID());
        if (foundPerson == null) {
            personsTable.put(person.getPassportID(), person);
            return person;
        } else {
            if (!foundPerson.equals(person)) {
                throw new MoviesAppException("Two persons with same passportID");
            }
            return person;
        }
    }

    private Coordinates stringToCoordinates(String line) {
        List<Double> data = Stream.of(line.split(" "))
                .map(Double::parseDouble)
                .toList();
        return new Coordinates(data.get(0), data.get(1));
    }

    private Date stringToDate(String line) {
        List<Integer> data = Stream.of(line.split("/"))
                .map(Integer::valueOf)
                .toList();
        return new Date(data.get(2) - 1900, data.get(1) - 1, data.get(0));
    }
}
