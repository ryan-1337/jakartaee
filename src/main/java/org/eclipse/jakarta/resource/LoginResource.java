package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.jakarta.dao.UtilisateurDao;
import org.eclipse.jakarta.model.Utilisateur;

@Path("/login")
public class LoginResource {
    @Inject
    private UtilisateurDao utilisateurDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Utilisateur utilisateur) {

        String username = utilisateur.getUsername();
        String password = utilisateur.getPassword();

        Boolean isPasswordAndUsernameValid = utilisateurDao.isUsernameAndPasswordValid(username, password);

        if(isPasswordAndUsernameValid) {
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return  Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
