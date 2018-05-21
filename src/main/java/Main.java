import java.io.IOException;
import java.util.ArrayList;

import static elastic.ElasticJavaQueryApi.*;
import static elastic.ConfigElasticSearch.*;
import static elastic.Utilities.printS;

public class Main {

    private static final String INDICE_NAME = "master";


    public static void main(String[] args) throws IOException {

        connectElastic("localhost", 9300);
//        createIndex(INDICE_NAME);
//        InsertMasterProcess();
//        deleteIndex(INDICE_NAME);

        printS("Exer : Mostrar la Cantidad de todos los documentos");
        countAllDocuments(INDICE_NAME);

        System.out.println("\n");

        printS("Exer : Mostrar la Cantidad de todos los documentos que contengan 'profesionales' en el campo descripcion");
        countValueSearch(INDICE_NAME, "descripcion", "profesionales");

        System.out.println("\n");

        printS("Exer : Mostrar los documentos que contengan 'profesionales' en el campo descripcion");
        searchMasterFromQuerywithPhrase(INDICE_NAME, "descripcion", "profesionales")
                .forEach(item -> printS(item));

        System.out.println("\n");

        printS("Exer : Mostrar los documentos con masterID 'M01'");
        searchSpecific(INDICE_NAME, "masterID", "M01")
                .forEach(item -> printS(item));
        System.out.println("\n");

        printS("Exer : Mostrar los documentos de master de Ingeniería Informática");
        searchSpecific(INDICE_NAME, "master", "Ingeniería Informática")
                .forEach(item -> printS(item));
        System.out.println("\n");

        printS("Exer : Mostrar los documentos que contengan 'profesionales', 'exigencias' y 'Máster' en la descripcion");
        ArrayList<String> aValuesAND = new ArrayList<>();
        aValuesAND.add("profesionales");
        aValuesAND.add("exigencias");
        aValuesAND.add("Máster");
        searhMasterWithQuery("AND", "descripcion", aValuesAND)
                .forEach(item -> printS(item));
        System.out.println("\n");

        printS("Exer : Mostrar los documentos que contengan 'profesionales', 'exigencias' o 'Máster' en la descripcion");
        ArrayList<String> aValuesOR = new ArrayList<>();
        aValuesOR.add("profesionales");
        aValuesOR.add("exigencias");
        aValuesOR.add("Máster");
        searhMasterWithQuery("OR", "descripcion", aValuesOR)
                .forEach(item -> printS(item));
        System.out.println("\n");

        printS("Exer : Mostrar los documentos que exista su descripcion");
        searhMasterWithQuery("exists", "descripcion", null)
                .forEach(item -> printS(item));
        System.out.println("\n");

        printS("Exer : Mostrar los documentos que contenga 'foramcion' en descripcion, utilizando fuzziness");
        FuzzinessSearch("fuzziness" ,INDICE_NAME, "descripcion",  "foramcion")
                .forEach(item -> printS(item));
        System.out.println("\n");

        printS("Exer : Mostrar los documentos que contenga 'Barcelona' y 'multidisciplinar' en descripcion, con proximidad 3 como max");
        ArrayList<String> aValuesProx = new ArrayList<>();
        aValuesProx.add("Barcelona");
        aValuesProx.add("multidisciplinar");
        ProximitySearch("proximity", INDICE_NAME, "descripcion", aValuesProx, 3)
                .forEach(item -> printS(item));

        closeConnection();

    }

}