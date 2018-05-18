package elastisearchJavaApi;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

import static elastisearchJavaApi.Main.client;
import static elastisearchJavaApi.Utilities.printS;
import static org.elasticsearch.index.query.QueryBuilders.*;

public class DataService {


    /**
     * get Match All QueryData
     * @return
     */
    public static List<String> getMatchAllQueryData() {
        QueryBuilder query = matchAllQuery();
        System.out.println("getMatchAllQueryCount query =>" + query.toString());
        SearchHit[] hits = client.prepareSearch("twitter").setQuery(query).execute().actionGet().getHits().getHits();
        List<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            // hit.sourceAsMap()
            list.add(hit.getSourceAsString());
        }
        return list;
    }

    /**
     * get Boolean Query Data
     * @return
     */
    public static List<String> getBoolQueryData() {
        QueryBuilder query = boolQuery().must(
                termQuery("user", "skyji")
        ).must(termQuery("location", "India"));
        printS("getBoolQueryCount query =>" + query.toString());
        SearchHit[] hits = client.prepareSearch("twitter").setQuery(query).execute().actionGet().getHits().getHits();
        List<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            // hit.sourceAsMap()
            list.add(hit.getSourceAsString());
        }
        return list;
    }

    /**
     * get Phrase QueryData
     * @return
     */
    public static List<String> getPhraseQueryData() {
        QueryBuilder query = matchPhraseQuery("name", "samuel");
        printS("getBoolQueryCount query =>" + query.toString());
        SearchHit[] hits = client.prepareSearch("twitter").setQuery(query).execute().actionGet().getHits().getHits();
        List<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            // hit.sourceAsMap()
            list.add(hit.getSourceAsString());
        }
        return list;
    }


}