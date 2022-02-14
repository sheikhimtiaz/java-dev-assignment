package socket.server;

import org.junit.jupiter.api.Test;
import socket.server.io.RequestObject;
import socket.server.service.InvokeService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvokeServiceTest {

    @Test
    void execute() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RequestObject requestObject = new RequestObject();
        requestObject.managerName= "PrimeCalculationManager";
        requestObject.method="findPrimes";
        requestObject.args = new HashMap<>();
        requestObject.args.put("n", "200");

        InvokeService invokeService = new InvokeService();
        int expected = invokeService.invokeMethodWithManagerName(requestObject);

        assertEquals(expected, 46);
    }
}
