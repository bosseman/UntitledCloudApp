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

import com.example.getstarted.objects.Convo;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;

import com.google.cloud.datastore.Cursor;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// [START example]
public class DatastoreDao implements ProfileDao {

  // [START constructor]
  private Datastore datastore;
  private KeyFactory keyFactory;

  public DatastoreDao() {
    datastore = DatastoreOptions.getDefaultInstance().getService(); // Authorized Datastore service
    keyFactory = datastore.newKeyFactory().setKind("Profiles");      // Is used for creating keys later
  }
  // [END constructor]

  // [START entityToBook]
  public Profile entityToBook(Entity entity) {
    return new Profile.Builder()                                     // Convert to Profile form
        .password(entity.getString(Profile.PASSWORD))
        .description(entity.getString(Profile.DESCRIPTION))
        .id(entity.getKey().getId())
        .title(entity.getString(Profile.UEMAIL))
        .imageUrl(entity.contains(Profile.IMAGE_URL) ? entity.getString(Profile.IMAGE_URL) : null)
        .build();
  }
  // [END entityToBook]

  // [START create]
  @Override
  public Long createProfiles(Profile profile) {
    IncompleteKey key = keyFactory.newKey();          // Key will be assigned once written
    FullEntity<IncompleteKey> incBookEntity = Entity.newBuilder(key)  // Create the Entity
        .set(Profile.PASSWORD, profile.getAuthor())           // Add Property ("author", book.getAuthor())
        .set(Profile.DESCRIPTION, profile.getDescription())
        .set(Profile.UEMAIL, profile.getTitle())
        .set(Profile.IMAGE_URL, profile.getImageUrl())
        .build();
    Entity bookEntity = datastore.add(incBookEntity); // Save the Entity
    return bookEntity.getKey().getId();                     // The ID of the Key
  }
  // [END create]


  // [END read]

  // [START update]
  @Override
  public void updateProfile(Profile profile) {
    Key key = keyFactory.newKey(profile.getId());  // From a book, create a Key
    Entity entity = Entity.newBuilder(key)         // Convert Profile to an Entity
        .set(Profile.PASSWORD, profile.getAuthor())
        .set(Profile.DESCRIPTION, profile.getDescription())

        .set(Profile.UEMAIL, profile.getTitle())
        .set(Profile.IMAGE_URL, profile.getImageUrl())
        .build();
    datastore.update(entity);                   // Update the Entity
  }
  // [END update]

  // [START delete]
  @Override
  public void deleteProfile(Long bookId) {
    Key key = keyFactory.newKey(bookId);        // Create the Key
    datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]

  // [START entitiesToBooks]
  public List<Profile> entitiesToProfiles(QueryResults<Entity> resultList) {
    List<Profile> resultBooks = new ArrayList<>();
    while (resultList.hasNext()) {  // We still have data
      resultBooks.add(entityToBook(resultList.next()));      // Add the Profile to the List
    }
    return resultBooks;
  }
  // [END entitiesToBooks]

  // [START listbooks]
  @Override
  public Result<Profile> listProfiles(String startCursorString, boolean matching, long id) {
    Cursor startCursor = null;
    if (startCursorString != null && !startCursorString.equals("")) {
      startCursor = Cursor.fromUrlSafe(startCursorString);    // Where we left off
    }
    Query<Entity> query = Query.newEntityQueryBuilder()       // Build the Query
        .setKind("Profiles")                                     // We only care about Books
        .setLimit(10)                                         // Only show 10 at a time
        .setStartCursor(startCursor)                          // Where we left off
        .setOrderBy(OrderBy.asc(Profile.UEMAIL))                  // Use default Index "title"
        .build();
    QueryResults<Entity> resultList = datastore.run(query);   // Run the query
    List<Profile> resultBooks = entitiesToProfiles(resultList);     // Retrieve and convert Entities
    Cursor cursor = resultList.getCursorAfter();              // Where to start next time
    if (cursor != null && resultBooks.size() == 10) {         // Are we paging? Save Cursor
      String cursorString = cursor.toUrlSafe();               // Cursors are WebSafe
      return new Result<>(resultBooks, cursorString);
    } else {
      return new Result<>(resultBooks);
    }
  }
  // [END listbooks]

@Override
public boolean validateProfile(String uemail, String upassword) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Profile readProfiles(String bookId) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean insertLike(Long decode, Long id) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Result<Convo> listConversation(Long myId, Long userId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void insertMessage(long myId, long userId, String message) {
	// TODO Auto-generated method stub
	
}
}
// [END example]
