package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.jakarta.auth.Credentials;
import org.eclipse.jakarta.auth.api.Secured;
import org.eclipse.jakarta.dao.UtilisateurDao;

import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Random;

@Path("/authentication")
public class AuthenticationResource {

    @Inject
    UtilisateurDao utilisateurDao;

    @Context
    SecurityContext securityContext;

    @POST
    @Secured
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(Credentials credentials, @Context SecurityContext securityContext) {

        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            Principal principal = securityContext.getUserPrincipal();
            String usernameValid = principal.getName();

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        utilisateurDao.isUsernameAndPasswordValid(username, password);
        // Throw an Exception if the credentials are invalid
    }

    private String issueToken(String username) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        return token;
    }
}
