package login;

import exceptions.PasswordNotCorrectFormat;

public class Login {

    public static void PasswordValidation(String password) throws PasswordNotCorrectFormat {
        if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$"))
            throw new PasswordNotCorrectFormat("Password is not in the correct format!");
    }
}
