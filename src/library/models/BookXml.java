package library.models;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Di on 11.04.2017.
 */
public class BookXml
{



    public static void generateXML()
    {


        Book book = new Book("","", 0, "");

        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Books");
            doc.appendChild(rootElement);

            //  Book element
            Element bookElement = doc.createElement("Book");
            rootElement.appendChild(bookElement);

            Element fields = doc.createElement("Fields");
            bookElement.appendChild(fields);





            for (Field field:Class.forName("library.models.Book").getDeclaredFields()
                 ) {
                Element elementField = doc.createElement("Field");
                fields.appendChild(elementField);

                Attr attr = doc.createAttribute("field");
                attr.setValue(field.getName());
                elementField.setAttributeNode(attr);
            }

            Element methods = doc.createElement("Methods");
            bookElement.appendChild(methods);


            for (Method method:book.getClass().getMethods()
                    ) {
                Element elementMethod = doc.createElement("Method");
                methods.appendChild(elementMethod);

                Attr attr = doc.createAttribute("method");
                attr.setValue(method.getName());
                elementMethod.setAttributeNode(attr);
            }

            // write the content into xml file
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer =
                    transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result =
                    new StreamResult(new File("books.xml"));
            transformer.transform(source, result);
            // Output to console for testing
            StreamResult consoleResult =
                    new StreamResult(System.out);
            transformer.transform(source, consoleResult);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // root element
    }







}
