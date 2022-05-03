package moviesApp.utils.dtos;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ResponseDto implements Serializable {
    List<String> messages = new LinkedList<>();

    public void addMessage(String message){
        messages.add(message);
    }

    public void printMessages(){
        messages.forEach(System.out::println);
    }
}
