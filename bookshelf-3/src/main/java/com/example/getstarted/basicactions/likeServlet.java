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



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



	    Integer endCursor;

	    long likedId;

		boolean matched = false;
		try {
		endCursor = Integer.parseInt(request.getParameter("cursor").toString());
		}
		catch(NumberFormatException e) {
			//At the end of the list
			endCursor = -1;
		}
		//System.out.print("TEST CURSOR"+endCursor);
		likedId = Long.decode(request.getParameter("id"));

		

		ProfileDao dao = (ProfileDao) this.getServletContext().getAttribute("dao");

		HttpSession session = request.getSession();

		try {

			matched = dao.insertLike(Long.decode(session.getAttribute("id").toString()), likedId);

		} catch (NumberFormatException | SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}


	    
		if(endCursor >= 0) {
			response.sendRedirect("/matching?cursor="+(endCursor-1));
		}else {
			//restart
		response.sendRedirect("/matching");
		}
		

		

	}



}
