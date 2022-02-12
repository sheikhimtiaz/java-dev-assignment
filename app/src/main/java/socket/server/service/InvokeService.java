package socket.server.service;

import socket.server.io.RequestObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class InvokeService {
    private static final String MANAGER_PATH = "socket.server.manager.";

    public int invokeMethodWithManagerName(RequestObject requestObject) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> c = Class.forName(MANAGER_PATH + requestObject.managerName);
        Constructor<?> cons = c.getConstructor();
        Object object = cons.newInstance();

        Method method = getMethodWithNameAndParameter(requestObject, object);

        return (method!=null) ? (int)method.invoke(object, Integer.parseInt((String)requestObject.args.get("n"))) : -1;
    }

    private Method getMethodWithNameAndParameter(RequestObject requestObject, Object object){
        for (Method method : object.getClass().getMethods()) {
            if (method.getName().equals(requestObject.method) && (method.getParameters().length == requestObject.args.size())) {
                return method;
            }
        }
        return null;
    }

}
