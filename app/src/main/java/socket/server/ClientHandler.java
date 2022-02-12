package socket.server;

import socket.server.io.RequestObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private static final String EXIT = "EXIT";

    public ClientHandler(Socket inSocket){
        socket = inSocket;
    }


    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try{
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//            String message = (String) objectInputStream.readObject();
            System.out.println(Thread.currentThread().getName());
            RequestObject requestObject;
            ExecutorService executor = Executors.newFixedThreadPool( 10);

            while((requestObject = (RequestObject) objectInputStream.readObject())!=null){
                System.out.println("Message from client: " + requestObject);

                if (requestObject == null || requestObject.method.equalsIgnoreCase(EXIT))
                    break;

//                MethodHandler methodHandler = new MethodHandler(requestObject, objectOutputStream);
//                Thread thread = new Thread(methodHandler);
//                thread.start();

                MethodHandler methodHandler = new MethodHandler(requestObject, objectOutputStream);
                executorService.submit(methodHandler);

            }

            objectInputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(objectInputStream!=null) objectInputStream.close();
                if(objectInputStream!=null) objectInputStream.close();
                if(socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
