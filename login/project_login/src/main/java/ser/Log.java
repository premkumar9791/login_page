package ser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet("/Log")
public class Log extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String uemail = request.getParameter("username");
	        String upass = request.getParameter("password");
	        HttpSession session = request.getSession();
	        RequestDispatcher dispatcher = null;
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet rs = null;

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com/sql3697586?useSSL=false",
	                    "sql3697586", "B63JXqENxi");

	            preparedStatement = connection
	                    .prepareStatement("SELECT * FROM Loginpage_data WHERE gmail=? AND password=? ");

	            preparedStatement.setString(1, uemail);
	            preparedStatement.setString(2, upass);

	            rs = preparedStatement.executeQuery();
	            if (rs.next()) {
	                session.setAttribute("name", rs.getString("name"));
	                response.sendRedirect("https://sagitor-fitness-page-mxo8.vercel.app/home");
	                return;
	                
	              //dispatcher = request.getRequestDispatcher("index.jsp");
	              
	            } else {
	                request.setAttribute("status", "error");
	                dispatcher = request.getRequestDispatcher("login.jsp");
	                dispatcher.forward(request, response);
	            }

	           // dispatcher.forward(request, response);
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) {
	                    rs.close();
	                }
	                if (preparedStatement != null) {
	                    preparedStatement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	}
}
