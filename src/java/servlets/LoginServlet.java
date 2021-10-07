package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {  
        // get the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String query = request.getQueryString();
        
        /**
         * Test 6. Session destroyed upon logout
         *  Test: Log in with correct credentials, then log out, then open a new tab and enter URL with /login
         *  Result: Login page is displayed
         */
        if (query != null && query.equals("logout"))
        {
            // invalidate the session
            session.invalidate();
            session = request.getSession();
            
           /**
            * Test 2. Logout
            *  Test: Log in with correct credentials, e.g. abe/password, then click logout link
            *  Result: Login page is displayed
            */
            // display the user has successfully logged out
            request.setAttribute("Entry", "logout");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;  
        }
        /**
         * Test 3. Logged in redirection
         *  Test: Log in with correct credentials, then change URL to /login
         *  Result: Web page URL changed to /home and home page is displayed
         */
        if (user == null || user.equals(""))
        {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;              
        } 
        else
        {
            /**
             * Test 5. Session created upon login
             *  Test: Log in with correct credentials, then open new tab and enter URL with /home
             *  Rest: Home page displayed
             */
            response.sendRedirect("home");
            return;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String username = "";
        String password = "";
        
        // capture the parameters coming in from the POST request
        username = request.getParameter("username");
        password = request.getParameter("password");
        
        // first validates that user name and password are not empty
        if(username == null || username.equals("") || password == null || password.equals(""))
        {
            request.setAttribute("Entry", "empty");

            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        else
        {
            /**
             * Test 1. Login
             *  Test: Attempt log in with incorrect credentials, e.g. abe/passwd
             *  Result: Failed authentication
             *  Test: Log in with correct credentials, e.g. abe/password
             *  Result: Home page displayed
             */
            AccountService a = new AccountService();
            // Then it passes the user name and password parameters to AccountService
            if(a.login(username, password) != null)
            {
                HttpSession session = request.getSession();
                session.setAttribute("user", new User(username, password));
                response.sendRedirect("home");
            }
            else
            {
                // invalid login and keep the textbook filled in with username
                request.setAttribute("user", new User(username, ""));
                request.setAttribute("Entry", "invalidLogin");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
            return;
        }
    }
}
