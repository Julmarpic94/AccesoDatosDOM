/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ad_practica_1_domxml;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class AD_Practica_1_DOMXML {

    Document documento;// Crea un documento para almacenar el documento

    public int abrirXMLaDOM(File archivoXml) throws SAXException, IOException {
        try {
            System.out.println("Abriendo XML: Books");
            DocumentBuilderFactory docuFactory = DocumentBuilderFactory.newDefaultInstance();
            // Ignorar espacios en blanco y comenarios
            docuFactory.setIgnoringComments(true);
            docuFactory.setIgnoringElementContentWhitespace(true);
            // Generar el DOM
            DocumentBuilder builder;
            builder = docuFactory.newDocumentBuilder();
            documento = builder.parse(archivoXml);// par que doc aputne al arbol DOM
            System.out.println("0");
            return 0; //si funciona

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(AD_Practica_1_DOMXML.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("-1");
            return -1;
        }
    }

    public void recorrerImprimirDOM() {
        Node nodo = null;
        Node raiz = documento.getFirstChild(); // Almaceno el nodo rais del documento
        NodeList listBooks = raiz.getChildNodes(); // almacenar la lista de libros

        for (int i = 0; i < listBooks.getLength(); i++) {
            nodo = listBooks.item(i); // nodo para iterar sobre los libros del archivo
            if (nodo.getNodeType() == Node.ELEMENT_NODE)// Miramos si el nodo iterado es un NODO
            {
                System.out.print("Libro: ");
            }
            NamedNodeMap atributos = nodo.getAttributes();//obtenemos los atributos con nodemap
            if (atributos != null) {
                for (int j = 0; j < atributos.getLength(); j++) {
                    Node atributo = atributos.item(j); //Creamos un nodo y le atribuimos un atributo
                    System.out.println(atributo.getNodeName() + ": " + atributo.getNodeValue());
                }
            }

            // Recorrer los hijos de cada libro
            NodeList listaHijos = nodo.getChildNodes();
            for (int j = 0; j < listaHijos.getLength(); j++) {
                Node hijo = listaHijos.item(j);
                if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                    //Mostramos nombre y contenido
                    System.out.println(hijo.getNodeName() + ": " + hijo.getTextContent());
                }
            }
            System.out.println("========================================================");
        }
    }

    public void insertarLibroDOM() {

        Scanner scanner = new Scanner(System.in);
        // Capturar una variable String
        System.out.println("Introduce los datos de nuevo Libro");
        System.out.print("Id del Libro: ");
        String id = scanner.nextLine();
        System.out.print("Titulo: ");
        String title = scanner.nextLine();
        System.out.print("Autor: ");
        String author = scanner.nextLine();
        System.out.print("Genero: ");
        String genre = scanner.nextLine();
        System.out.print("Precio: ");
        String price = scanner.nextLine();
        System.out.print("Fecha de publicación (DD-MM-AAAA): ");
        String publiDate = scanner.nextLine();
        System.out.print("Descripcion:  ");
        String description = scanner.nextLine();

        scanner.close();
        System.out.println("Anadiendo libro: " + title + ".....");
        // Crea los nodos y anadir al padre padre
        //Titulo title
        Node nTitle = documento.createElement("title");
        Node nTitle_text = documento.createTextNode(title);
        nTitle.appendChild(nTitle_text);
        // Autor (Author)
        Node nAuthor = documento.createElement("author");
        Node nAuthor_text = documento.createTextNode(author);
        nAuthor.appendChild(nAuthor_text);
        // Genero (genero)
        Node nGenre = documento.createElement("genre");
        Node nGenre_text = documento.createTextNode(genre);
        nGenre.appendChild(nGenre_text);
        // Precio (price)
        Node nPrice = documento.createElement("price");
        Node nPrice_text = documento.createTextNode("" + price);
        nPrice.appendChild(nPrice_text);
        // Publicacion (publis)
        Node nPublis = documento.createElement("publish");
        Node nPublis_text = documento.createTextNode(publiDate);
        nPublis.appendChild(nPublis_text);
        // Description
        Node nDescrip = documento.createElement("description");
        Node nDescrip_text = documento.createTextNode(description);
        nDescrip.appendChild(nDescrip_text);

        //Creamos el libro con el atributo ya anadimos todo
        Node nLibro = documento.createElement("book");
        ((Element) nLibro).setAttribute("id", id);
        nLibro.appendChild(nAuthor);
        nLibro.appendChild(nTitle);
        nLibro.appendChild(nGenre);
        nLibro.appendChild(nPrice);
        nLibro.appendChild(nPublis);
        nLibro.appendChild(nDescrip);
        // Anado el nodo a la raiz
        nLibro.appendChild(documento.createTextNode("\n"));
        Node raiz = documento.getFirstChild();
        raiz.appendChild(nLibro);
        System.out.println("Libro insertado");
    }

    public void borrarLibro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el Titulo del libro a borrar: ");
        String title = scanner.nextLine();
        System.out.println("Borrando Libro....");
        scanner.close();
        //Cogemos la raiz
        Node raiz = documento.getDocumentElement();
        NodeList listaNodo = documento.getElementsByTagName("title");
        Node nodo;
        for (int i = 0; i < listaNodo.getLength(); i++) {
            nodo = listaNodo.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                //nodo.getParentNode().removeChil(n1); // borra <titulo> title<Titulo, pero deja libor y autor
                // por ello ingresamos ChildNodes y ParentesNodes
                if (nodo.getChildNodes().item(0).getNodeValue().equals(title)) {
                    System.out.println("Borramos el nodo <Libro> con título " + title);
                    nodo.getParentNode().getParentNode().removeChild(nodo.getParentNode());
                }
            }
        }
        System.out.println("Libro Borrado");
    }

    public void guardarDOMArchivo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce la ruta para guardar");
        String ruta = scanner.nextLine();
        scanner.close();
        if (ruta != null) {
            File archivoDestino = new File(ruta);
            try {
                //si el file no es un directoria y o no existe y crearé uno nuevo O existe y puedo sobre escribir
                if (!archivoDestino.isDirectory() && (archivoDestino.createNewFile() || archivoDestino.canWrite())) {
                    try {
                        Source src = new DOMSource(documento); // Definimos origen
                        StreamResult srt = new StreamResult(new File(ruta));
                        Transformer transformador = TransformerFactory.newInstance().newTransformer();

                        //Opción para identar el archivo
                        transformador.setOutputProperty(OutputKeys.INDENT, "yes");
                        transformador.transform(src, (javax.xml.transform.Result) srt);
                        System.out.println("Archivo creado con exito");
                    } catch (TransformerException e) {
                        System.out.println(e);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) throws SAXException, IOException {
        AD_Practica_1_DOMXML practica = new AD_Practica_1_DOMXML();
        File archivoXml = new File("books.xml");
        practica.abrirXMLaDOM(archivoXml);
        practica.recorrerImprimirDOM();
        //practica.insertarLibroDOM();
        //practica.recorrerImprimirDOM();
        //practica.borrarLibro();
        //practica.recorrerImprimirDOM();
        practica.guardarDOMArchivo();

    }
}
