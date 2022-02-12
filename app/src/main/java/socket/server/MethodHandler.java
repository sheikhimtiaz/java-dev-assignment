package socket.server;

import socket.server.io.RequestObject;
import socket.server.service.InvokeService;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodHandler implements Runnable{
    private final InvokeService invokeService = new InvokeService();
    RequestObject requestObject;
    ObjectOutputStream objectOutputStream;

    public MethodHandler(RequestObject requestObject1, ObjectOutputStream objectOutputStream1){
        requestObject = requestObject1;
        objectOutputStream = objectOutputStream1;
    }

    @Override
    public void run() {
        try{
            int result = -1;

            System.out.println(Thread.currentThread().getName());
            result = invokeService.invokeMethodWithManagerName(requestObject);
            System.out.println("Result : " + result);
            objectOutputStream.writeObject("Hi client : " + result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
