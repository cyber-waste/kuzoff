package cyberwaste.kuzoff.client.rmi_iiop;

import javax.naming.InitialContext;
import javax.naming.Context;
import org.omg.PortableServer.*;

import java.util.*;
import org.omg.CORBA.*;

import cyberwaste.kuzoff.core.impl.RemoteDatabaseManagerImpl;

import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;

public class RmiIIOPServerManager {
public RmiIIOPServerManager(String[] args) {
  try {
    Properties p = System.getProperties();
    p.put("org.omg.CORBA.ORBClass", 
        "com.sun.corba.se.internal.POA.POAORB");
    p.put("org.omg.CORBA.ORBSingletonClass", 
        "com.sun.corba.se.internal.corba.ORBSingleton");

    ORB orb = ORB.init( args, p );

    POA rootPOA = (POA)orb.resolve_initial_references("RootPOA");

    Policy[] tpolicy = new Policy[3];
    tpolicy[0] = rootPOA.create_lifespan_policy(
      LifespanPolicyValue.TRANSIENT );
    tpolicy[1] = rootPOA.create_request_processing_policy(
      RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY );
    tpolicy[2] = rootPOA.create_servant_retention_policy(
      ServantRetentionPolicyValue.RETAIN);
    POA tPOA = rootPOA.create_POA("MyTransientPOA", null, tpolicy);
        
    tPOA.the_POAManager().activate();

    RemoteDatabaseManagerImpl impl = new RemoteDatabaseManagerImpl();
    Tie tie = (Tie)Util.getTie( impl );
    String dbId = "db";
    byte[] id = dbId.getBytes();
    tPOA.activate_object_with_id( id, (Servant) tie );

    Context initialNamingContext = new InitialContext();
    initialNamingContext.rebind("DatabaseService", 
      tPOA.create_reference_with_id(id, 
        ((Servant) tie)._all_interfaces(tPOA,id)[0]) );
    System.out.println("Database Server: Ready...");

    orb.run();
  } 
    catch (Exception e) {
      System.out.println("Problem running Database Server: " + e);
      e.printStackTrace();
    } 
}


public static void main(String args[]) {
  new RmiIIOPServerManager( args );
}
}
