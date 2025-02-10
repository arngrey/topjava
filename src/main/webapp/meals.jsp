<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<table>
    <tr>
        <td style="text-align:center;">Date</td>
        <td style="text-align:center;">Description</td>
        <td style="text-align:center;">Calories</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;"></td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <c:choose>
            <c:when test="${!meal.excess}">
                <tr style="color:green;border-top:1px solid black;">
                    <td>${meal.formattedDateTime}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>update</td>
                    <td>delete</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color:red;">
                    <td>${meal.formattedDateTime}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>update</td>
                    <td>delete</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>
</body>
</html>