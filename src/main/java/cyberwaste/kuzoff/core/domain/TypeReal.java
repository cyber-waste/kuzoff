package cyberwaste.kuzoff.core.domain;

public class TypeReal extends Type{
    
    public TypeReal(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        try{
            Double.parseDouble(val);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getValue(String val) {
        return val;
    }
}
