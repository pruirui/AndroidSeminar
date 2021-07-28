package demo.util;

import java.util.regex.Pattern;

public class format {

    public boolean isRightPhone (String phone){
        return Pattern.matches("^1[3-9]\\d{9}$", phone);
    }

    public boolean isRightPass(String password) {
        return Pattern.matches(" ^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).*$ ", password);
    }

}
