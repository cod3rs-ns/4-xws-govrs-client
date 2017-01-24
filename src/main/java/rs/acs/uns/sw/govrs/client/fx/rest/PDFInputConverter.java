package rs.acs.uns.sw.govrs.client.fx.rest;


import com.gluonhq.connect.converter.InputStreamInputConverter;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFInputConverter extends InputStreamInputConverter<Object> {
    private String filePath;

    public PDFInputConverter (String filePath) {
        this.filePath = filePath;
    }
    @Override
    public File read() {
        File f = null;
        try {
            InputStream input = getInputStream();
            byte[] buffer = new byte[4096];
            int n = -1;
            f = new File(filePath);
            OutputStream output = new FileOutputStream(f);
            while ((n = input.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
            output.close();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "PDF download error!", e);
        }

        return f;
    }
}
