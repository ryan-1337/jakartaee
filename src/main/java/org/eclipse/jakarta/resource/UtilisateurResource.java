package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.jakarta.dao.UtilisateurDao;
import org.eclipse.jakarta.model.Utilisateur;
import org.eclipse.jakarta.service.AuthService;

import java.util.List;

@Path("/users")
public class UtilisateurResource {
    @Inject
    UtilisateurDao utilisateurDao;

    @Inject
    AuthService authService;

    @Inject
    SecurityContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam("Authorization") String token) {
        Response response = Response.ok(utilisateurDao.getAll(), MediaType.APPLICATION_JSON).build();
        return isValidToken(token, response);
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOne(@HeaderParam("Authorization") String token, Utilisateur utilisateur) {
        Utilisateur user = utilisateurDao.findByUsername(utilisateur.getUsername());
        if(user == null) {
            utilisateurDao.add(utilisateur);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Transactional
    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        try {
            Utilisateur utilisateur = utilisateurDao.findById(id);
            utilisateurDao.delete(utilisateur);
            return Response.status(Response.Status.ACCEPTED).build();
        }
         catch (EntityNotFoundException e) {
             return Response.status(Response.Status.NOT_FOUND)
                     .entity("Technique not found for ID: " + id)
                     .type(MediaType.APPLICATION_JSON)
                     .build();
         }
    }

    @Transactional
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@HeaderParam("Authorization") String token,@PathParam("id") Long id) {
        Utilisateur utilisateur = utilisateurDao.findById(id);
        utilisateurDao.update(utilisateur);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    public Response isValidToken(String token, Response response) {
        if (token == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (!utilisateurDao.getUserByToken(token).isPresent()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }
}