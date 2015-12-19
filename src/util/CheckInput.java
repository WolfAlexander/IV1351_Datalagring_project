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
        checkZIPcode(userData.getPostnr());
    }

    private static void checkPnr(String pnr) throws IllegalArgumentException{
        if(pnr.isEmpty())
            throw new IllegalArgumentException("Field 'Social-sec number' cannot be empty!");
        String pnrPattern = "^[1|2][0|9][0-9][0-9][0|1][0-9][0-3][0-9][0-9]{4}$"; //Needs more work(31+ is possible)

        if(!pnr.matches(pnrPattern))
            throw new IllegalArgumentException("Invalid social-sec number! Try again!");
    }

    private static void checkIfOnlyString(String string, String fieldName) throws IllegalArgumentException{
        String onlyStringRegex = "[^0-9]+";
        if(string.isEmpty())
            throw new IllegalArgumentException("Field " + fieldName + " cannot be empty!");

        if(!string.matches(onlyStringRegex))
            throw new IllegalArgumentException("Field " + fieldName + " contains illegal characters. Only strings are permitted.");
    }

    private static void checkZIPcode(String zipCode) throws IllegalArgumentException{
        String zipCodePattern = "[0-9]{5}";
        if(zipCode.isEmpty())
            throw new IllegalArgumentException("Field 'ZIP code' cannot be empty!");
        else if(!zipCode.matches(zipCodePattern))
            throw new IllegalArgumentException("Invalid ZIP code! Please enter ZIP code that contains five digits.");
    }
}