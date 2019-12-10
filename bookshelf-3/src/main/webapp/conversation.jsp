<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<div class="container" id="centeredActions">
    <h3 align="center">Conversations</h3>
    <a href="/mainActions" class="btn btn-success btnMenu">
        <i class="glyphicon glyphicon-link"></i>
        Main menu
    </a>
    
    <c:choose>
        <c:when test="${empty conversation}">
            <div class="text-center">
                <img class="rounded paddingMatching"
                     src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS12CPzYdB3k0r251NyJR9WLelCIxb11LhFfShMWwFPG5Kucgukgg&s">
                <p align="center">No messages found for this conversation!</p>
            </div>
        </c:when>
        <c:otherwise>
            <div>
                <div class="conversationContainer">
                    <c:forEach items="${conversation}" var="message">
                        <div class="media">
                                <%--<div class="media-left">
                                    <img alt="ahhh"
                                         src="${fn:escapeXml(not empty message.imageUrl?profile.imageUrl:'http://placekitten.com/g/128/192')}">
                                </div>--%>
                            <div class="media-body">
                                <h4 class="mt-0">${message.fromId} ${message.time}</h4>
                                <p>${message.message}</p>
                            </div>
                            <hr>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="messageContainer">
        <form action="/sendMessage" method=POST>
            <button type="submit" class="btn btn-success" style="float:right;" name="openC">
                Send message!
            </button>
            <div style="overflow: hidden; padding-right: .5em;">
                <textarea class="form-control" name="inputMessage" rows="2" cols="50"></textarea>
            </div>
        </form>
    </div>

    <%--  <c:if test="${not empty cursor}">
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
<script type="text/javascript">
    $("img").each(function () {
        var $img = $(this);
        $img.width($img.width() * .5);
    });
</script>
<!-- [END list] -->
