package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Table implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final String name;
    private final List<Type> columnTypes;
    
    public Table(String name, List<Type> columnTypes){
        this.name = name;
        this.columnTypes = Collections.unmodifiableList(columnTypes);
    }
    
    public String getName() {
        return name;
    }

    public List<Type> getColumnTypes() {
        return columnTypes;
    }
}
