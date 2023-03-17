<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Anime</title>
  <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
  <!-- YOUR own local CSS -->
  <link rel="stylesheet" href="css/style.css"/>
  <!-- For any Bootstrap that uses JS-->
  <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
  <h1>${category.name}</h1>
  <p><a href="/">Home</a></p>
  <hr>

  <h3>You Movies In ${category.name} Categories</h3>
  <ul>
    <c:forEach var="movie" items="${assingMovie}">
      <li><c:out value="${movie.animeName}"></c:out></li>
    </c:forEach>
  </ul>
  <hr>
  <form action="/categories/${id}" method="post">
    <h4>Add Movies:</h4>
    <select name="productId" id="productId" class="input">
      <c:forEach var="movie" items="${unassingMovie}">
        <option value="${movie.id}">${movie.animeName}</option>
      </c:forEach>
    </select>
    <input class="input" class="button" type="submit" value="Add"/>
  </form>
</div>
</body>
</html>