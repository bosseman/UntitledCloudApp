package com.example.getstarted.basicactions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import java.io.IOException;



import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class logoutServlet
 */
@WebServlet(name = "logout" ,urlPatterns = {"/logout"})
public class logoutServlet extends HttpServlet {

       



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        //invalidate the session if exists

        HttpSession session = request.getSession(false);

        if(session != null){

            session.invalidate();

        }
        request.setAttribute("page", "login");
        request.getRequestDispatcher("/base.jsp").forward(request, response);


    }

}