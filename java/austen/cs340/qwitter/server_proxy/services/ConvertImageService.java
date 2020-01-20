package austen.cs340.qwitter.server_proxy.services;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public class ConvertImageService {

    public ConvertImageService() {}

    public String convertImage(File file) {
        String encodedFile = null;
        try {
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            in.read(bytes);
            encodedFile = new String(Base64.encode(bytes, 0), "UTF-8");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return encodedFile;
    }
}
