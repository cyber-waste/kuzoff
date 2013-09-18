package cyberwaste.kuzoff.core.domain;

public class TypeInt extends Type{
    
    public TypeInt(String name){
        super(name);
    }
    
    public boolean isValid(Value val){
        String valString = val.getValue();
        try{
            Integer.parseInt(valString);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
}
