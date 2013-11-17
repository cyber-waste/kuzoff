package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private List<String> columnTypeNames;
    
    public Table() {
    }
    
    public Table(String name, List<String> columnTypeNames){
        this.name = name;
        this.columnTypeNames = Collections.unmodifiableList(columnTypeNames);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setColumnTypeNames(List<String> columnTypeNames) {
        this.columnTypeNames = columnTypeNames;
    }
    
    public List<String> getColumnTypeNames() {
        return columnTypeNames;
    }

    public List<Type> columnTypes() {
        List<Type> columnTypes = new ArrayList<Type>();
        for (String typeName : columnTypeNames) {
            columnTypes.add(Type.createType(typeName));
        }
        return columnTypes;
    }
}
