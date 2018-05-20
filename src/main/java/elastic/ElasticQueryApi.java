package elastic;

import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

import static elastic.ConfigElasticSearch.client;
import static elastic.Utilities.aMaster;
import static elastic.Utilities.getNewMasterJson;
import static elastic.Utilities.printS;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Created by Michael
 */
public class ElasticQueryApi {

    private static QueryBuilder query;
    private static long count;

    private static ArrayList<String> aList;
    private static SearchHit[] hits;

    //variables for query format
    private static String operator_query;
    private static String field_query;
    private static ArrayList<String> values_query;
    private static StringBuilder sb;


    /**
     * InsertMasterProcess
     */
    public static void InsertMasterProcess() {
        for (int i = 0; i < aMaster.size(); i++) printS("\nInsert Master : " +
                    insert(getNewMasterJson(i)));
    }

    /**
     * InsertMasterProcess Service
     * 1 doc
     *
     * @param doc
     * @return
     */
    public static String insert(String doc) {
        return client
                .prepareIndex("master", "doc")
                .setSource(doc, XContentType.JSON)
                .get()
                .getResult()
                .toString();
    }

    /**
     * count all data
     *
     * @param indiceName
     */
    public static void countAllDocuments(String indiceName) {
        query = matchAllQuery();
        count = client
                .prepareSearch(indiceName)
                .setQuery(query).setSize(0)
                .execute()
                .actionGet()
                .getHits()
                .getTotalHits();
        printS("Count All Documents : " + count);
    }


    /**
     * countValueSearch
     *
     * @return
     */
    public static void countValueSearch(String indiceName, String propertyName,
                                        String valor) {
        long count = 0;
        query = matchPhraseQuery(propertyName, valor);
        count = client
                .prepareSearch(indiceName)
                .setQuery(query)
                .setSize(0)
                .execute()
                .actionGet()
                .getHits()
                .getTotalHits();
        printS("Count Value Search : " + count);
    }

    /**
     * get Phrase QueryData
     *
     * @return
     */
    public static List<String> searchMasterFromQuerywithPhrase(String indiceName, String propertyName,
                                                               String phrase) {
        query = matchPhraseQuery(propertyName, phrase);
        aList = new ArrayList<>();
        hits = client
                .prepareSearch(indiceName)
                .setQuery(query)
                .execute()
                .actionGet()
                .getHits()
                .getHits();
        for (SearchHit hit : hits) aList.add(hit.getSourceAsString());
        return aList;
    }

    /**
     * sear hMaster Query Special
     * @return
     */
    public static List<String> searhMasterWithQuery(String operator, String field,
                                                    ArrayList<String> aValues) {
        aList = new ArrayList<>();
        operator_query = operator;
        field_query = field;
        values_query = aValues;
        query = queryStringQuery(generarQuery());//        query = queryStringQuery("_exists_:descripcion");
        hits = client
                .prepareSearch()
                .setIndices("master")
                .setTypes("doc")
                .setQuery(query)
                .execute()
                .actionGet()
                .getHits()
                .getHits();
        for (SearchHit hit : hits) aList.add(hit.getSourceAsString());
        return aList;
    }

    /**
     * generar Query
     * @return
     */
    private static String generarQuery() {
        String s = "";
        switch (operator_query){
            case "AND":
                s = genQueryAND();
                break;
            case "OR":
                s = genQueryOR();
                break;
            case "exists" :
                s = genQueryExist();
                break;
        }
        return s;
    }

    private static String genQueryExist() {
        sb = new StringBuilder("( _exists_:");
        sb.append(field_query + ")");
        System.out.println("String_query exists : " + sb);
        return sb.toString();
    }

    /**
     * genQueryOR
     */
    private static String genQueryOR() {
        sb = new StringBuilder("(" + field_query + " ");
        for (int i = 0; i < values_query.size(); i++) {
            if (i < values_query.size()-1)
                sb.append(values_query.get(i) + " OR ");
            else
                sb.append(values_query.get(i) + ")");
        }
        System.out.println("String_query or : " + sb);
        return  sb.toString();
    }

    /**
     * genQueryOR
     */
    private static String genQueryAND() {
        sb = new StringBuilder("(" + field_query + " ");
        for (int i = 0; i < values_query.size(); i++) {
            if (i < values_query.size()-1)
                sb.append(values_query.get(i) + " AND ");
            else
                sb.append(values_query.get(i) + ")");
        }
        System.out.println("String_query and : " + sb);
        return  sb.toString();
    }

}
