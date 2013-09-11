package cyberwaste.kuzoff.outputmanager;

import java.util.Collection;

import cyberwaste.kuzoff.database.Table;

public class ConsoleOutputManager implements OutputManager {

    @Override
    public void output(Collection<Table> tables) {
        for (Table table : tables) {
            output(table);
        }
    }

    @Override
    public void output(Table table) {
        System.out.println(table.name());
    }
}
