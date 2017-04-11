package library.models;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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


        Book book1 = new Book("Schildt","Intro to Java", 2017, "1241241ada");
        Book book2 = new Book("Snowden","How to hack Pentagon", 2015, "54524dfh");

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

                Attr attrAccess = doc.createAttribute("isAccessible");
                attrAccess.setValue(Boolean.toString(field.isAccessible()));
                elementField.setAttributeNode(attrAccess);

                Attr attrType = doc.createAttribute("type");
                attrType.setValue(field.getType().getName());
                elementField.setAttributeNode(attrType);
            }






            Element methods = doc.createElement("Methods");
            bookElement.appendChild(methods);


            for (Method method:book1.getClass().getMethods()
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

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

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
