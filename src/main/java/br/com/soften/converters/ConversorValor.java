/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.converters;

/**
 *
 * @author Renata Rafa
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@Deprecated 
@FacesConverter(value = "conversorValor")
public class ConversorValor implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if ((value == null) || (value.trim().isEmpty())) {
            return null;
        } else {
            try {
                if (value.contains(".")) {

                    char[] valor = value.toCharArray();
                    int indice1 = value.length() - 3;
                    Character x = value.charAt(indice1);
                    if (x.equals('.')) {
                        valor[indice1] = ',';
                    }
                    int indice2 = value.length() - 4;
                    x = value.charAt(indice2);
                    if (x.equals('.')) {
                        valor[indice1] = ',';
                    }
                    value = new String(valor);
                    if (value.contains(",")) {
                        value = value.replaceAll(Pattern.quote("."), "");
                        String novoValor = value.replace(",", ".");
                        return new BigDecimal(novoValor);
                    }
                }
            } catch (Exception e) {
                throw new ConverterException("Valor inválido");
            }

            try {
                value = value.replaceAll(Pattern.quote("."), "");
                String novoValor = value.replace(",", ".");
                return new BigDecimal(novoValor);

            } catch (Exception e) {
                throw new ConverterException("Valor inválido");
            }
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if ((value == null) || (value.toString().trim().isEmpty())) {
                return "0,0000";
            } else {
                NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
                numberFormat.setMinimumFractionDigits(2);
                numberFormat.setMaximumFractionDigits(4);
                String retorno = numberFormat.format(new BigDecimal(value.toString()));

                return retorno;
            }
        } catch (Exception e) {
            return "";

        }
    }

}
