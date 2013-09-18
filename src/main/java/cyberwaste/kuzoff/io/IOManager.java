package cyberwaste.kuzoff.io;

import java.io.IOException;
import java.util.Collection;

import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.io.command.Command;

public abstract class IOManager {
    
    protected abstract Command getNextCommand() throws IOException;
    
    protected abstract boolean hasMoreCommands();
    
    public abstract void outputMessage(String message);
    
    protected void start() {
        while (hasMoreCommands()) {
            try {
                Command command = getNextCommand();
                
                if (command != null) {
                    command.execute(this);
                }
            } catch (Exception e) {
                outputError(e);
            }
        }
    }
    
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
    
    public void outputRow(Row row) {
        outputMessage("ROW: " + row.toString());
    }
    
    public void outputTable(Table table) {
        StringBuilder tableInfo = new StringBuilder();
        tableInfo.append("table ").append(table.name()).append(" (");
        
        boolean needComaBefore = false;
        for (Type columnType : table.columnTypes()) {
            if (needComaBefore) {
                tableInfo.append(", ");
            } else {
                needComaBefore = true;
            }
            
            tableInfo.append(columnType.name());
        }
        
        tableInfo.append(")");
        
        System.out.println(tableInfo.toString());
    }
    
    private void outputError(Exception e) {
        outputMessage("ERROR: "+ e.getMessage());
    }
}
