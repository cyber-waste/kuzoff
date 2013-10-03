package cyberwaste.kuzoff.iiop.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.springframework.util.Assert;

import cyberwaste.kuzoff.core.DatabaseManager;

public class RemoteDatabaseManagerFactory {
    
    public DatabaseManager locateRemoteDatabaseManager() throws Exception {
        Context ic = new InitialContext();
        
        Object objref = ic.lookup("DatabaseService");
        Assert.notNull(objref);
        
        DatabaseManager databaseManager = (DatabaseManager) PortableRemoteObject.narrow(objref, DatabaseManager.class);
        Assert.notNull(databaseManager);
        
        return databaseManager;
    }
}
