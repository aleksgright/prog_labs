package moviesApp.moviesAppClient;

import moviesApp.utils.dtos.CommandDto;
import moviesApp.utils.dtos.ResponseDto;
import moviesApp.utils.exceptions.MoviesAppException;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    private static String Ip;
    // мы узнаем что хочет сказать клиент?

    public static void main(String[] args) {
        try {
            try {
                System.out.print("Enter IP: ");
                Scanner scanner = new Scanner(System.in);
                Ip = scanner.nextLine();
                CommandDtoCreator commandDtoCreator = new CommandDtoCreator();
                boolean serverWasUp = false;
                boolean f = true;
                while (f) {
                    try {
                        if (!serverWasUp) {
                            System.out.println("Sending connection request...");
                            serverWasUp = true;
                        }
                        clientSocket = new Socket(Ip, 8790);
                        if (!serverWasUp) {
                            System.out.println("Connection is up");
                            serverWasUp = true;
                        }
                        serverWasUp = true;
                        System.out.print(">");
                        String word = scanner.nextLine(); // ждём пока клиент что-нибудь
                        if ("exit".equals(word)) {
                            f = false;
                        }
                        if (word.equals("execute_script")) {

                        }
                        // не напишет в консоль
                        try {
                            sendRequest(commandDtoCreator.parseCommand(word));
                            readResponse();
                        } catch (MoviesAppException e) {
                            System.out.println("Invalid command");
                        }
                    } catch (ConnectException e) {
                        if (serverWasUp) {
                            System.out.println("Сервер не запущен");
                            serverWasUp = false;
                        }
                    } catch (SocketException e) {
                        if (serverWasUp) {
                            System.out.println("Сервер не отвечает");
                            serverWasUp = false;
                        }
//                    String serverWord = in.readLine(); // ждём, что скажет сервер
//                    System.out.println(serverWord); // получив - выводим на экран
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                try {
                    clientSocket.close();
                } catch (NullPointerException ignored) {
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private static void sendRequest(CommandDto commandDto) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandDto);
        byteArrayOutputStream.writeTo(outputStream);
        System.out.println("Request sent");
    }

    private static void readResponse() throws IOException, ClassNotFoundException {
        InputStream inputStream = clientSocket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ResponseDto responseDto = (ResponseDto) objectInputStream.readObject();
        inputStream.close();
        System.out.println("Response received");
        responseDto.printMessages();
    }
}
