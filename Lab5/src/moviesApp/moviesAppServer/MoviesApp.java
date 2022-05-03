package moviesApp.moviesAppServer;

import moviesApp.moviesAppServer.entities.Movie;
import moviesApp.moviesAppServer.entities.Person;
import moviesApp.moviesAppServer.services.CommandExecutor;
import moviesApp.moviesAppServer.services.FileService;

import java.util.Hashtable;


public class MoviesApp {
    public static void main(String[] args) {
        final FileService fileService = new FileService();
        Hashtable<Integer, Movie> moviesTable = new Hashtable<>();
        Hashtable<String, Person> personsTable = new Hashtable<>();
        try {
            fileService.read(moviesTable, personsTable);
        } catch (Throwable e) {
            System.out.println("No variable");
            System.exit(0);
        }
        Server server = new Server(moviesTable, personsTable);
        Thread thread = new Thread(server);
        thread.start();
        CommandExecutor commandExecutor = new CommandExecutor(moviesTable, personsTable);
        commandExecutor.startRunning();
        //        System.out.println(moviesTable);
    }
}
