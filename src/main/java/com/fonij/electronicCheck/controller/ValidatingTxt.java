package com.fonij.electronicCheck.controller;


import com.fonij.electronicCheck.model.TextBoxType;
import java.util.regex.*;

/**
 * NEED REFACTORING
 */
public class ValidatingTxt {

    private static Pattern pattern;

    public static void validate(String text, TextBoxType type) throws Exceptions {
        setPattern(type);
        if (text.isEmpty())
            throw new Exceptions("Please Fill All TextFields");
        if (!pattern.matcher(text).find())
            throw new Exceptions(getMessage(type));
    }

    private static String getMessage(TextBoxType type) {
        switch (type) {
            case FIRSTNAME:
                return "Invalid FirstName Format";
            case LASTNAME:
                return "Invalid LastName Format";
            case PRICE:
                return "Invalid Price Format";
            case CHECKNUMBER:
                return "Invalid Check Number Format";
            case NATIONALCODE:
                return "Invalid National Code Format";
            case CUSTOMERNUMBER:
                return "Invalid Customer Number Format";
            default:
                return "";
        }
    }

    private static void setPattern(TextBoxType type) {
        switch (type) {
            case FIRSTNAME:
            case LASTNAME:
                pattern = Pattern.compile("^[a-zA-Z\\s]{1,20}$");
                break;
            case PRICE:
                pattern = Pattern.compile("[0-9]{1,15}");
                break;
            case CHECKNUMBER:
                pattern = Pattern.compile("[0-9]{1,6}");
                break;
            case NATIONALCODE:
                pattern = Pattern.compile("[0-9]{10}");
                break;
            case CUSTOMERNUMBER:
                pattern = Pattern.compile("[0-9]{6}");
                break;
        }
    }

}

