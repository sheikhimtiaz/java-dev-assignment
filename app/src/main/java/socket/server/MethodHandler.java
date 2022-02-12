package socket.server;

import socket.server.io.RequestObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodHandler implements Runnable{
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
            result = invokeMethodWithManagerName(requestObject);
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


    private int invokeMethodWithManagerName(RequestObject requestObject) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> c = Class.forName("socket.server.manager."+requestObject.managerName);
        Constructor<?> cons = c.getConstructor();
        Object object = cons.newInstance();

        Method method = object.getClass().getMethod(requestObject.method, int.class);

        return (int)method.invoke(object, Integer.parseInt((String)requestObject.args.get("n")));
    }
}
