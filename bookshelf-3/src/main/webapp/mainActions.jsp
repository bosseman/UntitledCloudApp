<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="styles.css"/>

<br>

<div id="centeredButtons">
    <h3>Welcome ${nickname} !</h3>
    <form action="/matching">
        <%--<button type="submit" class="btn">Match</button>--%>
        <button type="submit" class="btn btn-success btnMenu">Match</button>
    </form>
    <form action="/conversations">
        <%--<button type="submit" class="btn">Conversations</button>--%>
        <button type="submit" class="btn btn-success btnMenu">Conversations</button>
    </form>
    <form action="/update">
        <%--<button type="submit" class="btn">Update Profile</button>--%>
        <button type="submit" class="btn btn-success btnMenu">Update Profile</button>
    </form>
    <form action="/logout" method=POST>
        <%--<button type="submit" class="btn">Update Profile</button>--%>
        <button type="submit" class="btn btn-success btnMenu">Logout</button>
    </form>
</div>
