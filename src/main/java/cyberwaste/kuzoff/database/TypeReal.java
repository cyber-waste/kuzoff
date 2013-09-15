package cyberwaste.kuzoff.database;

public class TypeReal extends Type{
    
    public TypeReal(String name){
        super(name);
    }
    
    public boolean isValid(Value val){
        String valString = val.getValue();
        try{
            double valDouble = Double.parseDouble(valString);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
