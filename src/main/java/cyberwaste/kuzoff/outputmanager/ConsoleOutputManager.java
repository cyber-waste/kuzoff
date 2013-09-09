package cyberwaste.kuzoff.outputmanager;

import cyberwaste.kuzoff.database.Table;

public class ConsoleOutputManager implements OutputManager{
    
    public ConsoleOutputManager(){
        
    }
    
    @Override
    public void show(Table table) {
        System.out.println(table.getName());
    }

}
