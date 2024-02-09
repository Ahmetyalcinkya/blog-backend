package com.blog.BlogBackend.utils;

import com.blog.BlogBackend.exceptions.BlogException;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlogValidations {

    public static void checkString(String value, String field, int length){
        if(value == null) throw new BlogException(field + " length cannot be null or empty!", HttpStatus.BAD_REQUEST);
        if(value.length() <= 3 || value.length() > length ) throw  new BlogException(field + " length cannot be bigger than " + length + " and cannot smaller than 3!",
                HttpStatus.BAD_REQUEST);
    }
    public static boolean checkEmail(String email){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean checkPassword(String password){
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public static boolean checkUserPicture(String pp){
        String regex = "^(http|https):\\/\\/[^\\s/$.?#].[^\\s]*\\.(jpg|jpeg|png|gif)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pp);
        return matcher.matches();
    }
    public static void checkContent(String content){
        if(content == null) throw new BlogException("Content length cannot be null or empty!", HttpStatus.BAD_REQUEST);
        if(content.length() <= 3 || content.length() > 250 ) throw new BlogException("Content length must be bigger than 250!", HttpStatus.BAD_REQUEST);
    }
}
