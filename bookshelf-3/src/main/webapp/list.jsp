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
<div class="container" id = "centeredActions">
<h3>Matching</h3>
<a href="/mainActions" class="btn btn-success btnMenu">
    <i class="glyphicon glyphicon-link"></i>
    Main menu
</a>
<c:choose>
    <c:when test="${empty books}">
        <p>No profiles found</p>
    </c:when>
    <c:otherwise>
        <c:forEach items="${books}" var="profile">
            <form action = "/like" method = "POST">
            <%--<div id="centeredButtons">--%>
            <div class="media">
                <div class="media-left">
                    <img class="paddingMatching" style="max-width: 100%"
                         src="${fn:escapeXml(not empty profile.imageUrl?profile.imageUrl:'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS12CPzYdB3k0r251NyJR9WLelCIxb11LhFfShMWwFPG5Kucgukgg&s')}">
                </div>

                <h4 align="center">${fn:escapeXml(profile.getCreatedBy())}</h4>
                <p align="center">${fn:escapeXml(profile.getDescription())}</p>

            </div>
        </c:forEach>
        <input type="hidden" name="id" value="${id}"/>
        <input type="hidden" name="cursor" value="${cursor}"/>
        <br>
        <button type="submit" class="btn btn-success btnMenu">Like</button>
        </form>
        <p>${matched}</p>
        <c:if test="${not empty cursor}">
            <a class="btn btn-success btnMenu" href="?cursor=${fn:escapeXml(cursor)}"> Next Profile </a>
        </c:if>
        <c:if test="${empty cursor}">
            <nav>
            <p>No more profiles available</p>
        </c:if>
        <%--</div>--%>
    </c:otherwise>
</c:choose>
</div>
<!-- [END list] -->
