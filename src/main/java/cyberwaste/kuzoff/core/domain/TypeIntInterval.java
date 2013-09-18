package cyberwaste.kuzoff.core.domain;

public class TypeIntInterval extends Type{


    public TypeIntInterval(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        try{
            int i = val.indexOf("-");
            if(i == -1) return false;
            int valInt1 = Integer.parseInt(val.substring(0,i));
            int valInt2 = Integer.parseInt(val.substring(i+1,val.length()));
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
