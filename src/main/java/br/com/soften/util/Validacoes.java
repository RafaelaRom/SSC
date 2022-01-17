/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Renata Rafa
 */
public class Validacoes {

    public static boolean isValidateEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean validaCEP(String cep) {

        Pattern pattern
                = Pattern.compile("[0-9]{5}-[0-9]{3}");

        Matcher m = pattern.matcher(cep);

        return m.matches();

    }
}
