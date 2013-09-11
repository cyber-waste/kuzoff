package cyberwaste.kuzoff.outputmanager;

import java.util.Collection;

import cyberwaste.kuzoff.database.Row;
import cyberwaste.kuzoff.database.Table;

public abstract class OutputManager {

    public void outputListTables(Collection<Table> tables) {
        outputMessage("Found " + tables.size() + " tables:");
        
        for (Table table : tables) {
            outputTable(table);
        }
    }

    public void outputTableCreated(Table table) {
        outputMessage("Table created:");
        outputTable(table);
    }

    public void outputTableRemoved(String tableName) {
        outputMessage("Table " + tableName + " removed");
    }

    public void outputError(Exception e) {
        outputMessage("ERROR: "+ e.getMessage());
    }
    
    public void outputRow(Row row) {
        outputMessage("Row added " + row.toString());
        //empty
        
    }
    
    public abstract void outputTable(Table table);

    protected abstract void outputMessage(String message);
}
