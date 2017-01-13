package rs.acs.uns.sw.govrs.client.fx.render;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class Renderer {

    private String name;
    private final String xhtmlRoot = "/render/";
    private final String xhtmlExtension = "-xhtml.xsl";

    public Renderer() {
        super();
    }

    public Renderer(String name) throws FileNotFoundException {
        this.name = name;
    }

    private static Document buildDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        return document;
    }

    public String toHtml(String xml)
            throws TransformerException, IOException, SAXException, ParserConfigurationException, URISyntaxException {

        // Init Transform file, Source file and Output Stream
        final StreamSource transformSource = new StreamSource(initHtmlTransformerFile());
        final TransformerFactory factory = TransformerFactory.newInstance();
        StringWriter writer = new StringWriter();

        // Set Transformer and set parameters
        final Transformer transformer = factory.newTransformer(transformSource);
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Generate XHTML
        transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

        // Transform DOM to HTML and perform transform
        final DOMSource source = new DOMSource(buildDocument(xml));
        final StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);

        return writer.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private File initHtmlTransformerFile() throws FileNotFoundException, URISyntaxException {
        URL url = this.getClass().getResource(xhtmlRoot + this.name + xhtmlExtension);
        return new File(url.toURI());
    }

}
