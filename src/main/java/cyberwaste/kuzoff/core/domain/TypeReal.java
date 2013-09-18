package cyberwaste.kuzoff.core.domain;

public class TypeReal extends Type{
    
    public TypeReal(String name){
        super(name);
    }
    
    public boolean isValid(Value val){
        String valString = val.getValue();
        try{
            Double.parseDouble(valString);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
