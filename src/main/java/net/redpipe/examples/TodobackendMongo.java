package net.redpipe.examples;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.embeddedmongo.EmbeddedMongoVerticle;
import net.redpipe.engine.core.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodobackendMongo {

    private final static Logger LOG = LoggerFactory.getLogger(TodobackendMongo.class);

    private final static JsonObject TEST_CONF = new JsonObject()
            .put("http_port", 9090);

    public static void main(String... args) {

    final DeploymentOptions opts = new DeploymentOptions().setWorker(true);
    final Server server = new Server();
    server
        .start(TEST_CONF)
        .flatMap(v -> server.getVertx().rxDeployVerticle(EmbeddedMongoVerticle.class.getName(), opts))
        .subscribe(
            voidz -> LOG.info("Server up and running"),
            error -> LOG.error("Could not start server", error)
        );
    }


}
