package cyberwaste.kuzoff.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import cyberwaste.kuzoff.database.DatabaseManager;
import cyberwaste.kuzoff.database.Table;
import cyberwaste.kuzoff.database.Type;

public class DatabaseManagerTest {
    
    private DatabaseManager manager;
    
    @Before
    public void setUp() throws Exception {
        manager = new DatabaseManager("/home/vlg/Documents/db_test");
        List<String> types = new ArrayList<String>();
        types.add("int"); types.add("char");
        manager.createTable("table1",types);
    }

    @After
    public void tearDown() throws Exception {
        manager.dropDatabase();
    }

    @Test
    public void test() throws IOException {
        String[] typeNames = {"int", "char"}; 
        Collection<Table> tables = manager.listTables();
        assertEquals(1, tables.size());
        Iterator<Table> it = tables.iterator();
        Table table = it.next();
        assertEquals(table.name(), "table1");
        List<Type> types = table.columnTypes();
        for(int i=0;i<types.size();i++){
            assertEquals(types.get(i).name(),typeNames[i]);
        }
    }

}
