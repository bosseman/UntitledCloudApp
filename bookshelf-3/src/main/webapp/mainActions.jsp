<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css"/>

<br>
<h3>${nickname}</h3>
<div id="centeredButtons">
    <form action="/matching">
        <button type="submit" class="btn btn-success btnMenu">Match</button>
    </form>
    <form action = "/conversations">
        <button type="submit" class="btn btn-success btnMenu">Conversations</button>
    </form>
     <form action = "/update">
        <button type="submit"  class="btn btn-success btnMenu">Update Profile</button>
    </form>
</div>
