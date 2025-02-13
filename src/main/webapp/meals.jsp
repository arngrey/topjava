<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<p><a href="meals?action=create">Add meal</a></p>
<table>
    <tr>
        <td style="text-align:center;">Date</td>
        <td style="text-align:center;">Description</td>
        <td style="text-align:center;">Calories</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;"></td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr style="<c:out value="${meal.excess ? 'color:red;' : 'color:green;'}" />">
            <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Edit</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>