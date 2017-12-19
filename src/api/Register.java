package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import db1.DBConnection;
import db.mysql.MySQLConnection;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject input = RpcParser.parseInput(request);
			MySQLConnection connection = MySQLConnection.getInstance();
			JSONObject msg = new JSONObject();
			
			String firstname = input.getString("first_name");
			String lastname = input.getString("last_name");
			String user_id = input.getString("user_id");
			String password = input.getString("password");
			
			if (connection.addUser(firstname, lastname, user_id, password)) {
	   			 HttpSession session = request.getSession();
	   			 session.setAttribute("user", user_id);
	   			 // setting session to expire in 10 minutes
	   			 session.setMaxInactiveInterval(10 * 60);
	   			 // Get user name
	   			 String name = connection.getFirstLastName(user_id);
	   			 msg.put("status", "OK");
	   			 msg.put("user_id", user_id);
	   			 msg.put("name", name);				
			} else {
				response.setStatus(401);
			}
			RpcParser.writeOutput(response, msg);
		} catch (Exception e) {
	   		 // TODO Auto-generated catch block
	   		 e.printStackTrace();
	   	 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}