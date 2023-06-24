package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.jakarta.dao.TechniqueDao;
import org.eclipse.jakarta.dao.UtilisateurDao;
import org.eclipse.jakarta.model.Technique;
import org.eclipse.jakarta.model.Utilisateur;
import org.eclipse.jakarta.service.AuthService;

@Path("/technique")
public class TechniqueResource {

    @Inject
    TechniqueDao techniqueDao;

    @Inject
    UtilisateurDao utilisateurDao;

    @Inject
    AuthService authService;

    @Inject
    SecurityContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam("Authorization") String token) {
        Response response = Response.ok(techniqueDao.getAll(), MediaType.APPLICATION_JSON).build();
        return isValidToken(token, response);
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOne(@HeaderParam("Authorization") String token, Technique technique) {
        Technique tech = techniqueDao.findByName(technique.getName());
        if(tech == null) {
            techniqueDao.add(technique);
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
        Technique technique = techniqueDao.findById(id);
        techniqueDao.delete(technique);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@HeaderParam("Authorization") String token, Long id) {
        Technique technique = techniqueDao.findById(id);
        techniqueDao.update(technique);
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
