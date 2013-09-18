package cyberwaste.kuzoff.core.domain;

public class TypeInt extends Type{
    
    public TypeInt(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        try{
            Integer.parseInt(val);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getValue(String val) {
        return val;
    }
}
