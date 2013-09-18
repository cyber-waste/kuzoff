package cyberwaste.kuzoff.io;

import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Type;

public class ConsoleOutputManager extends OutputManager {
    
    @Override
    public void outputMessage(String message) {
        System.err.println(message);
    }
    
    @Override
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
}
