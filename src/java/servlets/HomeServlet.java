
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        /**
         * Test 4. Logged out redirection
         *  Test: Start from the login screen, then change URL to /home
         *  Result: Web page URL changed to /login and login page is displayed
         */
        if (user == null || user.equals(""))
        {
            // for first visit, first page is login page
            response.sendRedirect("login");
            return;  
        }
        else
        {
            // login successfully
            String username = user.getUsername();
            request.setAttribute("username", username);
            getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
            return;        
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }



}
