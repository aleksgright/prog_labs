package moviesApp.moviesAppServer;

import moviesApp.moviesAppServer.entities.Movie;
import moviesApp.moviesAppServer.entities.Person;
import moviesApp.moviesAppServer.services.CommandExecutor;
import moviesApp.utils.dtos.CommandDto;
import moviesApp.utils.dtos.ResponseDto;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Hashtable;
import java.util.Iterator;

public class Server implements Runnable{
    private CommandExecutor commandExecutor;
    public static final int PORT = 8790;


    public Server(Hashtable<Integer, Movie> moviesHashtable, Hashtable<String, Person> personsHashtable) {
        commandExecutor = new CommandExecutor(moviesHashtable, personsHashtable);
    }


    public void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(PORT));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (selector.select() > -1) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isValid()) {
                        try {
                            if (key.isAcceptable()) {
                                accept(key);
                            } else if (key.isReadable()) {
                                try {
                                    SocketChannel channel = (SocketChannel) key.channel();
                                    ResponseDto responseDto = performRequest(receiveData(channel));
                                    sendResponse(responseDto, channel);
                                    channel.close();
                                } catch (StreamCorruptedException e) {
                                    System.out.println("JOPA");
                                } catch (IOException ignored) {
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            close(key);
                        }
                    }
                }
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel newChannel = ((ServerSocketChannel) key.channel()).accept();
        newChannel.configureBlocking(false);
        newChannel.register(key.selector(), SelectionKey.OP_READ);
        System.out.println("Connection is up");
    }

    private CommandDto receiveData(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(16000);
        socketChannel.read(buffer);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
        System.out.println("Message recieved");
        return (CommandDto) objectInputStream.readObject();
    }

    private ResponseDto performRequest(CommandDto request) {
        String response = commandExecutor.executeFromCommandDto(request);
        ResponseDto responseDto = new ResponseDto();
        responseDto.addMessage(response);
        return responseDto;
    }

    private void sendResponse(ResponseDto response, SocketChannel socketChannel) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
        socketChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
    }

    private void close(SelectionKey key) throws IOException {
        key.cancel();
        key.channel().close();
    }
}

