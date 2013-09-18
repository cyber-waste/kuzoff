package cyberwaste.kuzoff.core.domain;

public class TypeChar extends Type{
    
    public TypeChar(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        if(val.length() == 1) return true;
        else return false;
    }

    @Override
    public String getValue(String val) {
        return val;
    }
}
