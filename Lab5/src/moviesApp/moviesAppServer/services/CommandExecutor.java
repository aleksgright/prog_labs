package moviesApp.moviesAppServer.services;

import moviesApp.moviesAppServer.entities.Movie;
import moviesApp.moviesAppServer.entities.Person;
import moviesApp.utils.dtos.CommandDto;
import moviesApp.utils.exceptions.MoviesAppException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CommandExecutor {
    private final FileService fileService;
    private final MovieStringConverter movieStringConverter;
    private CommandParser commandParser;
    private Hashtable<String, Person> personsHashtable;
    private Hashtable<Integer, Movie> moviesHashtable;
    private Date initialisationDate;
    private ScriptParser scriptParser;
    private Movie movieFromDto;

    public CommandExecutor(Hashtable<Integer, Movie> moviesHashtable, Hashtable<String, Person> personsHashtable) {
        this.personsHashtable = personsHashtable;
        this.moviesHashtable = moviesHashtable;
        fileService = new FileService();
        movieStringConverter = new MovieStringConverter();
        commandParser = new CommandParser(this);
    }

    public void startRunning() {
        initialisationDate = new Date();
        commandParser = new CommandParser(this);
        scriptParser = new ScriptParser(this);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String command = in.nextLine();
            System.out.println(commandParser.parseCommand(command));
        }
    }

    public void parseCommand(String command) {
        System.out.println(commandParser.parseCommand(command));
    }

    public String executeFromCommandDto(CommandDto commandDto) {
        movieFromDto = commandDto.getMovie();
        if (commandDto.getCommand().equals("exit")) {
            save();
            return "Client's session closed. Collection was saved.";
        }
        if (commandDto.getArgument() != null) {
            return commandParser.parseCommand(commandDto.getCommand() + " " + commandDto.getArgument());
        }
        return commandParser.parseCommand(commandDto.getCommand());
    }

    public String info() {
        return "Collection type: HashTable\n" + "Creation date: "
                + initialisationDate + "\nКол-во элементов: " + moviesHashtable.size();
    }

    public String executeScript(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String command;
            while ((command = reader.readLine()) != null) {
                if (command.equals("executeScript")) {
                    System.out.println("Inner scripts are not allowed");
                    break;
                }
                try {
                    scriptParser.parseCommand(command, reader);
                } catch (MoviesAppException e) {
                    break;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new MoviesAppException("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new MoviesAppException("Can not read the file");
        }
        return "execute_script completed successfully";
    }

    private void removeDirectorByMoviesId(int id) {
        String personToRemovePassportId = moviesHashtable.get(id).getDirector().getPassportID();
        int count = 0;
        Collection<Movie> movies = moviesHashtable.values();
        for (Movie movie : movies) {
            if (movie.getDirector().getPassportID().equals(personToRemovePassportId)) {
                count++;
            }
        }
        if (count <= 1) {
            personsHashtable.remove(personToRemovePassportId);
        }
    }

    public String filterGreaterThanOscarsCount(int oscarsCount) {
        for (Movie movie : moviesHashtable.values()) {
            if (movie.getOscarsCount() > oscarsCount) {
                System.out.println(movie);
            }
        }
        return "filter_greater_than_oscars_count completed successfully";
    }

    public String show() {
        StringBuilder result = new StringBuilder();
        for (Movie movie : moviesHashtable.values()) {
            result.append(movie);
        }
        return result.toString();
    }

    public String filterStartsWithName(String beginning) {
        for (Movie movie : moviesHashtable.values()) {
            if (movie.getName().indexOf(beginning) == 0) {
                System.out.println(movie);
            }
        }
        return "filter_starts_with_name completed successfully";
    }

    public String sumOfOscarsCount() {
        int result = 0;
        for (Movie movie : moviesHashtable.values()) {
            result += movie.getOscarsCount();
        }
        System.out.println(result);
        return "sum_of_oscars_count completed successfully";
    }

    public String removeGreaterKey(int id) {
        int size = moviesHashtable.size();
        for (int i = id + 1; i <= size; i++) {
            removeDirectorByMoviesId(i);
            moviesHashtable.remove(i);
        }
        return "remove_greater_key completed successfully";
    }

    public String remove(int id) {
        if (moviesHashtable.containsKey(id)) {
            removeDirectorByMoviesId(id);
            int size = moviesHashtable.size();
            for (int i = id; i < size; i++) {
                Movie replacedMovie = moviesHashtable.get(i + 1);
                replacedMovie.setId(replacedMovie.getId() - 1);
                moviesHashtable.replace(i, moviesHashtable.get(i + 1));
            }
            moviesHashtable.remove(size);
        } else {
            System.out.println("No such id");
        }
        return "remove completed successfully";
    }

    public String clear() {
        moviesHashtable.clear();
        return "clear completed successfully";
    }

    public String save() {
        fileService.write(moviesHashtable);
        return "save completed successfully";
    }

    public String help() {
        return "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "insert null {element} : добавить новый элемент с заданным ключом\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_key null : удалить элемент из коллекции по его ключу\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого\n" +
                "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный\n" +
                "sum_of_oscars_count : вывести сумму значений поля oscarsCount для всех элементов коллекции\n" +
                "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки\n" +
                "filter_greater_than_oscars_count oscarsCount : вывести элементы, значение поля oscarsCount которых больше заданного";
    }

    public String exit() {
        System.exit(0);
        return "exit completed successfully";
    }

    public String informAboutInvalidCommand() {
        return "Invalid command. Enter help to see the list of possible commands";
    }

    public String replaceIfGreater(int id) {
        if (moviesHashtable.containsKey(id)) {
            Movie movieToUpdate = getMovieFromConsoleOrCommandDto(id);
            if (movieToUpdate.compareTo(moviesHashtable.get(id)) > 0) {
                removeDirectorByMoviesId(id);
                moviesHashtable.replace(id, movieToUpdate);
            }
        } else {
            System.out.println("No such id");
        }
        return "replace_if_greater completed successfully";
    }

    public String replaceIfGreater(int id, BufferedReader reader) {
        if (moviesHashtable.containsKey(id)) {
            Movie movieToUpdate = getMovieFromScript(id + 1, reader);
            if (movieToUpdate.compareTo(moviesHashtable.get(id)) > 0) {
                removeDirectorByMoviesId(id);
                moviesHashtable.replace(id, movieToUpdate);
            }
        } else {
            System.out.println("No such id");
        }
        return "replace_if_greater completed successfully";
    }

    public String removeLower() {
        int size = moviesHashtable.size();
        Movie movieToUpdate = getMovieFromConsoleOrCommandDto(size + 1);
        for (int i = 1; i <= size; i++) {
            if (movieToUpdate.compareTo(moviesHashtable.get(i)) > 0) {
                removeDirectorByMoviesId(i);
                moviesHashtable.remove(i);
            }
        }
        Set<Integer> keys = moviesHashtable.keySet();
        List<Integer> keysArray = new ArrayList<>(keys);
        keysArray.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        int currentPlace = 1;
        for (int i : keysArray) {
            Movie movie = moviesHashtable.remove(i);
            movie.setId(currentPlace);
            moviesHashtable.put(currentPlace, movie);
            currentPlace++;
        }
        return "remove_lower completed successfully";
    }

    public String removeLower(BufferedReader reader) {
        int size = moviesHashtable.size();
        Movie movieToUpdate = getMovieFromScript(size + 1, reader);
        for (int i = 1; i <= size; i++) {
            if (movieToUpdate.compareTo(moviesHashtable.get(i)) > 0) {
                removeDirectorByMoviesId(i);
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
        return "remove_lower completed successfully";
    }

    public String insert(int id) {
        Movie movieToInsert = getMovieFromConsoleOrCommandDto(id);
        int size = moviesHashtable.size();
        if (id > size) {
            moviesHashtable.put(id, movieToInsert);
        } else {
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
        return "insert completed successfully";
    }

    public String insert(int id, BufferedReader reader) {
        Movie movieToInsert = getMovieFromScript(id, reader);
        int size = moviesHashtable.size();
        if (id > size) {
            moviesHashtable.put(id, movieToInsert);
        } else {
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
        return "insert completed successfully";
    }

    public String update(int id) {
        if (moviesHashtable.containsKey(id)) {
            removeDirectorByMoviesId(id);
            moviesHashtable.replace(id, getMovieFromConsoleOrCommandDto(id));
        } else {
            System.out.println("No such id");
        }
        return "update completed successfully";
    }

    public String update(int id, BufferedReader reader) {
        if (moviesHashtable.containsKey(id)) {
            removeDirectorByMoviesId(id);
            moviesHashtable.replace(id, getMovieFromScript(id, reader));
        } else {
            System.out.println("No such id");
        }
        return "update completed successfully";
    }

    private Movie getMovieFromConsoleOrCommandDto(int id) {
        if (movieFromDto != null) {
            return movieFromDto;
        }
        String[] movieData = commandParser.inputMovieData();
        movieData[0] = String.valueOf(id);
        return movieStringConverter.convertDataArrayToMovie(movieData);
    }

    private Movie getMovieFromScript(int id, BufferedReader reader) {
        String[] movieData = scriptParser.readMovieData(reader);
        movieData[0] = String.valueOf(id);
        return movieStringConverter.convertDataArrayToMovie(movieData);
    }

    public Hashtable<Integer, Movie> getMoviesHashtable() {
        return moviesHashtable;
    }

    public Hashtable<String, Person> getPersonsHashtable() {
        return personsHashtable;
    }
}
