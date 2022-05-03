package moviesApp.moviesAppServer;

import moviesApp.moviesAppServer.entities.Movie;
import moviesApp.moviesAppServer.entities.Person;
import moviesApp.moviesAppServer.services.CommandExecutor;
import moviesApp.utils.dtos.CommandDto;
import moviesApp.utils.dtos.ResponseDto;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static SocketChannel clientSocket; //сокет для общения
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private List<SocketChannel> connectedClients;
    private CommandExecutor commandExecutor;
    public static final int PORT = 8790;
    int bufferSize = 8192;
    static final byte[] OK = new byte[]{0x00, 0x5a, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

//    public Server(int port, String host) {
//        PORT = port;
//        HOST = host;
//    }

    static class Attachment {
        /**
         * Буфер для чтения, в момент проксирования становится буфером для
         * записи для ключа хранимого в peer
         * <p>
         * ВАЖНО: При парсинге Socks4 заголовком мы предполагаем что размер
         * буфера, больше чем размер нормального заголовка, у браузера Mozilla
         * Firefox, размер заголовка равен 12 байт 1 версия + 1 команда + 2 порт +
         * 4 ip + 3 id (MOZ) + 1 \0
         */

        ByteBuffer in;
        /**
         * Буфер для записи, в момент проксирования равен буферу для чтения для
         * ключа хранимого в peer
         */
        ByteBuffer out;
        /**
         * Куда проксируем
         */
        SelectionKey peer;

    }


    public Server(Hashtable<Integer, Movie> moviesHashtable, Hashtable<String, Person> personsHashtable) {
        commandExecutor = new CommandExecutor(moviesHashtable, personsHashtable);
    }


    public void run() {
        try {
            // Создаём Selector
            Selector selector = Selector.open();
            // Открываем серверный канал
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            // Убираем блокировку
            serverChannel.configureBlocking(false);
            // Вешаемся на порт
            serverChannel.socket().bind(new InetSocketAddress(PORT));
            // Регистрация в селекторе
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            Scanner scanner = new Scanner(System.in);
            while (selector.select()>-1) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isValid()) {
                        try {
                            if (key.isAcceptable()) {
                                // Принимаем соединение
                                accept(key);
                            } else if (key.isReadable()) {
                                // Читаем данные
//                            read(key);
                                try {
                                    SocketChannel channel = (SocketChannel) key.channel();
                                    ResponseDto responseDto = performRequest(receiveData(channel));
                                    sendResponse(responseDto, channel);
                                    channel.close();
                                } catch (StreamCorruptedException e) {
                                    System.out.println("JOPA");
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


    private void accept(SelectionKey key) throws IOException, ClosedChannelException {
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

//    private void read(SelectionKey key) throws IOException, UnknownHostException, ClosedChannelException {
//        SocketChannel channel = ((SocketChannel) key.channel());
//        Attachment attachment = ((Attachment) key.attachment());
//        if (attachment == null) {
//            // Лениво инициализируем буферы
//            key.attach(attachment = new Attachment());
//            attachment.in = ByteBuffer.allocate(bufferSize);
//        }
//        if (channel.read(attachment.in) < 1) {
//            // -1 - разрыв 0 - нету места в буфере, такое может быть только если
//            // заголовок превысил размер буфера
//            close(key);
//        } else if (attachment.peer == null) {
//            // если нету второго конца :) стало быть мы читаем заголовок
//            readHeader(key, attachment);
//        } else {
//            // ну а если мы проксируем, то добавляем ко второму концу интерес
//            // записать
//            attachment.peer.interestOps(attachment.peer.interestOps() | SelectionKey.OP_WRITE);
//            // а у первого убираем интерес прочитать, т.к пока не записали
//            // текущие данные, читать ничего не будем
//            key.interestOps(key.interestOps() ^ SelectionKey.OP_READ);
//            // готовим буфер для записи
//            attachment.in.flip();
//        }
//    }

    private void readHeader(SelectionKey key, Attachment attachment) throws IllegalStateException, IOException,
            UnknownHostException, ClosedChannelException {
        byte[] ar = attachment.in.array();
        if (ar[attachment.in.position() - 1] == 0) {
            // Если последний байт \0 это конец ID пользователя.
            if (ar[0] != 4 && ar[1] != 1 || attachment.in.position() < 8) {
                // Простенькая проверка на версию протокола и на валидность
                // команды,
                // Мы поддерживаем только conect
                throw new IllegalStateException("Bad Request");
            } else {
                // Создаём соединение
                SocketChannel peer = SocketChannel.open();
                peer.configureBlocking(false);
                // Получаем из пакета адрес и порт
                byte[] addr = new byte[]{ar[4], ar[5], ar[6], ar[7]};
                int p = (((0xFF & ar[2]) << 8) + (0xFF & ar[3]));
                // Начинаем устанавливать соединение
                peer.connect(new InetSocketAddress(InetAddress.getByAddress(addr), p));
                // Регистрация в селекторе
                SelectionKey peerKey = peer.register(key.selector(), SelectionKey.OP_CONNECT);
                // Глушим запрашивающее соединение
                key.interestOps(0);
                // Обмен ключами :)
                attachment.peer = peerKey;
                Attachment peerAttachemtn = new Attachment();
                peerAttachemtn.peer = key;
                peerKey.attach(peerAttachemtn);
                // Очищаем буфер с заголовками
                attachment.in.clear();
            }
        }
    }


    private void write(SelectionKey key) throws IOException {
        // Закрывать сокет надо только записав все данные
        SocketChannel channel = ((SocketChannel) key.channel());
        Attachment attachment = ((Attachment) key.attachment());
        if (channel.write(attachment.out) == -1) {
            close(key);
        } else if (attachment.out.remaining() == 0) {
            if (attachment.peer == null) {
                // Дописали что было в буфере и закрываемся
                close(key);
            } else {
                // если всё записано, чистим буфер
                attachment.out.clear();
                // Добавялем ко второму концу интерес на чтение
                attachment.peer.interestOps(attachment.peer.interestOps() | SelectionKey.OP_READ);
                // А у своего убираем интерес на запись
                key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);
            }
        }
    }


    private void connect(SelectionKey key) throws IOException {
        SocketChannel channel = ((SocketChannel) key.channel());
        Attachment attachment = ((Attachment) key.attachment());
        // Завершаем соединение
        channel.finishConnect();
        // Создаём буфер и отвечаем OK
        attachment.in = ByteBuffer.allocate(bufferSize);
        attachment.in.put(OK).flip();
        attachment.out = ((Attachment) attachment.peer.attachment()).in;
        ((Attachment) attachment.peer.attachment()).out = attachment.in;
        // Ставим второму концу флаги на на запись и на чтение
        // как только она запишет OK, переключит второй конец на чтение и все
        // будут счастливы
        attachment.peer.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        key.interestOps(0);
    }


    private void close(SelectionKey key) throws IOException {
        key.cancel();
        key.channel().close();
    }
}

//    private void openServerSocket(){
//        try {
//            serverSocket = new ServerSocketChannel(PORT);
//    //        serverSocket.setSoTimeout(soTimeout);
//        } catch (IllegalArgumentException exception) {
//            throw new MoviesAppException("Порт '" + PORT + "' находится за пределами возможных значений!");
//        } catch (IOException exception) {
//            throw new MoviesAppException("Произошла ошибка при попытке использовать порт '" + PORT + "'!");
//        }
//    }
//
//    private SocketChannel connectToClient() {
//        try {
////            Outputer.println("Прослушивание порта '" + port + "'...");
////            App.logger.info("Прослушивание порта '" + port + "'...");
//            SocketChannel clientSocket = serverSocket.accept();
////            Outputer.println("Соединение с клиентом успешно установлено.");
////            App.logger.info("Соединение с клиентом успешно установлено.");
//            return clientSocket;
//        } catch (IOException exception) {
////            Outputer.printerror("Произошла ошибка при соединении с клиентом!");
////            App.logger.error("Произошла ошибка при соединении с клиентом!");
//            throw new MoviesAppException("Произошла ошибка при соединении с клиентом");
//        }
//    }

