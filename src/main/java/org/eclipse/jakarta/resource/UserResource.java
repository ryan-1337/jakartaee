package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.jakarta.dao.UserDao;
import org.eclipse.jakarta.model.User;

import java.util.List;

@Path("/users")
public class UserResource {
    @Inject
    UserDao userDao;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOne(User utilisateur) {
        User user = userDao.findByUsername(utilisateur.getUsername());
        if(user == null) {
            userDao.add(utilisateur);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Transactional
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Long id) {
        User user = userDao.findById(id);
        userDao.delete(user);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Long id) {
        User user = userDao.findById(id);
        userDao.update(user);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}