package moviesApp.services;

import moviesApp.entities.*;
import moviesApp.utils.MoviesAppException;

import java.io.*;
import java.util.Hashtable;

public class FileService {
    private final MovieStringConverter movieStringConverter;
    private static final String environmentKey = "LABA";
    private final String filePath;

    public FileService() {
        this.filePath = System.getenv("LABA");
        movieStringConverter = new MovieStringConverter();
    }

//    public static void editEnvironment(String key, String value) {
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        Map env = processBuilder.environment();
//        env.put(key, value);
//    }

    public void read(Hashtable<Integer, Movie> movieHashtable, Hashtable<String, Person> personsTable) {
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            firstBlock:
            {
                while (true) {
                    reader.readLine();
                    reader.readLine();
                    String[] dataArray = new String[8];
                    for (int i = 0; i < 8; i++) {
                        String line = reader.readLine();
                        if (line == null) {
                            break firstBlock;
                        }
                        dataArray[i] = jsonLineToStringData(line);
                    }
                    movieStringConverter.addMovieToTable(
                            movieStringConverter.convertDataArrayToMovie(dataArray, personsTable),
                            movieHashtable
                    );
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MoviesAppException("Can not read the file");
        }
    }

    public void write(Hashtable<Integer, Movie> movieHashtable) {
        write(movieHashtable, filePath);
    }

    public void write(Hashtable<Integer, Movie> movieHashtable, String fileName) {
        try {
            int commaCounter = 0;
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            byte[] start = "[\n\t{\n".getBytes();
            fileOutputStream.write(start);
            for (int i = 1; i <= movieHashtable.size(); i++) {
                byte[] movieToBytes = movieStringConverter.movieToJsonFormat(movieHashtable.get(i)).getBytes();
                fileOutputStream.write(movieToBytes);
                if (commaCounter < movieHashtable.size() - 1) {
                    byte[] end = "\t},\n\t{\n".getBytes();
                    fileOutputStream.write(end);
                } else {
                    byte[] end = "\t}\n]".getBytes();
                    fileOutputStream.write(end);
                }
                commaCounter++;
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MoviesAppException("Cannot write in file");
        }
    }

    private String jsonLineToStringData(String line) {
        return line.split("\"")[3];
    }
}
