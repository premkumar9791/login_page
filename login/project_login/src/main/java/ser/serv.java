package ser;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/serv")
public class serv extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upass = request.getParameter("pass");
        String ucontact = request.getParameter("contact");
        RequestDispatcher dispatcher = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com/sql3697586?useSSL=false", "sql3697586", "B63JXqENxi");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Loginpage_data (name, gmail, password) VALUES (?, ?, ?)");

            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, uemail);
            preparedStatement.setString(3, upass);

            int rowsInserted = preparedStatement.executeUpdate();

            dispatcher = request.getRequestDispatcher("registration.html");
            response.setContentType("text/html");
            if (rowsInserted > 0) {
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("status", "fail");
            }

            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
