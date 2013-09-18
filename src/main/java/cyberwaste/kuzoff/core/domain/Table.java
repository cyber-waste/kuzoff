package cyberwaste.kuzoff.core.domain;

import java.util.Collections;
import java.util.List;

public class Table {
    
    private final String name;
    private final List<Type> columnTypes;
    
    public Table(String name, List<Type> columnTypes){
        this.name = name;
        this.columnTypes = Collections.unmodifiableList(columnTypes);
    }
    
    public String name() {
        return name;
    }

    public List<Type> columnTypes() {
        return columnTypes;
    }
}
