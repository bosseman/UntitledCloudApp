<!--
Copyright 2016 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="styles.css"/>
<div class="container">
    <h3>Matching</h3>
    <a href="/mainActions" class="btn btn-success btn-sm">
        <i class="glyphicon glyphicon-plus"></i>
        Main menu
    </a>
    <c:choose>
        <c:when test="${empty books}">
            <p>No profiles found</p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${books}" var="profile">
                <form action = "/like" method = "POST">
                <div class="media">
                    <div class="media-left">
                        <img alt="ahhh"
                             src="${fn:escapeXml(not empty profile.imageUrl?profile.imageUrl:'http://placekitten.com/g/128/192')}">
                    </div>
                    <div class="media-body">
                        <h4>${fn:escapeXml(profile.getCreatedBy())}</h4>
                        <p>${fn:escapeXml(profile.getDescription())}</p>
                    </div>
                </div>
            </c:forEach>
            <input type="text" name="id" value="${id}"/>
            <input type="text" name="cursor" value="${cursor}">
            <br>
            <button type="submit">Like</button>
            </form>
            <p>${macthed}</p>
            <c:if test="${not empty cursor}">
                <nav>
                    <ul class="pager">
                        <li><a href="?cursor=${fn:escapeXml(cursor)}">Next Profile</a></li>
                    </ul>
                </nav>
            </c:if>
            <c:if test="${empty cursor}">
                <nav>
                    <p>No more profiles</p>
                </nav>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>
<!-- [END list] -->
