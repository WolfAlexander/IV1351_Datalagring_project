package model;

import DTO.UserData;

public class CheckInput {
    /**
     * This method checks if input from user is a valid one
     * @param userData of type DTO.UserData where data is stored
     * @throws IllegalArgumentException is any input is invalid
     */
    public static void checkUserDataInput(UserData userData) throws IllegalArgumentException{
        checkPnr(userData.getPnr());
        checkIfOnlyString(userData.getEnamn());
        checkIfOnlyString(userData.getFnamn());
        checkIfOnlyString(userData.getPostort());
    }

    private static void checkPnr(String pnr) throws IllegalArgumentException{
        String firstPattern = "";
        String secondPattern = "";
    }

    private static void checkIfOnlyString(String string) throws IllegalArgumentException{
        String pattern = "";
    }

    private static void checkIfValidEmail(String email) throws IllegalArgumentException{
        String pattern = "^[_a-z0-9-\\+]+(\\.[_a-z0-9]+)*@[a-z0-9-]+(\\-[])";
    }
}