<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css"/>
<div class="container" id="centeredActions">
    <h3 align="center">Conversations</h3>
    <a href="/mainActions" class="btn btn-success btnMenu">
        <i class="glyphicon glyphicon-link"></i>
        Main menu
    </a>
    <c:choose>
        <c:when test="${empty books}">
            <div class="text-center">
                <img class="rounded paddingMatching"
                     src="${fn:escapeXml(not empty profile.imageUrl?book.imageUrl:'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS12CPzYdB3k0r251NyJR9WLelCIxb11LhFfShMWwFPG5Kucgukgg&s')}">
                <p align="center">No matches with which a conversation is possible!</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${books}" var="profile">
                <form action="/OpenConvo">
                    <div class="media">
                        <div class="media-left">
                            <img alt="ahhh"
                                 src="${fn:escapeXml(not empty profile.imageUrl?profile.imageUrl:'http://placekitten.com/g/128/192')}">
                        </div>
                        <div class="media-body">
                            <button type="submit" class="conversationButton btn-block" name="openC"
                                    value="${profile.id}">
                                Click here to open the conversation with ${fn:escapeXml(profile.getCreatedBy())}
                            </button>
                        </div>
                        <hr>
                    </div>
                </form>
            </c:forEach>
        </c:otherwise>
    </c:choose>
<%--            <c:if test="${not empty cursor}">
                <nav>
                    <ul class="pager">
                        <li><a href="?cursor=${fn:escapeXml(cursor)}">Load Next</a></li>
                    </ul>
                </nav>
            </c:if>
            <c:if test="${empty cursor}">
                <nav>
                    <p>No more profiles</p>
                </nav>
            </c:if>--%>

</div>
<!-- [END list] -->
