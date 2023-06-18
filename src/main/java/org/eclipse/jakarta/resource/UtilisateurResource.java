package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.jakarta.dao.UtilisateurDao;
import org.eclipse.jakarta.model.Utilisateur;

import java.util.List;

@Path("/users")
public class UtilisateurResource {
    @Inject
    UtilisateurDao utilisateurDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Utilisateur> getAll() {
        return utilisateurDao.getAll();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOne(Utilisateur utilisateur) {
        utilisateurDao.add(utilisateur);
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Integer id) {
        Utilisateur utilisateur = utilisateurDao.findById(id);
        utilisateurDao.delete(utilisateur);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Integer id) {
        Utilisateur utilisateur = utilisateurDao.findById(id);
        utilisateurDao.update(utilisateur);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}