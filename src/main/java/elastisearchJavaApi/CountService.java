package elastisearchJavaApi;

import org.elasticsearch.index.query.QueryBuilder;

import static elastisearchJavaApi.Main.client;
import static org.elasticsearch.index.query.QueryBuilders.*;

public class CountService {

    /**
     * getMatchAllQueryCount
     * @return
     */
    public static long getMatchAllQueryCount() {
        QueryBuilder query = matchAllQuery();
        System.out.println("getMatchAllQueryCount query =>"+ query.toString());
        long count = client.prepareSearch("sesion").setQuery(query).setSize(0).execute().actionGet().getHits().getTotalHits();
        return count;
    }

    /**
     * getBoolQueryCount
     * @return
     */
    public static long getBoolQueryCount() {
        QueryBuilder query = boolQuery().must(
                termQuery("asignatura","Asign1")
        ).must( termQuery("contenido","contenido1"));
        System.out.println("getBoolQueryCount query =>"+ query.toString());
        long count = client.prepareSearch("sesion").setQuery(query).setSize(0).execute().actionGet().getHits().getTotalHits();
        return count;
    }

    /**
     * getPhraseQueryCount
     * @return
     */
    public static long getPhraseQueryCount() {
        QueryBuilder query = matchPhraseQuery("decripcion","decripcion");
        System.out.println("getPhraseQueryCount query =>"+ query.toString());
        long count = client.prepareSearch("sesion").setQuery(query).setSize(0).execute().actionGet().getHits().getTotalHits();
        return count;
    }


}