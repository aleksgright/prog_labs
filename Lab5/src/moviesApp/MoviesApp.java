package moviesApp;

import moviesApp.entities.Movie;
import moviesApp.entities.Person;
import moviesApp.services.CommandExecutor;
import moviesApp.services.CommandParser;
import moviesApp.services.FileService;

import java.util.Hashtable;


public class MoviesApp {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        Hashtable<Integer, Movie> moviesTable = new Hashtable<>();
        Hashtable<String, Person> personsTable = new Hashtable<>();
        fileService.read(moviesTable, personsTable);
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.startRunning(moviesTable, personsTable);
        System.out.println(moviesTable);
//=        System.out.println(jsonToMovieConverter.convertJsonToMovie("\"id\": \"1\",\n\t\t\"name\": \"Pulp fiction\",\n\t\t\"coordinates\": \"100 50\",\n\t\t\"creationDate\": \"03 07 2022\",\n\t\t\"oscarsCount\": \"1\",\n\t\t\"genre\": \"HORROR\",\n\t\t\"mpaaRating\": \"G\",\n\t\t\"director\": \"Nolan qwerty12345 GREEN YELLOW UNITED_KINGDOM\""));
    }
}
