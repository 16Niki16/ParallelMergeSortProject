package exceptions.custom;

public class EmptyArrayException extends Exception{
    public EmptyArrayException(String message){
        super(message);
    }
    public  EmptyArrayException(String message, Throwable clause){
        super(message, clause);
    }
}
