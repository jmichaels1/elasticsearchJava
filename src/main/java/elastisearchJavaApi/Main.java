package elastisearchJavaApi;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static elastisearchJavaApi.CountService.getPhraseQueryCount;
import static elastisearchJavaApi.DataService.getBoolQueryData;
import static elastisearchJavaApi.DataService.getMatchAllQueryData;
import static elastisearchJavaApi.DataService.getPhraseQueryData;
import static elastisearchJavaApi.IngestService.ingest;
import static elastisearchJavaApi.Utilities.printS;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public class Main {

    public static Main main;
    public static Client client;
    public static ESManager esManager;

    /**
     * inicializa variables
     */
    private static void initVariables() {
        main = new Main();
        esManager = new ESManager();
        client = esManager.getClient("localhost", 9300).get();
    }

    /**
     * elimina index
     * @param client
     */
    private static void deleteIndex(Client client) {
        DeleteIndexResponse deleteResponse = client.admin().indices()
                .delete(new DeleteIndexRequest("twitter")).actionGet();
    }


    private static void insertSesionInitials() {
        StringBuilder sesionID;
        StringBuilder date;
        StringBuilder asignatura;
        StringBuilder contenido;
        StringBuilder descripcion;

        int asigID = 0;
        String json = "";

        for (int i = 0; i < 30; i++) {
            sesionID = new StringBuilder(String.valueOf(i+1));
            date = new StringBuilder(LocalDate.now().plusDays(i+1)
                    .format(DateTimeFormatter.BASIC_ISO_DATE));
            ++asigID;
            if (asigID > 5) asigID = 1;
            asignatura = new StringBuilder("Asignatura"+asigID);
            contenido = new StringBuilder("Contenido"+asigID);
            descripcion = new StringBuilder("Descripcion"+asigID);
            printS("\nIngestService response::: " + ingest("dia", generarJson(sesionID, date,
                    asignatura, contenido, descripcion)));
        }

    }

    private static String generarJson(StringBuilder sesionID, StringBuilder date,
                                      StringBuilder asignatura, StringBuilder contenido,
                                      StringBuilder descripcion) {
        return "{" +
                "\"sesionID\":\""+sesionID+"\"," +
                "\"date\":\""+date+"\"," +
                "\"asignatura\":\""+asignatura+"\"," +
                "\"contenido\":\""+contenido+"\"," +
                "\"descripcion\":\""+descripcion+"\"" +
                "}";
    }

    private static void deleted() {
        //        // Delete
//        // delete one record by id
//        printS("delete by id " + delete("AVSMh1LBWlqOklhqtVNs"));
//        //delete record by query
//        printS("delete by query " + deleteByQuery("samuel"));
    }

    private static void data() {
        printS("\ngetMatchAllQueryData from ES::: ");
        getMatchAllQueryData().forEach(item -> System.out.println(item));

        printS("\ngetBoolQueryData from ES::: ");
        getBoolQueryData().forEach(item -> System.out.println(item));

        printS("\ngetPhraseQueryData from ES::: ");
        getPhraseQueryData().forEach(item -> System.out.println(item));
    }

    private static void count() {
//        printS("\ngetMatchAllQueryCount from ES::: " + getMatchAllQueryCount());
//        printS("\ngetBoolQueryCount from ES::: " + getBoolQueryCount());
        printS("\ngetPhraseQueryCount from ES::: " + getPhraseQueryCount());
    }


    /**
     * create and config indice
     * @param indexName
     */
    private static void createIndex(String indexName) throws IOException {

        client.admin().indices().preparePutMapping(indexName)
                .setType("_default")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("index_analyzer", "autocomplete")
                        .field("search_analyzer", "spanish")
                        .endObject());
    }

    public static void main(String[] args) throws IOException {

        initVariables();

//        createIndex("sesion");

//        deleteIndex(client);
//        insertSesionInitials();

//        count();
//        data();
//        deleted();
        System.out.println("matchQuery : " + matchQuery("Asignatura", "Asignatura1"));

        client.close();

    }

}