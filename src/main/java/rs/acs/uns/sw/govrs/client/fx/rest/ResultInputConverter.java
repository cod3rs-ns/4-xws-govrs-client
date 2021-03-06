package rs.acs.uns.sw.govrs.client.fx.rest;


import com.gluonhq.connect.converter.InputStreamInputConverter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converts response body to SearchResult.
 */
public class ResultInputConverter extends InputStreamInputConverter<Object> {
    private Class<?> returnObjectType;

    public ResultInputConverter(Class<?> classType) {
        returnObjectType = classType;
    }

    @Override
    public Object read() {
        try (StringWriter stringWriter = new StringWriter()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream()));
                 BufferedWriter writer = new BufferedWriter(stringWriter)) {
                boolean firstWrite = true;
                String line;
                while ((line = reader.readLine()) != null) {
                    if (firstWrite) {
                        firstWrite = false;
                    } else {
                        writer.newLine();
                    }
                    writer.write(line);
                }
            }
            //Logger.getLogger(getClass().getName()).log(Level.INFO, stringWriter.toString());
            JAXBContext context = JAXBContext.newInstance(returnObjectType);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            return unMarshaller.unmarshal(new StringReader(stringWriter.toString()));

        } catch (Exception ex) {
            Logger.getLogger(LawInputConverter.class.getName()).log(Level.SEVERE, "Convert error", ex);
            return null;
        }
    }
}
