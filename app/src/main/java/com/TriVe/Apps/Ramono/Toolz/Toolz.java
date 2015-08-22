package com.TriVe.Apps.Ramono.Toolz;


import java.util.ArrayList;
import java.util.List;

/**
 * <b>Toolz  Compilation.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class Toolz
{

    public static boolean isPhoneNumbervalid(String phoneNumber)
    {
        List<Character> TheNumber = new ArrayList<>();

        for (char c : phoneNumber.toCharArray())
        {
            if (Character.isDigit(c))
                TheNumber.add(c);
        }

        if (TheNumber.get(0).equals('0') && TheNumber.size() == 10)
            return true;
        else
            return false;

    }

}
