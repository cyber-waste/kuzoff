package cyberwaste.kuzoff.core.domain;

public abstract class Type {
    
    private final String name;

    public Type(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
    
    public static Type createType(String name){
        if("char".equals(name)) return new TypeChar(name);
        else if("int".equals(name)) return new TypeInt(name);
        else if("real".equals(name)) return new TypeReal(name);
        else return null;
    }
    
    public abstract boolean isValid(Value val);
}
