
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
  <form action = "/openConvo" method = POST>
  <div class="media">
      <div class="media-left">
        <img alt="ahhh" src="${fn:escapeXml(not empty profile.imageUrl?profile.imageUrl:'http://placekitten.com/g/128/192')}">
      </div>
      <div class="media-body">
        <h4>${fn:escapeXml(profile.getCreatedBy())}</h4>
      </div>
      <button type = "submit" class = "btn" name = "openC" value = "${id}">Open Conversations</button> 
  </div>
   </form>
   </c:forEach>
  <c:if test="${not empty cursor}">
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
   </c:if>
</c:otherwise>
  </c:choose>
</div>
<!-- [END list] -->
