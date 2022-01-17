package soften.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;

public class CriptografaSenha {

    private static BasicTextEncryptor bte;
    private static final String CODIFICACAO = "$*0-(Y7T.^~Kis,Tm<@0$3_-";

    public static String md5s(String senha) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        sen = hash.toString(16);
        return sen;
    }

    public static String criptografa(String pass) {

        bte = new BasicTextEncryptor();

        bte.setPassword(CODIFICACAO);

        return bte.encrypt(pass);

    }

    public static String desCriptografa(String pass) throws EncryptionOperationNotPossibleException {

        bte = new BasicTextEncryptor();

        bte.setPassword(CODIFICACAO);

      
        return bte.decrypt(pass);

    }

    public static boolean isSenhaCriptografada(String senha) {
        bte = new BasicTextEncryptor();
        try {
            bte.setPassword(CODIFICACAO);
            bte.decrypt(senha);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static String gerarSenha() {
        String senha = new RandPass(new char[]{'a', 'b', 'c', 'd', 'e', 'F', 'G', 'H', 'I', 'J', 'L', 'm', 'n', 'o', 'p', 'q', 'r', 'S', 'T', 'U', 'V', 'x', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}).getPass(8);
        return senha;
    }

}

class RandPass {

    char[] caracteresSenha = null;

    RandPass(char[] caracteresSenha) {
        this.caracteresSenha = caracteresSenha;
    }

    String getPass(int numeroCaracteres) {
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < numeroCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteresSenha.length);
            senha.append(caracteresSenha[posicao]);

        }
        return senha.toString();
    }
}
