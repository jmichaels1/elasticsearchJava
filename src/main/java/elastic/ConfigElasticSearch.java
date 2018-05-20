package elastic;


import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by Michael
 */
public class ConfigElasticSearch {

    public static Client client;


    /**
     * connect elasticsearch
     */
    public static void connectElastic(String localhost, int port) {
        client = getClient(localhost, port).get();
    }

    /**
     * get client
     * @param host
     * @param port
     * @return
     */
    public static Optional<Client> getClient(String host, int port) {
        try {
            Settings.Builder setting = Settings.builder().put("client.transport.sniff", false);
            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
            return Optional.of(client);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * close connection elastic
     */
    public static void closeConnection(){
        client.close();
    }

    /**
     * create and config indice
     * @param indexName
     */
    public static void createIndex(String indexName) throws IOException {
        client.admin().indices().preparePutMapping(indexName)
                .setType("_default")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("index_analyzer", "autocomplete")
                        .field("search_analyzer", "spanish")
                        .endObject());
    }

    /**
     * delete indice
     * @param indiceName
     */
    public static void deleteIndex(String indiceName) {
        DeleteIndexResponse deleteResponse = client.admin().indices()
                .delete(new DeleteIndexRequest(indiceName)).actionGet();
    }


}