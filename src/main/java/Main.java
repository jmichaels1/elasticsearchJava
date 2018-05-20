import java.io.IOException;
import java.util.ArrayList;

import static elastic.ElasticQueryApi.*;
import static elastic.ConfigElasticSearch.*;
import static elastic.Utilities.printS;

public class Main {

    private static final String INDICE_NAME = "master";



    public static void main(String[] args) throws IOException {

        connectElastic("localhost", 9300);
//        deleteIndex(INDICE_NAME);
//        createIndex(INDICE_NAME);
//        InsertMasterProcess();

//        countAllDocuments(INDICE_NAME);
//        countValueSearch(INDICE_NAME, "descripcion", "profesionales");

//        searchMasterFromQuerywithPhrase(INDICE_NAME, "descripcion", "profesionales")
//                .forEach(item -> printS(item));

//        countAllDocuments(INDICE_NAME);

        ArrayList<String> aValuesAND = new ArrayList<>();
        aValuesAND.add("profesionales");
        aValuesAND.add("exigencias");
        aValuesAND.add("Máster");

        searhMasterWithQuery("AND", "descripcion", aValuesAND)
                .forEach(item -> printS(item));

        ArrayList<String> aValuesOR = new ArrayList<>();
        aValuesOR.add("profesionales");
        aValuesOR.add("exigencias");
        aValuesOR.add("Máster");

//        searhMasterWithQuery("OR", "descripcion", aValuesAND)
//                .forEach(item -> printS(item));

        searhMasterWithQuery("exists", "descripcion", aValuesAND)
                .forEach(item -> printS(item));

        closeConnection();

    }

}