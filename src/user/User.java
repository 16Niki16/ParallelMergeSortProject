package user;

import exceptions.PasswordNotCorrectFormat;
import login.Login;

public record User(String username, String password) {
    public static User of(String line) throws PasswordNotCorrectFormat {
        String[] splitt = line.split(" ");
        Login.PasswordValidation(splitt[1]);
        return new User(splitt[0], splitt[1]);
    }

}
