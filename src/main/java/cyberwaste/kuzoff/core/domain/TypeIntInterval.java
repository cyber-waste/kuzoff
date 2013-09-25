package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class TypeIntInterval extends Type implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public TypeIntInterval(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        try{
            int i = val.indexOf("-");
            if(i == -1) return false;
            Integer.parseInt(val.substring(0,i));
            Integer.parseInt(val.substring(i+1,val.length()));
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
