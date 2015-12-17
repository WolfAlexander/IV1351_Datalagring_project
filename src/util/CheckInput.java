package util;

import DTO.UserData;

public class CheckInput {
    /**
     * This method checks if input from user is a valid one
     * @param userData of type DTO.UserData where data is stored
     * @throws IllegalArgumentException is any input is invalid
     */
    public static void checkUserDataInput(UserData userData) throws IllegalArgumentException{
        checkPnr(userData.getPnr());
        checkIfOnlyString(userData.getEnamn(), "Firstname");
        checkIfOnlyString(userData.getFnamn(), "Secondname");
        checkIfOnlyString(userData.getPostort(), "City");
    }

    private static void checkPnr(String pnr) throws IllegalArgumentException{
        if(pnr.isEmpty())
            throw new IllegalArgumentException("Field 'Social-sec number' cannot be empty!");
        String firstPattern = "";
        String secondPattern = "";
    }

    private static void checkIfOnlyString(String string, String fieldName) throws IllegalArgumentException{
        String onlyStringRegex = "[^0-9]+";
        if(string.isEmpty())
            throw new IllegalArgumentException();

        if(!string.matches(onlyStringRegex))
            throw new IllegalArgumentException("Field " + fieldName + " contains illegal characters. Only string are ");
    }

    private static void checkIfValidEmail(String email) throws IllegalArgumentException{
        String pattern = "^[_a-z0-9-\\+]+(\\.[_a-z0-9]+)*@[a-z0-9-]+(\\-[])";
    }
}