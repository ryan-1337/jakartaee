package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.eclipse.jakarta.dao.UtilisateurDao;
import org.eclipse.jakarta.model.Utilisateur;
import org.eclipse.jakarta.service.AuthService;

@Path("/auth")
public class AuthResource {

    @Inject
    private AuthService authService;

    @Inject
    UtilisateurDao utilisateurDao;

    @POST
    @Path("/login")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(JsonObject credentials, @Context UriInfo uriInfo) {
        System.err.println("hello");

        String username = credentials.getString("username");
        String password = credentials.getString("password");

        // Vérification des informations d'identification
        if (!authService.authenticate(username, password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Utilisateur user = utilisateurDao.getUserByUsername(username);

        // Génération du jeton d'accès
        String token = authService.generateToken(username);

        // Construction de la réponse
        JsonObject responseJson = Json.createObjectBuilder()
                .add("token", token)
                .build();

        user.setToken(token);
        utilisateurDao.update(user);


        // Ajout du jeton d'accès dans l'en-tête de réponse
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(token);
        return Response.ok(responseJson)
                .header("Authorization", "Bearer " + token)
                .location(uriBuilder.build())
                .build();
    }
}