package net.redpipe.examples;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.embeddedmongo.EmbeddedMongoVerticle;
import io.vertx.reactivex.core.Vertx;
import net.redpipe.engine.core.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodobackendMongo {

    private final static Logger LOG = LoggerFactory.getLogger(TodobackendMongo.class);

    private final static JsonObject TEST_CONF = new JsonObject()
            .put("http_port", 9090);

    private static Vertx vertx = Vertx.vertx();

    public static void main(String... args) {

        final DeploymentOptions opts = new DeploymentOptions().setWorker(true);
        vertx.deployVerticle(EmbeddedMongoVerticle.class.getName(), opts, db -> {
            if (db.failed()) {
                db.cause().printStackTrace();
                return;
            }
            new Server()
                    .start(TEST_CONF)
                    .subscribe(
                            voidz -> LOG.info("Server up and running"),
                            error -> LOG.error("Could not start server", error)
                    );
        });
    }


}
