package moviesApp.services;

import moviesApp.entities.Movie;
import moviesApp.entities.Person;
import moviesApp.utils.MoviesAppException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CommandExecutor {
    private final FileService fileService;
    private final MovieStringConverter movieStringConverter;
    private CommandParser commandParser;
    private Hashtable<String, Person> personsHashtable;
    private Hashtable<Integer, Movie> moviesHashtable;

    public CommandExecutor() {
        fileService = new FileService();
        movieStringConverter = new MovieStringConverter();
    }

    public void startRunning(Hashtable<Integer, Movie> moviesHashtable, Hashtable<String, Person> personsHashtable) {
        this.personsHashtable = personsHashtable;
        this.moviesHashtable = moviesHashtable;
        CommandParser commandParser = new CommandParser(moviesHashtable, personsHashtable);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String command = in.nextLine();
            commandParser.parseCommand(this, command);
        }
    }

    void executeScript(String filePath) {
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            while (true) {
                String command = reader.readLine();
                if (command.equals("")) {
                    break;
                }
                if (command.equals("executeScript")) {
                    System.out.println("Inner scripts are not allowed");
                }
                commandParser.parseCommand(this, command);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MoviesAppException("Can not read the file");
        }
    }

    void filterGreaterThanOscarsCount(int oscarsCount) {
        for (Movie movie : moviesHashtable.values()) {
            if (movie.getOscarsCount() > oscarsCount) {
                System.out.println(movie);
            }
        }
    }

    void show() {
        for (Movie movie : moviesHashtable.values()) {
            System.out.println(movie);
        }
    }

    void filterStartsWithName(String beginning) {
        for (Movie movie : moviesHashtable.values()) {
            if (movie.getName().indexOf(beginning) == 0) {
                System.out.println(movie);
            }
        }
    }

    void sumOfOscarsCount() {
        int result = 0;
        for (Movie movie : moviesHashtable.values()) {
            result += movie.getOscarsCount();
        }
        System.out.println(result);
    }

    void removeGreaterKey(int id) {
        int size = moviesHashtable.size();
        for (int i = id + 1; i <= size; i++) {
            moviesHashtable.remove(i);
        }
    }

    void replaceIfGreater(int id) {
        var movieData = commandParser.inputMovieData();
        movieData[0] = String.valueOf(id);
        var movieToUpdate = movieStringConverter.convertDataArrayToMovie(movieData, personsHashtable);
        if (movieToUpdate.compareTo(moviesHashtable.get(id)) > 0) {
            moviesHashtable.replace(id, movieToUpdate);
        }
    }

    void removeLower() {
        var movieData = commandParser.inputMovieData();
        int size = moviesHashtable.size();
        movieData[0] = String.valueOf(size + 1);
        var movieToUpdate = movieStringConverter.convertDataArrayToMovie(movieData, personsHashtable);
        for (int i = 1; i <= size; i++) {
            if (movieToUpdate.compareTo(moviesHashtable.get(i)) > 0) {
                moviesHashtable.remove(i);
            }
        }
        Set<Integer> keys = moviesHashtable.keySet();
        List<Integer> keysArray = new ArrayList<>(keys);
        keysArray.sort(Comparator.comparingInt(o -> o));
        int currentPlace = 1;
        for (int i : keysArray) {
            Movie movie = moviesHashtable.remove(i);
            movie.setId(currentPlace);
            moviesHashtable.put(currentPlace, movie);
            currentPlace++;
        }
    }

    void remove(int id) {
        int size = moviesHashtable.size();
        for (int i = id; i < size; i++) {
            Movie replacedMovie = moviesHashtable.get(i + 1);
            replacedMovie.setId(replacedMovie.getId() - 1);
            moviesHashtable.replace(i, moviesHashtable.get(i + 1));
        }
        moviesHashtable.remove(size);
    }

    void insert(int id) {
        var movieData = commandParser.inputMovieData();
        movieData[0] = String.valueOf(id);
        var movieToInsert = movieStringConverter.convertDataArrayToMovie(movieData, personsHashtable);
        int size = moviesHashtable.size();
        Movie replacedMovie = moviesHashtable.get(size);
        replacedMovie.setId(replacedMovie.getId() + 1);
        moviesHashtable.put(size + 1, replacedMovie);
        for (int i = size; i > id; i--) {
            replacedMovie = moviesHashtable.get(i - 1);
            replacedMovie.setId(replacedMovie.getId() + 1);
            moviesHashtable.replace(i, replacedMovie);
        }
        moviesHashtable.replace(id, movieToInsert);
    }

    void update(int id) {
        var movieData = commandParser.inputMovieData();
        movieData[0] = String.valueOf(id);
        var movieToUpdate = movieStringConverter.convertDataArrayToMovie(movieData, personsHashtable);
        moviesHashtable.replace(id, movieToUpdate);
    }


    void clear(Hashtable<Integer, Movie> movieHashtable) {
        movieHashtable.clear();
    }

    void save(Hashtable<Integer, Movie> movieHashtable) {
        fileService.write(movieHashtable);
    }

    void help() {
        System.out.println(
                """
                        help : вывести справку по доступным командам
                        info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                        show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                        insert null {element} : добавить новый элемент с заданным ключом
                        update id {element} : обновить значение элемента коллекции, id которого равен заданному
                        remove_key null : удалить элемент из коллекции по его ключу
                        clear : очистить коллекцию
                        save : сохранить коллекцию в файл
                        execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                        exit : завершить программу (без сохранения в файл)
                        remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
                        replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
                        remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный
                        sum_of_oscars_count : вывести сумму значений поля oscarsCount для всех элементов коллекции
                        filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки
                        filter_greater_than_oscars_count oscarsCount : вывести элементы, значение поля oscarsCount которых больше заданного"""
        );
    }

    void exit() {
        System.exit(0);
    }

    void informAboutInvalidCommand() {
        System.out.println("Invalid command. Enter help to see the list of possible commands");
    }
}
