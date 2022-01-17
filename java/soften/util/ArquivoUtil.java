package soften.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;


public class ArquivoUtil {

    public static String geraStringAleatoria(String idUsuario) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + idUsuario;
    }

    public static byte[] bufferedToByte(BufferedImage buf) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buf, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            return null;
        }
    }

    public static BufferedImage byteToBuffered(byte[] imageInByte) {

        try {
            if (imageInByte == null) {
                return null;
            }

            
            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bImageFromConvert = ImageIO.read(in);

            return bImageFromConvert;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    public static void gravaArquivo(String caminho, byte[] byteArray) {
        java.io.File file = new java.io.File(caminho);
        FileOutputStream in;
        try {
            in = new FileOutputStream(file);
            in.write(byteArray);
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String geraStringAleatoria() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static File byteToFile(String caminho, String nomeArquivo, byte[] arquivo) {

        File file = new File(caminho, nomeArquivo); //Criamos um nome para o arquivo

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file)); //Criamos o arquivo
            bos.write(arquivo); //Gravamos os bytes lá
            bos.close(); //Fechamos o stream.
        } catch (FileNotFoundException ex) {

            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return file;
    }

    public static byte[] fileToByte(File file) {
        FileInputStream fileInputStream = null;

        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            return bFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void adicionarArquivoParaPastaZip(ZipOutputStream pastaZip, byte[] arquivo, String pathName) throws FileNotFoundException, IOException {
        byte[] buffer = new byte[1024];
        File file = new File(pathName);

        FileOutputStream xmlWriter = new FileOutputStream(file);
        xmlWriter.write(arquivo);
        FileInputStream input = new FileInputStream(file);
        pastaZip.putNextEntry(new ZipEntry(file.getName()));
        int tam;
        while ((tam = input.read(buffer)) > 0) {
            pastaZip.write(buffer, 0, tam);
        }
        pastaZip.closeEntry();
        input.close();
    }

    public static String formatFileSize(double size) {
        String hrSize = null;

        size = size * 1024.0;

        double k = size;
        double m = size / 1024.0;
        double g = ((size / 1024.0) / 1024.0);
        double t = (((size / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else {
            hrSize = dec.format(k).concat(" KB");
        }

        return hrSize;
    }

}
