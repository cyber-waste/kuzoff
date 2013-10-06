package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public abstract class Type implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final String name;

    public Type(String name) {
        this.name = name;
    }
    
    public static Type createType(String name){
        if("char".equals(name)) return new TypeChar(name);
        else if("int".equals(name)) return new TypeInt(name);
        else if("real".equals(name)) return new TypeReal(name);
        else if("textfile".equals(name)) return new TypeTextFile(name);
        else if("intinterval".equals(name)) return new TypeIntInterval(name);
        else return null;
    }

    public String getName() {
        return name;
    }
    
    public abstract boolean isValid(String val);
    public abstract String getValue(String val);
}
