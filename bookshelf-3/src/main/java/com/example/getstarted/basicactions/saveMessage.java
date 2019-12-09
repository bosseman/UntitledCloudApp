package com.example.getstarted.basicactions;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.getstarted.daos.ProfileDao;

/**
 * Servlet implementation class saveMessage
 */
@WebServlet(name = "sendMessage" , urlPatterns = {"/sendMessage"})
public class saveMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		long userId = Long.decode(req.getParameter("id"));
		long myId = Long.decode(session.getAttribute("id").toString());
		String message = req.getParameter("inputMessage");
	    ProfileDao dao = (ProfileDao) this.getServletContext().getAttribute("dao");
	    try {
			dao.insertMessage(myId, userId, message);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    resp.sendRedirect("/OpenConvo");
	}

}
