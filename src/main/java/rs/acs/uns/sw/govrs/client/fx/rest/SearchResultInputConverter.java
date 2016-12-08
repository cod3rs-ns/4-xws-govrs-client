package rs.acs.uns.sw.govrs.client.fx.rest;

import com.gluonhq.connect.converter.InputStreamInputConverter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.SearchResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converts response body to SearchResult.
 */
public class SearchResultInputConverter extends InputStreamInputConverter<SearchResult> {
    @Override
    public SearchResult read() {
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
            Logger.getLogger(SearchResultInputConverter.class.getName()).log(Level.INFO, stringWriter.toString());
            JAXBContext context = JAXBContext.newInstance(SearchResult.class);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            return (SearchResult) unMarshaller.unmarshal(new StringReader(stringWriter.toString()));

        } catch (Exception ex) {
            Logger.getLogger(SearchResultInputConverter.class.getName()).log(Level.SEVERE, "Convert error", ex);
            return null;
        }
    }
}
