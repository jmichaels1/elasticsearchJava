package elastisearchJavaApi;


import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

import static elastisearchJavaApi.Main.client;

/***
 * add delete by query plugin to Elastisearch
 * $ bin/plugin install delete-by-query
 */
public class DeleteService {

    public static String delete(String id) {
        return client.prepareDelete("twitter", "tweet", id).get().getResult().toString();
    }


    public static long deleteByQuery(String name) {

        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("user", name))
                .source("twitter")
                .get();
        long deleted = response.getDeleted();
        return deleted;
    }
}