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

package com.example.getstarted.objects;

// [START example]
public class Profile {
  // [START book]
  private String uemail;
  private String upassword;
  private String nickname;
  private String description;
  private Long id;
  private String imageUrl;
  // [END book]
  // [START keys]
  public static final String PASSWORD = "upassword";
  public static final String NICKNAME = "nickname";
  public static final String DESCRIPTION = "description";
  public static final String ID = "id";
  public static final String UEMAIL = "uemail";
  public static final String IMAGE_URL = "imageUrl";

  // [END keys]

  // [START constructor]
  // We use a Builder pattern here to simplify and standardize construction of Profile objects.
  private Profile(Builder builder) {
    this.uemail = builder.email;
    this.upassword = builder.password;
    this.nickname = builder.nickName;
    this.description = builder.description;
    this.id = builder.id;
    this.imageUrl = builder.imageUrl;
  }

  // [END constructor]
  // [START builder]
  public static class Builder {
    private String email;
    private String password;
    private String nickName;
    private String description;
    private Long id;
    private String imageUrl;

    public Builder title(String title) {
      this.email = title;
      return this;
    }

    public Builder password(String author) {
      this.password = author;
      return this;
    }

    public Builder createdBy(String createdBy) {
      this.nickName = createdBy;
      return this;
    }
    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder imageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    public Profile build() {
      return new Profile(this);
    }
  }

  public String getTitle() {
    return uemail;
  }

  public void setTitle(String title) {
    this.uemail = title;
  }

  public String getAuthor() {
    return upassword;
  }

  public void setAuthor(String author) {
    this.upassword = author;
  }

  public String getCreatedBy() {
    return nickname;
  }

  public void setCreatedBy(String createdBy) {
    this.nickname = createdBy;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  // [END builder]
  @Override
  public String toString() {
    return
        "Title: " + uemail + ", Author: " + upassword + ", Published date: " 
            + ", Added by: " + nickname;
  }
}
// [END example]
