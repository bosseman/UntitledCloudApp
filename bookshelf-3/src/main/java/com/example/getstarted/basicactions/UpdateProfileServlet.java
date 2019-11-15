/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.basicactions;

import com.example.getstarted.daos.ProfileDao;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.util.CloudStorageHelper;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// [START example]
@SuppressWarnings("serial")
@MultipartConfig
@WebServlet(name = "update", value = "/update")
public class UpdateProfileServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
	  HttpSession session = req.getSession();
     req.setAttribute("email", session.getAttribute("uemail").toString());
     req.setAttribute("password", session.getAttribute("upassword").toString());
     req.setAttribute("passwordRepeat", session.getAttribute("upassword").toString());
     req.setAttribute("description", session.getAttribute("description").toString());
     req.setAttribute("nickname", session.getAttribute("nickname").toString());
     req.setAttribute("imageUrl", session.getAttribute("imageUrl").toString());
     req.setAttribute("id", session.getAttribute("id").toString());
     req.setAttribute("action", "/update");
      req.setAttribute("page", "form");
      req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {

    // [START storageHelper]
    CloudStorageHelper storageHelper =
        (CloudStorageHelper) req.getServletContext().getAttribute("storageHelper");
    String imageUrl =
        storageHelper.getImageUrl(
            req, resp, getServletContext().getInitParameter("bookshelf.bucket"));
    // [END storageHelper]
    ProfileDao dao = (ProfileDao) this.getServletContext().getAttribute("dao");
    try {
      // [START bookBuilder]
      Profile profile = new Profile.Builder()
          .title(req.getParameter("uEmail"))
          .password(req.getParameter("uPassword"))
          .id(Long.decode(req.getParameter("id")))
          .description(req.getParameter("description"))
          .createdBy(req.getParameter("Nickname"))
          .imageUrl(imageUrl)
          .build();
      // [END bookBuilder]
      HttpSession session = req.getSession();
      dao.updateProfile(profile);	
      
      session.setAttribute("uemail", profile.getTitle());
      session.setAttribute("upassword", profile.getAuthor());
      session.setAttribute("nickname", profile.getCreatedBy());
      session.setAttribute("description", profile.getDescription());
      session.setAttribute("id", profile.getId());
      session.setAttribute("imageUrl", profile.getImageUrl());
      
      resp.sendRedirect("/mainActions");
      
    } catch (Exception e) {
      throw new ServletException("Error updating book", e);
    }
  }
}
// [END example]
