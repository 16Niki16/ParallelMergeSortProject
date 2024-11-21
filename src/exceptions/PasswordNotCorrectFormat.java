package exceptions;

public class PasswordNotCorrectFormat extends Exception{
    public PasswordNotCorrectFormat(String message){super(message);}
    public PasswordNotCorrectFormat(String message, Throwable clause){super(message, clause);}
}
