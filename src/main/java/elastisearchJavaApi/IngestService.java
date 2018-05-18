package elastisearchJavaApi;


import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.List;

import static elastisearchJavaApi.Main.client;

public class IngestService {

    /**
     * ingest Service
     * 1 doc
     * @param type
     * @param doc
     * @return
     */
    public static String ingest(String type, String doc) {
         return  client.prepareIndex("sesion", "dia")
                 .setSource(doc, XContentType.JSON)
                 .get().getResult().toString();
    }

    /**
     * ingest Service
     * many docs
     * @param type
     * @param docs
     * @return
     */
    public static boolean ingest(String type, List<String> docs) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        docs.forEach(doc -> bulkRequest.add(client.prepareIndex("sesion", type).setSource(doc)));
        return bulkRequest.get().hasFailures();

    }

}