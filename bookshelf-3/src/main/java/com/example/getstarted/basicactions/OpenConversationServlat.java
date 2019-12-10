package com.example.getstarted.basicactions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.getstarted.daos.ProfileDao;
import com.example.getstarted.objects.Convo;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;

/**
 * Servlet implementation class OpenConversationServlat
 */
@WebServlet(name = "openConvo", urlPatterns = {"/OpenConvo"})
public class OpenConversationServlat extends HttpServlet {


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String userName = req.getParameter("userName");
		Long userId = Long.decode(req.getParameter("openC"));
		//System.out.println(userId);
		 HttpSession session = req.getSession();
		    ProfileDao dao = (ProfileDao) this.getServletContext().getAttribute("dao");

		    List<Convo> conversation = null;
		    String endCursor = null;
		    try {

		      Result<Convo> result = dao.listConversation(Long.decode(session.getAttribute("id").toString()), userId);
		      conversation = result.result;

		    } catch (Exception e) {
		      throw new ServletException("Error listing books", e);
		    }
		    

		    req.getSession().getServletContext().setAttribute("conversation", conversation);
		    StringBuilder profileNames = new StringBuilder();
		    for (Convo profile : conversation) {
		      profileNames.append(profile + " ");

		    }
			//Minor change. Save the id we wish to open into a session variable. This will be used for getting convo. 
		    session.setAttribute("openProfileId", userId);
		    req.setAttribute("page", "conversation");
		    req.getRequestDispatcher("/base.jsp").forward(req, resp);
		    
		    
	}


}
