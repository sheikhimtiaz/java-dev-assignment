package socket.server.service;

import socket.server.io.RequestObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class InvokeService {
    private static final String PREFIX = "socket.server.manager.";

    public int invokeMethodWithManagerName(RequestObject requestObject) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> c = Class.forName(PREFIX + requestObject.managerName);
        Constructor<?> cons = c.getConstructor();
        Object object = cons.newInstance();

        Method method = object.getClass().getMethod(requestObject.method, int.class);

        return (int)method.invoke(object, Integer.parseInt((String)requestObject.args.get("n")));
    }
}
