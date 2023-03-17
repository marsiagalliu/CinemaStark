<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!-- c:out ; c:forEach ; c:if -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (like dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Create Product</title>
    <!-- Bootstrap -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous">

</head>

<body>
<div class="container">
    <!-- Beginning of Container -->
    <div
            class="d-flex flex-column justify-content-center align-items-center gap-2">
        <h1> Create Anime:</h1>
        <a href="/admin">Go Back</a>
    </div>
    <div
            class="d-flex justify-content-center gap-5 mt-3">
        <div class = "bg-light p-5">

            <%--@elvariable id="anime" type="com"--%>
            <form:form class="form d-flex flex-column gap-3" action="/anime/new"
                       method="post" modelAttribute="anime" enctype="multipart/form-data">
                <div
                        class=" d-flex flex-column justify-content-center align-items-center gap-2">
                    <form:label path="animeName">Anime Name: </form:label>
                    <form:errors path="animeName"/>
                    <form:input class="form-control" type="text" path="animeName"/>
                </div>
                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <form:label path="description">Anime Description: </form:label>
                    <form:errors path="description"/>
                    <form:input class="form-control" type="text" path="description"/>
                </div>
                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <form:label path="dateAired">Anime DateAired: </form:label>
                    <form:errors path="dateAired"/>
                    <form:input class="form-control" type="text" path="dateAired"/>
                </div>
                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <form:label path="studio">Anime Studio: </form:label>
                    <form:errors path="studio"/>
                    <form:input class="form-control" type="text" path="studio"/>
                </div>
                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <form:label path="status">Anime status: </form:label>
                    <form:errors path="status"/>
                    <form:input class="form-control" type="text" path="status"/>
                </div>
                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <form:label path="quality">Anime quality: </form:label>
                    <form:errors path="quality"/>
                    <form:input class="form-control" type="text" path="quality"/>
                </div>
                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <form:label path="type">Type: </form:label>
                    <form:errors path="type"/>
                    <form:input class="form-control" type="text" path="type"/>
                </div>
                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <form:label path="duration">Duration: </form:label>
                    <form:errors path="duration"/>
                    <form:input class="form-control" type="text" path="duration"/>
                </div>


                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <label>Anime Photos: </label>
                    <p>${massage}</p>
                    <input class="form-control" type="file" name="pic">
                </div>
                <div
                        class=" d-flex flex-column  justify-content-center align-items-center gap-2">
                    <label>Anime Video: </label>
                    <p>${massage}</p>
                    <input class="form-control" type="file" name="video">
                </div>
                <input class="w-50 btn btn-primary" type="submit" value="Submit" />
            </form:form>
        </div>


    </div>
</div>
<!-- End of Container -->
</body>

</html>