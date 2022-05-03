package moviesApp.moviesAppClient;

import moviesApp.utils.dtos.CommandDto;
import moviesApp.utils.dtos.ResponseDto;
import moviesApp.utils.exceptions.MoviesAppException;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static Socket clientSocket;
    private static String Ip;

    public static void main(String[] args) {
        try {
            try {
                System.out.print("Enter IP: ");
                Scanner scanner = new Scanner(System.in);
                Ip = scanner.nextLine();
                CommandDtoCreator commandDtoCreator = new CommandDtoCreator();
                int connectionTries = 0;
                boolean serverWasUp = false;
                boolean f = true;
                while (f) {
                    try {
                        if (connectionTries > 500) {
                            System.out.println("Waiting time exceeded");
                            throw new MoviesAppException("Waiting time exceeded");
                        }
                        if (connectionTries > 1) {
                            System.out.println("Sending connection request...");
                        }
                        connectionTries++;
                        clientSocket = new Socket(Ip, 8790);
                        if (connectionTries>1) {
                            System.out.println("Connection is up");
                            serverWasUp = true;
                        }
                        connectionTries = 0;
                        System.out.print(">");
                        String word = scanner.nextLine();
                        if ("exit".equals(word)) {
                            f = false;
                        }
                        if (word.startsWith("exec")) {
                            List<CommandDto> commandDtoList = new LinkedList<>();
                            try (BufferedReader reader = new BufferedReader(new FileReader(word.split(" ")[1]))) {
                                ClientsScriptParser clientsScriptParser = new ClientsScriptParser();
                                String scriptCommand;
                                while ((scriptCommand = reader.readLine()) != null) {
                                    if (scriptCommand.equals("executeScript")) {
                                        System.out.println("Inner scripts are not allowed");
                                        break;
                                    }
                                    try {
                                        commandDtoList.add(clientsScriptParser.parseCommand(scriptCommand, reader));
                                    } catch (MoviesAppException e) {
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                throw new MoviesAppException("File not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new MoviesAppException("Can not read the file");
                            }
                            clientSocket.close();
                            sendListOfRequests(commandDtoList.iterator());
                        } else {
                            try {
                                sendRequest(commandDtoCreator.parseCommand(word));
                                readResponse();
                            } catch (MoviesAppException e) {
                                System.out.println("Invalid command");
                            }
                        }
                    } catch (ConnectException e) {
                        if (connectionTries==1) {
                            System.out.println("Server is not up");
                            serverWasUp = false;
                        }
                    } catch (SocketException e) {
                        if (connectionTries==1) {
                            System.out.println("Server does not respond");
                            serverWasUp = false;
                        }
                    }
                }
            } catch (UnknownHostException e) {
                System.out.println("Invalid host");
            } catch (Throwable ignored) {
            } finally {
                System.out.println("Client was closed");
                try {
                    clientSocket.close();
                } catch (NullPointerException ignored) {
                }
            }
        } catch (IOException ignored) {
        }
    }

    private static void sendRequest(CommandDto commandDto) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandDto);
        byteArrayOutputStream.writeTo(outputStream);
    }

    private static void readResponse() throws IOException, ClassNotFoundException {
        InputStream inputStream = clientSocket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ResponseDto responseDto = (ResponseDto) objectInputStream.readObject();
        inputStream.close();
        responseDto.printMessages();
    }

    private static void sendListOfRequests(Iterator<CommandDto> iterator) {
        boolean serverWasUp = true;
        boolean f = true;
        while (f) {
            try {
                if (!serverWasUp) {
                    System.out.println("Sending connection request...");
                }
                clientSocket = new Socket(Ip, 8790);
                if (!serverWasUp) {
                    System.out.println("Connection is up");
                    serverWasUp = true;
                }
                CommandDto commandDto = iterator.next();
                try {
                    sendRequest(commandDto);
                    readResponse();
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
                }
                if (!iterator.hasNext()) {
                    f = false;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e);
            }
        }
    }
}
