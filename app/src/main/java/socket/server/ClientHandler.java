package socket.server;

import socket.server.io.RequestObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;

public class ClientHandler implements Runnable{

    ExecutorService executorService;
    private final Socket socket;
    private static final String EXIT = "EXIT";

    public ClientHandler(Socket inSocket, ExecutorService executorService2){
        socket = inSocket;
        executorService = executorService2;
    }


    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(Thread.currentThread().getName());
            RequestObject requestObject;

            while((requestObject = (RequestObject) objectInputStream.readObject())!=null){
                System.out.println("Message from client: " + requestObject);

                if (requestObject == null || requestObject.method.equalsIgnoreCase(EXIT)){
                    System.out.println("Closing the server.");
                    return;
                }

                MethodHandler methodHandler = new MethodHandler(requestObject, objectOutputStream);
                executorService.submit(methodHandler);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
