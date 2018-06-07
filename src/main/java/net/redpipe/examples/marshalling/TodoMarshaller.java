package net.redpipe.examples.marshalling;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import net.redpipe.examples.domain.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TodoMarshaller {

    private final static Logger LOG = LoggerFactory.getLogger(TodoMarshaller.class);

    public static List<Todo> fromMongoCollection(List<JsonObject> l) {
        return l.stream()
                .map(TodoMarshaller::fromMongo)
                .collect(Collectors.toList());
    }

    public static Todo fromMongo(JsonObject json) {
        try {
            return Json.mapper.readValue(json.encode(), Todo.class);
        } catch (IOException e) {
            LOG.error("Could not read from Mongo DB, invalid data stored : {}", json);
            return null;
        }
    }


}
