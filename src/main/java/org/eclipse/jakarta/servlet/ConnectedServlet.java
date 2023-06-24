package org.eclipse.jakarta.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.eclipse.jakarta.dao.UtilisateurDao;
import org.eclipse.jakarta.model.Utilisateur;
import org.eclipse.jakarta.service.AuthService;

import java.io.IOException;

@WebServlet("/connectedServlet")
public class ConnectedServlet extends HttpServlet{

        @Inject
        private AuthService authService;

        @Inject
        private UtilisateurDao utilisateurDao;


        @Override
        public void init() throws ServletException {
            super.init();
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                HttpSession session = request.getSession();

                String action = request.getParameter("action");

                if (action != null) {
                        String username = request.getParameter("username");
                        String password = request.getParameter("password");

                        Utilisateur user = new Utilisateur();
                        user.setUsername(username);
                        user.setPassword(password);
                        utilisateurDao.add(user);
                }
        }
}
