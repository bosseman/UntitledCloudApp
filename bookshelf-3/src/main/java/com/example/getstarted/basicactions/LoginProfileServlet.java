package com.example.getstarted.basicactions;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.getstarted.daos.*;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.util.CloudStorageHelper;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginProfileServlet extends HttpServlet {

    public LoginProfileServlet() {

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("page", "login");
		request.getRequestDispatcher("/base.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProfileDao dao = (ProfileDao) this.getServletContext().getAttribute("dao");
		String uemail = request.getParameter("uMail");
		String upassword = request.getParameter("uPassword");
		boolean loginSuccess = false;
		try {
			loginSuccess = dao.validateProfile(uemail, upassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(loginSuccess) {

			HttpSession session = request.getSession();
			session.setAttribute("uemail", uemail);
			session.setAttribute("upassword", upassword);
			try {
				Profile thisProfile = dao.readProfiles(uemail);
				session.setAttribute("nickname", thisProfile.getCreatedBy());
				session.setAttribute("description", thisProfile.getDescription());
				session.setAttribute("id", thisProfile.getId());
				session.setAttribute("imageUrl", thisProfile.getImageUrl());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("/mainActions");

		}
		else {
			response.sendRedirect("/login");
		}

	}

}
