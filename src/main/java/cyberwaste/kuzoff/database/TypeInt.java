package cyberwaste.kuzoff.database;

public class TypeInt extends Type{
    
    public TypeInt(String name){
        super(name);
    }
    
    public boolean isValid(Value val){
        String valString = val.getValue();
        try{
            int valInt = Integer.parseInt(valString);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
}
