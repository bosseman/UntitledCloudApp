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

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
// [START annotations]
@MultipartConfig
@WebServlet(name = "create", urlPatterns = {"/create"})
// [END annotations]
public class CreateProfileServlet extends HttpServlet {

  // [START setup]
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    req.setAttribute("page", "form");           // Tells base.jsp to include form.jsp
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
    req.setAttribute("action", "/create");
  }
  // [END setup]

  // [START formpost]
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
    // [START bookBuilder]
    Profile profile = new Profile.Builder()
        .password(req.getParameter("uPassword"))   // form parameter
        .description(req.getParameter("description"))
        .createdBy(req.getParameter("Nickname"))
        .title(req.getParameter("uEmail"))
        .imageUrl(imageUrl)
        .build();
    // [END bookBuilder]
    try {
      Long id = dao.createProfiles(profile);
      resp.sendRedirect("/login");   
    } catch (Exception e) {
      throw new ServletException("Error creating book", e);
    }
  }
  // [END formpost]
}
// [END example]
