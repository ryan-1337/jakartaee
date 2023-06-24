package org.eclipse.jakarta.servlet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jakarta.dao.UtilisateurDao;
import org.eclipse.jakarta.model.Utilisateur;
import org.eclipse.jakarta.service.AuthService;

import java.io.IOException;

@WebServlet("/authServlet")
public class AuthServlet extends HttpServlet {

    @Inject
    private AuthService authService;

    @Inject
    private UtilisateurDao utilisateurDao;


    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/connected.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String action = request.getParameter("action");

        if (action != null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (authService.authenticate(username, password)) {
                String token = authService.generateToken(username);
                session.setAttribute("token", token);
                response.setHeader("Authorization", token);
                response.setStatus(HttpServletResponse.SC_OK);
                // Redirect to BookServlet
                response.sendRedirect(request.getContextPath() + "/authServlet");
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter missing");
        }

        if (action != null) {
            switch (action) {
                case "login":
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    if (authService.authenticate(username, password)) {
                        String token = authService.generateToken(username);
                        session.setAttribute("token", token);
                        response.setHeader("Authorization", token);
                        response.setStatus(HttpServletResponse.SC_OK);
                        // Redirect to BookServlet
                        response.sendRedirect(request.getContextPath() + "/authServlet");
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                    }
                    break;
                case "register":
                    response.sendRedirect(request.getContextPath() + "/connectedServlet");

                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    break;
            }
        }
    }
}