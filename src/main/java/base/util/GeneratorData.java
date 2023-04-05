package base.util;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.User;

public class GeneratorData {
    private static String email;
    private static String password;
    private static String name;
    public static String generateEmail(){
        return email = RandomStringUtils.random(10)+"@mail.ru";
    }
    public static String generatePassword(){
        return password = RandomStringUtils.random(8);
    }
    public static String generateName(){
        return name = RandomStringUtils.random(4);
    }
    private static void createUserData(){
        generateEmail();
        generatePassword();
        generateName();
    }
    public static User createUser(){
        createUserData();
        return new User(email,password,name);
    }
}
