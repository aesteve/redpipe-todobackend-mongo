package net.redpipe.examples.services;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.mongo.MongoClient;
import io.vertx.core.Vertx;
import net.redpipe.examples.domain.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;


import net.redpipe.examples.marshalling.TodoMarshaller;

import javax.ws.rs.ext.Provider;

@Provider
public class TodoMongoService {

    private final static String COLLECTION = "todos";
    private final static Logger LOG = LoggerFactory.getLogger(TodoMongoService.class);
    private final static JsonObject EMPTY_QUERY = new JsonObject();


    @Inject
    private Vertx vertx;
    private MongoClient client;

    public Single<List<Todo>> findAll() {
        return client()
                .rxFind(COLLECTION, EMPTY_QUERY)
                .map(TodoMarshaller::fromMongoCollection);
    }

    public Single<Todo> findById(int id) {
        return client()
                .rxFindOne(COLLECTION, byId(id), null)
                .map(TodoMarshaller::fromMongo);
    }

    private JsonObject byId(int id) {
        return new JsonObject()
                .put("id", id);
    }

    private void init() {
        final JsonObject config = new JsonObject()
                .put("connection_string", "mongodb://localhost:27018")
                .put("db_name", "my_DB");
        client = MongoClient.createShared(io.vertx.reactivex.core.Vertx.newInstance(vertx), config);
        client.createCollection(COLLECTION, res -> {
            if (res.failed()) {
                LOG.error("Could not create collection {} within Mongo.", COLLECTION, res.cause());
            } else {
                LOG.info("Successfully created collection {} within Mongo", COLLECTION);
            }
        });
    }

    private MongoClient client() {
        if (client == null) {
            init();
        }
        return client;
    }

}
