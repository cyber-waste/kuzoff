package cyberwaste.kuzoff.core.domain;

public class TypeChar extends Type{
    
    public TypeChar(String name){
        super(name);
    }
    
    public boolean isValid(Value val){
        String valString = val.getValue();
        if(valString.length() == 1) return true;
        else return false;
    }
}
