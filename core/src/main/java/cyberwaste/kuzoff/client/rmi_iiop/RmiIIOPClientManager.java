package cyberwaste.kuzoff.client.rmi_iiop;


import javax.rmi.*;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.naming.Context;

import cyberwaste.kuzoff.client.shell.ShellManager;
import cyberwaste.kuzoff.core.DatabaseManager;

public class RmiIIOPClientManager {

public static void  main( String args[] ) {
  Context ic;
  Object objref;
  DatabaseManager db;

  try {
    ic = new InitialContext();
  } catch (NamingException e) {
      System.out.println("failed to obtain context" + e);
      e.printStackTrace();
      return;
  }

  try {
    objref = ic.lookup("DatabaseService");
    System.out.println("Client: Obtained a ref. to Database Server.");
  } catch (NamingException e) {
      System.out.println("failed to lookup object reference");
      e.printStackTrace();
      return;
  }

  try {
    db = (DatabaseManager) PortableRemoteObject.narrow(
      objref, DatabaseManager.class);
    ShellManager shell = new ShellManager();
    shell.setDatabaseManager(db);
    System.out.println("Shell started...");
    shell.start();
  } catch (ClassCastException e) {
      System.out.println("narrow failed");
      e.printStackTrace();
      return;
    } catch( Exception e ) {
          System.err.println( "Exception " + e + "Caught" );
          e.printStackTrace( );
          return;
      }
}
}
