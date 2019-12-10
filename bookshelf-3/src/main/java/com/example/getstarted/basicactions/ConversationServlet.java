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

	    }
	    request.setAttribute("cursor", endCursor);
	    request.setAttribute("page", "conversations");
	    request.getRequestDispatcher("/base.jsp").forward(request, response);
	    
	    
	  }



}
