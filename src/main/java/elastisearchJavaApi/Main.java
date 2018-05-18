package elastisearchJavaApi;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static elastisearchJavaApi.CountService.getBoolQueryCount;
import static elastisearchJavaApi.CountService.getMatchAllQueryCount;
import static elastisearchJavaApi.CountService.getPhraseQueryCount;
import static elastisearchJavaApi.DataService.getBoolQueryData;
import static elastisearchJavaApi.DataService.getMatchAllQueryData;
import static elastisearchJavaApi.DataService.getPhraseQueryData;
import static elastisearchJavaApi.IngestService.ingest;
import static elastisearchJavaApi.Utilities.printS;
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
        String sesionID = "";
        String date = "";
        String asignatura = "Asign";
        String contenido = "contenido";
        String descripcion = "decripcion";

        int asigID = 0;
        String json = "";

        for (int i = 0; i < 30; i++) {
            sesionID = String.valueOf(i+1);
            date = LocalDate.now().plusDays(i+1).format(DateTimeFormatter.BASIC_ISO_DATE);
            ++asigID;
            if (asigID > 5) asigID = 1;
            asignatura += asigID;
            contenido += asigID;
            descripcion += asigID;
            printS("\nIngestService response::: " + ingest("dia", generarJson(sesionID, date,
                    asignatura, contenido, descripcion)));
        }

    }

    private static String generarJson(String sesionID, String date,
                                      String asignatura, String contenido,
                                      String descripcion) {
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

    public static void main(String[] args) throws IOException {

        initVariables();

//        deleteIndex(client);
//        insertSesionInitials();

        count();
//        data();
//        deleted();

        client.close();

        matchQuery("Asignatura", "Asignatura1");

    }

}