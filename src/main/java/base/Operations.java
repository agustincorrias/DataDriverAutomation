package base;

public class Operations {

    public boolean isNumeric (String cadena){
        try{
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
