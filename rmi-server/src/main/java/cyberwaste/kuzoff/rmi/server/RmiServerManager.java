package cyberwaste.kuzoff.rmi.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiServerManager {
    
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("rmiServerContext.xml");
    }
}
