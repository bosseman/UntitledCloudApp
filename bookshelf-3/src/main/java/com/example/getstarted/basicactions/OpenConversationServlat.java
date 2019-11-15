package com.example.getstarted.basicactions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OpenConversationServlat
 */
@WebServlet(name = "openConvo", urlPatterns = {"/OpenConvo"})
public class OpenConversationServlat extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get button id(Profile needed) and session id 
		//Call function to query in the conversation from coversation table? yes thats probably the best way to do it
		//Where button.id = id1 and session id = id 2 OR button.id = id2 and session.id = id1 ...Order by timestamp? idk how to do that 
		//Create ResultSet to hold each message
		//pass ResultSet to JSP to do forEach
		//forEach only displays: profile nickname: message
		//-------If each message is retrieved at the same order they are sent then order by wont matter.
		//load two btns
		//One for submit message, message gets saved into db with the ids
		//---------message must also be added to ResultSet to be displayed
		//Thoughts written down, time to smoke
		//The other just redirects back to /convserations
	}

}
