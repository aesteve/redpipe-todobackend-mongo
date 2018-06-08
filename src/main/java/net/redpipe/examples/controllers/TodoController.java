package net.redpipe.examples.controllers;

import io.reactivex.Single;
import net.redpipe.examples.domain.Todo;
import net.redpipe.examples.services.TodoMongoService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Collections;
import java.util.List;

@Path("/todos")
public class TodoController {

    @Inject
    private TodoMongoService db;

    @GET
    public Single<List<Todo>> allTodos() {
        // return db.findAll();
        return Single.just(Collections.emptyList());
    }

    @GET
    @Path("/{id}")
    public Single<Todo> getById(@PathParam("id") Integer id) {
        return db.findById(id);
    }
}
