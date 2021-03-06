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

import java.sql.SQLException;

// [START example]
public interface ProfileDao {
  Long createProfiles(Profile profile) throws SQLException;

  Profile readProfiles(String bookId) throws SQLException;

  void updateProfile(Profile profile) throws SQLException;

  void deleteProfile(Long bookId) throws SQLException;
  
  boolean validateProfile(String uemail, String upassword) throws SQLException;

  Result<Profile> listProfiles(String startCursor, boolean matching, long id) throws SQLException;

  boolean insertLike(Long decode, Long id)throws SQLException;

Result<Convo> listConversation(Long myId, Long userId) throws SQLException;

void insertMessage(long myId, long userId, String message)throws SQLException;
}
// [END example]
