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
<header>
    <c:set var="iteration" scope="session" value="${(empty iteration) ? '0' : iteration}"/>
</header>

<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/styles.css"/>



<c:set var="profiles" value="${['A','B','C','D']}" scope="session"/>
<c:set var="length" value="${fn:length(profiles)}" scope="session"/>

<div>
    <h3 align="center">Matching</h3>
    <a href="/mainActions" class="btn btn-success btnMenu">
        <i class="glyphicon glyphicon-link"></i>
        Main menu
    </a>
    <c:choose>
        <c:when test="${empty profiles}">
            <div class="text-center">
                <img class="rounded paddingMatching"
                     src="${fn:escapeXml(not empty profile.imageUrl?profile.imageUrl:'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS12CPzYdB3k0r251NyJR9WLelCIxb11LhFfShMWwFPG5Kucgukgg&s')}">
                <p align="center">No profiles found close to you!</p>
            </div>
        </c:when>
        <c:otherwise>
            <form action="/like" method="POST">
                <div class="media">
                    <c:out value="${profiles[iteration]}" escapeXml="false"/>
                    <div class="text-center">
                        <img class="paddingMatching" src='http://placekitten.com/g/128/192'>
                    </div>
                    <div class="media-body">
                        <h4>${fn:escapeXml(profile.getCreatedBy())}</h4>
                        <p>${fn:escapeXml(profile.getDescription())}</p>
                    </div>
                </div>
<%--            </c:forEach>--%>
                <div class="text-center paddingMatching">
                    <input type="text" name="id" value="${id}"/>
                    <input type="text" name="cursor" value="${cursor}">x
                </div>
                <div class="text-center">
                    <c:choose>
                        <c:when test="${iteration < length - 1}">
                            <button name="btnNextProfile" class="btn btn-success matchingButtons">Next Profile</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-success matchingButtons disabled">No more Profiles</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="submit" class="btn btn-success matchingButtons">Like</button>
                </div>
            </form>
            <br>
            <p>${matched}</p>
        </c:otherwise>
    </c:choose>
</div>
<!-- [END list] -->
