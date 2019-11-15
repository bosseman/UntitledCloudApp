package com.example.getstarted.basicactions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.getstarted.objects.Profile;


@WebServlet(name = "dislike", urlPatterns = {"/dislike"})
@SuppressWarnings("serial")
public class dislikeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Profile profiles = null;
		req.getSession().getServletContext().setAttribute("Profiles", profiles);
	    req.setAttribute("page", "list");
	    req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
