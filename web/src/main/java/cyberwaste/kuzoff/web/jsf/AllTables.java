package cyberwaste.kuzoff.web.jsf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.primefaces.event.CellEditEvent;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Value;
import cyberwaste.kuzoff.core.impl.DatabaseManagerImpl;

public class AllTables {

    private DatabaseManager databaseManager;
    
    private String database;
    private String table;
    
    public String getDatabase() {
        return database;
    }
    
    public void setDatabase(String database) {
        this.database = database;
    }
    
    public String getTable() {
        return table;
    }
    
    public void setTable(String table) {
        this.table = table;
    }
    
    public List<String> getDatabases() {
        List<String> databases = new ArrayList<String>();
        for (File databaseFolder : DatabaseManagerImpl.KUZOFF_HOME.listFiles()) {
            databases.add(databaseFolder.getName());
        }
        return databases;
    }
    
    public Collection<String> getTables() throws IOException {
        try {
            databaseManager.forDatabaseFolder(database);
            Collection<String> tables = new ArrayList<String>();
            for (Table table : databaseManager.listTables()) {
                tables.add(table.getName());
            }
            return tables;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    public List<Row> getData() throws IOException {
        try {
            databaseManager.forDatabaseFolder(database);
            return databaseManager.loadTableData(table);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    public List<String> getColumnTypeNames() throws IOException {
        try {
            databaseManager.forDatabaseFolder(database);
            return databaseManager.loadTable(table).getColumnTypeNames();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }  
  
    public void onCellEdit(CellEditEvent event) throws Exception {
        String columnHeader = event.getColumn().getHeaderText();
        int columnIndex = Integer.parseInt(columnHeader.substring(columnHeader.indexOf("#") + 1, columnHeader.indexOf(":"))) - 1;
        
        List<Row> data = getData();
        
        List<String> oldRow = new ArrayList<String>();
        List<String> newRow = new ArrayList<String>();
        for (Value value : data.get(event.getRowIndex()).getRow()) {
            oldRow.add(value.data());
            newRow.add(value.data());
        }
        newRow.set(columnIndex, event.getNewValue().toString());

        databaseManager.forDatabaseFolder(database);
        databaseManager.removeRow(table, oldRow);
        databaseManager.addRow(table, newRow);
    } 
    
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}
