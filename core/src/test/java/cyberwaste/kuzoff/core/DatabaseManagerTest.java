package cyberwaste.kuzoff.core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.impl.RemoteDatabaseManagerImpl;

public class DatabaseManagerTest {
    
    private RemoteDatabaseManagerImpl manager;
    
    @Before
    public void setUp() throws Exception {
        manager = new RemoteDatabaseManagerImpl();
        manager.forDatabaseFolder("/home/vlg/Documents/db_test");
        List<String> types = new ArrayList<String>();
        types.add("int"); types.add("char");
        manager.createTable("table1",types);
        List<String> row = new ArrayList<String>();
        row.add("10"); row.add("a");
        manager.addRow("table1",row);
        row = new ArrayList<String>();
        row.add("20"); row.add("b");
        manager.addRow("table1", row);
        
        manager.createTable("table2", types);
        row.clear();
        row.add("20"); row.add("b");
        manager.addRow("table2", row);
        row.clear();
        row.add("30"); row.add("c");
        manager.addRow("table2", row);
        row.clear();
        row.add("30"); row.add("c");
        manager.addRow("table2", row);
        
    }

    @After
    public void tearDown() throws Exception {
        manager.dropDatabase();
    }

    @Test
    public void listTablesTest() throws IOException {
        String[] typeNames = {"int", "char"}; 
        Collection<Table> tables = manager.listTables();
        assertEquals(2, tables.size());
        Iterator<Table> it = tables.iterator();
        Table table = it.next();
        assertEquals(table.name(), "table2");
        List<Type> types = table.columnTypes();
        for(int i=0;i<types.size();i++){
            assertEquals(types.get(i).name(),typeNames[i]);
        }
        table = it.next();
        assertEquals(table.name(), "table1");
        for(int i=0;i<types.size();i++){
            assertEquals(types.get(i).name(),typeNames[i]);
        }
    }
    
    @Test
    public void loadTableDataTest() throws IOException{
        List<Row> rows = manager.loadTableData("table1");
        assertEquals(2,rows.size());
        for(Row row : rows) assertEquals(2,row.length());
        String[][] rowDataActual = {{"10","a"},{"20","b"}};
        for(int i=0; i<rows.size();i++){
            Row row = rows.get(i);
            for(int j=0;j<row.length();j++){
                assertEquals(row.getElement(j).getValue(), rowDataActual[i][j]);
            }
        }
    }
    
    @Test
    public void removeRowTest() throws Exception {
        Map<Integer,String> columnData = new HashMap<Integer, String>();
        columnData.put(2, "a");
        manager.removeRow("table1", columnData);
        List<Row> rows = manager.loadTableData("table1");
        assertEquals(1,rows.size());
        Row row = rows.get(0);
        String[] rowDataActual = {"20","b"};
        for(int i=0;i<row.length();i++){
            assertEquals(row.getElement(i).getValue(), rowDataActual[i]);
        }
    }
    
    @Test
    public void unionTablesTest() throws Exception{
        Table unionTable = manager.unionTable("table1", "table2");
        List<Row> rows = manager.loadTableData(unionTable.name());
        assertEquals(3, rows.size());
        String[][] rowDataActual = {{"10","a"},{"20","b"},{"30","c"}};
        for(int i=0;i<rows.size();i++){
            for(int j=0;j<unionTable.columnTypes().size();j++){
                assertEquals(rowDataActual[i][j], rows.get(i).getElement(j).getValue());
            }
        }
    }

    @Test
    public void differenceTableTest() throws Exception{
        Table differenceTable = manager.differenceTable("table1", "table2");
        List<Row> rows = manager.loadTableData(differenceTable.name());
        assertEquals(1, rows.size());
        Row row = rows.get(0);
        String[] rowDataActual = {"10","a"};
        for(int i=0;i<differenceTable.columnTypes().size();i++){
            assertEquals(rowDataActual[i], row.getElement(i).getValue());
        }
    }
    
    @Test
    public void uniqueTableTest() throws Exception{
        Table uniqueTable = manager.uniqueTable("table2");
        List<Row> rows = manager.loadTableData(uniqueTable.name());
        assertEquals(2, rows.size());
        String[][] rowDataActual = {{"20","b"},{"30","c"}};
        for(int i=0;i<rows.size();i++){
            for(int j=0;j<uniqueTable.columnTypes().size();j++){
                assertEquals(rowDataActual[i][j], rows.get(i).getElement(j).getValue());
            }
        }
    }
    
}
