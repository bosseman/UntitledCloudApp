<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<br>
<h3>${nickname}</h3>
<div id="centeredButtons">
    <form action="/matching">
        <button type="submit" class="btn">Match</button>
    </form>
    <form action = "/conversations">
        <button type="submit" class="btn">Conversations</button>
    </form>
     <form action = "/update">
        <button type="submit"  class="btn">Update Profile</button>
    </form>
</div>
