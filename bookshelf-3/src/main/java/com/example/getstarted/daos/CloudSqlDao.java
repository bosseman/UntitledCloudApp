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

package com.example.getstarted.daos;

import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

// [START example]
public class CloudSqlDao implements ProfileDao {
  // [START constructor]
  private static final BasicDataSource dataSource = new BasicDataSource();

  /**
   * A data access object for Bookshelf using a Google Cloud SQL server for storage.
   */
  public CloudSqlDao(final String url) throws SQLException {

    dataSource.setUrl(url);
    final String createTableSql = "CREATE TABLE IF NOT EXISTS profiles ( id INT NOT NULL "
        + "AUTO_INCREMENT, uemail VARCHAR(255), upassword VARCHAR(255), nickname VARCHAR(255), "
        + "description VARCHAR(255), imageUrl "
        + "VARCHAR(255), PRIMARY KEY (id))";
    try (Connection conn = dataSource.getConnection()) {
      conn.createStatement().executeUpdate(createTableSql);
      System.out.println("True");
    }
    final String createLikes = "CREATE TABLE IF NOT EXISTS likes1 ( id INT NOT NULL "
            + "AUTO_INCREMENT, id1 INT, id2 INT,"
            + " PRIMARY KEY (id))";
        try (Connection conn = dataSource.getConnection()) {
          conn.createStatement().executeUpdate(createLikes);
          System.out.println("True");
        }
        final String createMatches = "CREATE TABLE IF NOT EXISTS matches ( id INT NOT NULL "
                + "AUTO_INCREMENT, id1 INT, id2 INT,"
                + " PRIMARY KEY (id))";
            try (Connection conn = dataSource.getConnection()) {
              conn.createStatement().executeUpdate(createMatches);
              System.out.println("True");
            }
  }
  // [END constructor]

  // [START create]
  @Override
  public Long createProfiles(Profile profile) throws SQLException {
    final String createBookString = "INSERT INTO profiles "
        + "(uemail, upassword, nickname, description, imageUrl) "
        + "VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = dataSource.getConnection();
         final PreparedStatement createBookStmt = conn.prepareStatement(createBookString,
             Statement.RETURN_GENERATED_KEYS)) {
      createBookStmt.setString(2, profile.getAuthor());
      createBookStmt.setString(3, profile.getCreatedBy());

      createBookStmt.setString(4, profile.getDescription());

      createBookStmt.setString(1, profile.getTitle());
      createBookStmt.setString(5, profile.getImageUrl());
      createBookStmt.executeUpdate();
      try (ResultSet keys = createBookStmt.getGeneratedKeys()) {
        keys.next();
        System.out.println("true");
        return keys.getLong(1);
      }
    }
  }
  // [END create]

  // [START read]
  @Override
  public Profile readProfiles(String bookId) throws SQLException {
    final String readBookString = "SELECT * FROM profiles WHERE uemail = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement readBookStmt = conn.prepareStatement(readBookString)) {
      readBookStmt.setString(1, bookId);
      try (ResultSet keys = readBookStmt.executeQuery()) {
        keys.next();
        return new Profile.Builder()
            .password(keys.getString(Profile.PASSWORD))
            .createdBy(keys.getString(Profile.NICKNAME))

            .description(keys.getString(Profile.DESCRIPTION))
            .id(keys.getLong(Profile.ID))

            .title(keys.getString(Profile.UEMAIL))
            .imageUrl(keys.getString(Profile.IMAGE_URL))
            .build();
      }
    }
  }
  // [END read]

  // [START update]
  @Override
  public void updateProfile(Profile profile) throws SQLException {
    final String updateBookString = "UPDATE profiles SET uemail = ?, upassword = ?, nickname = ?, "
        + "description = ?,  imageUrl = ? WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement updateBookStmt = conn.prepareStatement(updateBookString)) {
      updateBookStmt.setString(2, profile.getAuthor());
      updateBookStmt.setString(3, profile.getCreatedBy());

      updateBookStmt.setString(4, profile.getDescription());

      updateBookStmt.setString(1, profile.getTitle());
      updateBookStmt.setString(5, profile.getImageUrl());
      updateBookStmt.setLong(6, profile.getId());
      updateBookStmt.executeUpdate();
    }
  }
  // [END update]

  // [START delete]
  @Override
  public void deleteProfile(Long bookId) throws SQLException {
    final String deleteBookString = "DELETE FROM profiles WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement deleteBookStmt = conn.prepareStatement(deleteBookString)) {
      deleteBookStmt.setLong(1, bookId);
      deleteBookStmt.executeUpdate();
    }
  }
  // [END delete]

  // [START listbooks]
  public boolean validateProfile(String uemail, String upassword) throws SQLException{
	  final String validateProfileString = "SELECT * FROM profiles WHERE uemail = ? and upassword = ?";
	  try(Connection conn = dataSource.getConnection();
			  PreparedStatement something = conn.prepareStatement(validateProfileString)){
		  something.setString(1, uemail);
		  something.setString(2, upassword);
		  try(ResultSet keys = something.executeQuery()){
			  if(keys.next()) {
				  return true;
			  }else return false;	  
		  }
	  }
  }
  
  @Override
  public Result<Profile> listProfiles(String cursor, boolean matching, long id) throws SQLException {
    int offset = 0;
    final String listProfilesString;
    if (cursor != null && !cursor.equals("")) {
      offset = Integer.parseInt(cursor);
    }
    if(!matching) {
    listProfilesString = "SELECT SQL_CALC_FOUND_ROWS uemail, upassword, nickname, description, "
        + " id,  imageUrl FROM profiles WHERE id != ? ORDER BY uemail ASC "
        + "LIMIT 1 OFFSET ?";
    }else {
    listProfilesString = "SELECT SQL_CALC_FOUND_ROWS p.uemail, p.nickname, p.description, p.id, p.imageUrl FROM"
    			+ "profiles p, matches m WHERE m.id1 = ? AND p.id = m.id2 ORDER BY nickname ASC LIMIT 10 OFFSET ?";
    }
    try (Connection conn = dataSource.getConnection();
         PreparedStatement listBooksStmt = conn.prepareStatement(listProfilesString)) {
      listBooksStmt.setLong(1, id);
      listBooksStmt.setInt(2, offset);
      List<Profile> resultBooks = new ArrayList<>();
      try (ResultSet rs = listBooksStmt.executeQuery()) {
        while (rs.next()) {
          Profile profile = new Profile.Builder()
              .password(rs.getString(Profile.PASSWORD))
              .createdBy(rs.getString(Profile.NICKNAME))

              .description(rs.getString(Profile.DESCRIPTION))
              .id(rs.getLong(Profile.ID))

              .title(rs.getString(Profile.UEMAIL))
              .imageUrl(rs.getString(Profile.IMAGE_URL))
              .build();
          resultBooks.add(profile);
         System.out.println(profile.getId());
        }
      }
      try (ResultSet rs = conn.createStatement().executeQuery("SELECT FOUND_ROWS()")) {
        int totalNumRows = 0;
        if (rs.next()) {
          totalNumRows = rs.getInt(1);
        }
        if (totalNumRows > offset + 1) {
          return new Result<>(resultBooks, Integer.toString(offset + 1));
        } else {
          return new Result<>(resultBooks);
        }
      }
    }
  }
public void deleteLike(Long decode, Long id) throws SQLException{
	final String deleteProfileString = "DELETE FROM likes1 WHERE from1 = ? AND to = ?";
    try (Connection conn = dataSource.getConnection();
            PreparedStatement deleteBookStmt = conn.prepareStatement(deleteProfileString)) {
         deleteBookStmt.setLong(1, id);
         deleteBookStmt.setLong(2, decode);
         deleteBookStmt.executeUpdate();
       }
}
  // [END listbooks]
public void insertMatch(Long decode, Long id) throws SQLException{
	//Delete from like then add to match
	deleteLike(decode, id);
	String likesString = "INSERT INTO matches"
			+"(id1, id2)"
			+"VALUES (?,?)";
    try (Connection conn = dataSource.getConnection();
            final PreparedStatement likeProfile = conn.prepareStatement(likesString,
                Statement.RETURN_GENERATED_KEYS)) {
    		likeProfile.setLong(1, decode);
    		likeProfile.setLong(2, id);
    		likeProfile.executeUpdate();
    	    try (ResultSet keys1 = likeProfile.getGeneratedKeys()) {
    	        	keys1.next();
    	        	System.out.println("true");
    	     }
    		}
    //Flip Flop
	likesString = "INSERT INTO matches"
			+"(id1, id2)"
			+"VALUES (?,?)";
    try (Connection conn = dataSource.getConnection();
            final PreparedStatement likeProfile = conn.prepareStatement(likesString,
                Statement.RETURN_GENERATED_KEYS)) {
    		likeProfile.setLong(2, decode);
    		likeProfile.setLong(1, id);
    		likeProfile.executeUpdate();
    	    try (ResultSet keys1 = likeProfile.getGeneratedKeys()) {
    	        	keys1.next();
    	        	System.out.println("true");
    	     }
    		}
		}
	

@Override
public boolean insertLike(Long decode, Long id) throws SQLException {
	boolean matched = false;
	//First check to see if the other person like them
	final String checkString = "SELECT * FROM likes1 WHERE id1 = ? AND id2 = ?";
	try(Connection conn = dataSource.getConnection();
			 PreparedStatement checking = conn.prepareStatement(checkString)){
				checking.setLong(2, decode);
				checking.setLong(1, id);
				try(ResultSet keys = checking.executeQuery()){
					if(keys.next()) {
						matched = true;
						insertMatch(decode, id);
					}else {
						final String likesString = "INSERT INTO likes1"
								+"(id1, id2)"
								+"VALUES (?,?)";
					    try ( 
					            final PreparedStatement likeProfile = conn.prepareStatement(likesString,
					                Statement.RETURN_GENERATED_KEYS)) {
					    		likeProfile.setLong(1, decode);
					    		likeProfile.setLong(2, id);
					    		likeProfile.executeUpdate();
					    	    try (ResultSet keys1 = likeProfile.getGeneratedKeys()) {
					    	        	keys1.next();
					    	        	System.out.println("true");
					    	     }
					    		}
							}
					}
			}
	return matched;
			
	}
}		
// [END example]
