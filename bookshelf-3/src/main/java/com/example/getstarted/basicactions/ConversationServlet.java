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

import com.example.getstarted.daos.CloudSqlDao;
import com.example.getstarted.daos.DatastoreDao;
import com.example.getstarted.daos.ProfileDao;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;


@WebServlet(name = "conversations", urlPatterns = {"/conversations"},loadOnStartup = 1)
@SuppressWarnings("serial")
public class ConversationServlet extends HttpServlet {

	  @Override
	  public void init() throws ServletException {
	    ProfileDao dao = null;

	    // [START storageHelper]
	    CloudStorageHelper storageHelper = new CloudStorageHelper();
	    // [END storageHelper]

	    // Creates the DAO based on the Context Parameters
	    String storageType = this.getServletContext().getInitParameter("bookshelf.storageType");
	    switch (storageType) {
	      case "datastore":
	        dao = new DatastoreDao();
	        break;
	      case "cloudsql":
	        try {
	          String connect = this.getServletContext().getInitParameter("sql.urlRemote");
	          if (connect.contains("localhost")) {
	            connect = this.getServletContext().getInitParameter("sql.urlLocal");
	          }
	          dao = new CloudSqlDao(connect);
	        } catch (SQLException e) {
	          throw new ServletException("SQL error", e);
	        }
	        break;
	      default:
	        throw new IllegalStateException(
	            "Invalid storage type. Check if bookshelf.storageType property is set.");
	    }
	    this.getServletContext().setAttribute("dao", dao);

	    // [START save_storage]
	    this.getServletContext().setAttribute("storageHelper", storageHelper);
	    this.getServletContext().setAttribute(
	        "isCloudStorageConfigured",    // Hide upload when Cloud Storage is not configured.
	        !Strings.isNullOrEmpty(getServletContext().getInitParameter("bookshelf.bucket")));
	    // [END save_storage]
	  }   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Need to set up a menu with all profiles matched with
		//First. We need to call a function to retrieve all matches profiles
		//Second. We need to load each profile similar to "List" into a cursor that displays... 10? at a time
		//Then create a button with the id of each unique profile, and user profile pic and name need to be loaded
		 HttpSession session = request.getSession();
	    ProfileDao dao = (ProfileDao) this.getServletContext().getAttribute("dao");
	    String startCursor = request.getParameter("cursor");
	    List<Profile> profiles = null;
	    String endCursor = null;
	    try {
	      Result<Profile> result = dao.listProfiles(startCursor, true, Long.decode(session.getAttribute("id").toString()));
	      profiles = result.result;
	      endCursor = result.cursor;
	    } catch (Exception e) {
	      throw new ServletException("Error listing books", e);
	    }
	    
	    request.getSession().getServletContext().setAttribute("books", profiles);
	    StringBuilder profileNames = new StringBuilder();
	    for (Profile profile : profiles) {
	      profileNames.append(profile.getCreatedBy() + " ");
	      request.setAttribute("id" , profile.getId().toString());
	    }
	    request.setAttribute("cursor", endCursor);
	    request.setAttribute("page", "conversations");
	    request.getRequestDispatcher("/base.jsp").forward(request, response);
	    
	    
	  }
	}    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Once user has selected a profile to open. We need to redirect to a different servlet by saving a variable session
		// call it "likedId" and then redirect to "openConversationServlet" "/openConvo"
		//New servlet will need to grab "likedId", process that and session.id to query database to retrieve
		//All messages. All messages will need to be displayed similar to profiles
		//Perhaps we timestamp messages and grab all where from id1 = session.id and id2 = "likedID" and vice versa
		//Then arrange in dec order? doesnt really matter here.
		//Perhaps also grab id so we can arrange messages from one person on one side then the other person on the other side
		//Kind of like texting? IDK, imma go smoke now that my thoughts have been written down
	}

}
