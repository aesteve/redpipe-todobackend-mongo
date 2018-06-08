package net.redpipe.examples.services;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

import net.redpipe.examples.domain.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;


import net.redpipe.examples.marshalling.TodoMarshaller;

import javax.ws.rs.ext.Provider;

import static net.redpipe.examples.utils.Conf.MONGO_PORT;

@Provider
public class TodoMongoService {

    private final static String COLLECTION = "todos";
    private final static Logger LOG = LoggerFactory.getLogger(TodoMongoService.class);
    private final static JsonObject EMPTY_QUERY = new JsonObject();


    @Inject
    private Vertx vertx;
    private MongoClient client;

    public Single<List<Todo>> findAll() {
        return withClient(client ->
            client
                .rxFind(COLLECTION, EMPTY_QUERY)
                .map(TodoMarshaller::fromMongoCollection)
        );
    }

    public Single<Todo> findById(int id) {
        return withClient(client ->
            client
                .rxFindOne(COLLECTION, byId(id), null)
                .map(TodoMarshaller::fromMongo)
        );
    }

    private JsonObject byId(int id) {
        return new JsonObject()
                .put("id", id);
    }

    private Single<MongoClient> init() {
        final JsonObject config = new JsonObject()
                .put("connection_string", "mongodb://localhost:" + MONGO_PORT)
                .put("db_name", "my_DB");
        client = MongoClient.createShared(vertx, config);
        return client
                .rxCreateCollection(COLLECTION)
                .toSingleDefault(client);
    }

    private<R> Single<R> withClient(Function<MongoClient, Single<R>> execution) {
        return client().flatMap(execution);
    }

    private Single<MongoClient> client() {
        if (client == null) {
            return init();
        }
        return Single.just(client);
    }

}
