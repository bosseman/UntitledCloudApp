package com.example.getstarted.basicactions;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.getstarted.daos.CloudSqlDao;
import com.example.getstarted.daos.ProfileDao;
import com.example.getstarted.objects.Profile;


@WebServlet(name = "like", urlPatterns = "/like")
public class likeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	     String endCursor = null;
	    long likedId;
		boolean matched = false;
		endCursor = request.getParameter("cursor").toString();
		likedId = Long.decode(request.getParameter("id"));
		
		ProfileDao dao = (ProfileDao) this.getServletContext().getAttribute("dao");
		HttpSession session = request.getSession();
		try {
			matched = dao.insertLike(Long.decode(session.getAttribute("id").toString()), likedId);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(matched) {
			request.setAttribute("matched", "Matched!");
		}else {
			request.setAttribute("matched", "No match :(");
		}
	    request.setAttribute("cursor", endCursor);
		request.setAttribute("page","list");
	    request.getRequestDispatcher("/base.jsp").forward(request, response);
		
		
	}

}
