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
    private static int numProximity = 0;
    private static StringBuilder sb;

    private static int numQuery = 0;
    private static String s_aux;


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
     * busqueda especfica
     * por el campo
     * @param indice
     * @param field
     * @param value
     */
    public static ArrayList<String> searchSpecific(String indice, String field,
                                                   String value){
        numQuery++;
        aList = new ArrayList<>();
        values_query = new ArrayList<>();
        field_query = field;
        values_query.add(value);
        query = matchPhraseQuery(field, value);
        System.out.printf("\n****** %d.- search Info : - operator : equals, - field : %s, - searchValues : %s ***\n",
                numQuery, field_query, values_query.toString());
        System.out.printf("\nString_query exists : %s, \n\nMasters Result : \n", query);
        hits = client
                .prepareSearch(indice)
                .setQuery(query)
                .setSize(10)
                .execute()
                .actionGet()
                .getHits()
                .getHits();
        for (SearchHit hit : hits) aList.add(hit.getSourceAsString());
        return aList;
    }


    /**
     * count all data
     *
     * @param indiceName
     */
    public static void countAllDocuments(String indiceName) {
        ++numQuery;
        System.out.printf("\n****** %d.- search Info : - operator : count - AllDocuments***\n", numQuery);
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
    public static void countValueSearch(String indice, String field,
                                        String valor) {
        long count = 0;
        ++numQuery;
        field_query = field;
        values_query = new ArrayList<>();
        values_query.add(valor);
        System.out.printf("\n****** %d.- search Info : - operator : count-SearchValue, - field : %s, - searchValues : %s ***\n",
                numQuery, field_query, values_query.toString());
        query = matchPhraseQuery(field_query, values_query.get(0));
        count = client
                .prepareSearch(indice)
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
    public static List<String> searchMasterFromQuerywithPhrase(String indiceName, String field,
                                                               String value) {
        ++numQuery;
        field_query = field;
        values_query = new ArrayList<>();
        values_query.add(value);
        query = matchPhraseQuery(field_query, values_query.get(0));
        aList = new ArrayList<>();
        System.out.printf("\n****** %d.- search Info : - operator : match phrase, - field : %s, - searchValues : %s ***\n",
                numQuery, field_query, values_query.toString());
        System.out.printf("\nString_query exists : %s, \n\nMasters Result : \n", query);
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
        numQuery++;
        aList = new ArrayList<>();
        operator_query = operator;
        field_query = field;
        values_query = aValues;
        query = queryStringQuery(generarQuery());
        printSearchQuery();
        System.out.printf("\nString_query exists : %s, \n\nMasters Result : \n", sb);
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
     * FuzzinessSearch
     * @param operator
     * @param indice
     * @param field
     * @param value
     * @return
     */
    public static ArrayList<String> FuzzinessSearch(String operator, String indice, String field,
                                                    String value){
        numQuery++;
        aList = new ArrayList<>();
        operator_query = operator;
        values_query = new ArrayList<>();
        field_query = field;
        values_query.add(value);
        query = queryStringQuery(generarQuery());
        System.out.printf("\n****** %d.- search Info : - operator : %s, - field : %s, - searchValues : %s ***\n",
                numQuery, operator_query, field_query, values_query.toString());
        System.out.printf("\nString_query exists : %s, \n\nMasters Result : \n", sb);
        hits = client
                .prepareSearch(indice)
                .setQuery(query)
                .execute()
                .actionGet()
                .getHits()
                .getHits();
        for (SearchHit hit : hits) aList.add(hit.getSourceAsString());
        return aList;
    }

    /**
     * Proximity search
     * @param operator
     * @param indice
     * @param field
     * @param aValue
     * @return
     */
    public static ArrayList<String> ProximitySearch(String operator, String indice, String field,
                                                    ArrayList<String> aValue, int numProx){
        numQuery++;
        aList = new ArrayList<>();
        operator_query = operator;
        values_query = aValue;
        field_query = field;
        numProximity = numProx;
        query = queryStringQuery(generarQuery());
        System.out.printf("\n****** %d.- search Info : - operator : %s, - field : %s, - searchValues : %s ***\n",
                numQuery, operator_query, field_query, values_query.toString());
        System.out.printf("\nString_query exists : %s, \n\nMasters Result : \n", sb);
        hits = client
                .prepareSearch(indice)
                .setQuery(query)
                .execute()
                .actionGet()
                .getHits()
                .getHits();
        for (SearchHit hit : hits) aList.add(hit.getSourceAsString());
        return aList;
    }

    /**
     * print info
     * of the query
     */
    private static void printSearchQuery() {
        if (!operator_query.equalsIgnoreCase("exists"))
            System.out.printf("\n****** %d.- search Info : - operator : %s, - field : %s, - searchValues : %s ***\n",
                numQuery, operator_query, field_query, values_query.toString());
        else System.out.printf("\n ****** %d.- search Info : - operator : %s, - field : %s***\n",
                numQuery, operator_query, field_query);
    }

    /**
     * generar Query
     * @return
     */
    private static String generarQuery() {
        s_aux = "";
        switch (operator_query){
            case "AND":
                s_aux = genQueryAND();
                break;
            case "OR":
                s_aux = genQueryOR();
                break;
            case "exists" :
                s_aux = genQueryExist();
                break;
            case "fuzziness" :
                s_aux = genQueryFuzziness();
                break;
            case "proximity" :
                s_aux = genProximity();
        }
        return s_aux;
    }

    /**
     * genProximity
     * @return
     */
    private static String genProximity() {
        sb = new StringBuilder("(" + field_query + "\"");
        for (String s :
                values_query) {
            sb.append(s + " ");
        }
        sb.append("\"~"+numProximity+")");
        return sb.toString();
    }

    /**
     * genQueryFuzziness
     * @return
     */
    private static String genQueryFuzziness() {
        sb = new StringBuilder("(" + field_query + " " + values_query.get(0) + "~)");
        return sb. toString();
    }

    /**
     * genQueryExist
     * @return
     */
    private static String genQueryExist() {
        sb = new StringBuilder("( _exists_:");
        sb.append(field_query + ")");
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
        return  sb.toString();
    }



}
