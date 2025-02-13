<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Add meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add meal</h2>
<br />
<form method="POST" style="display:flex;flex-direction:column;width:400px;">
    <div style="display:flex;width:100%;background-color:lightgrey;">
        <label for="dateTime" style="display:flex;flex:1;">DateTime</label>
        <div style="display:flex;flex:1;"><input id="dateTime" name="dateTime" type="datetime-local" /></div>
    </div>
    <div style="display:flex;width:100%;background-color:lightgrey;margin-top:20px;">
        <label for="description" style="display:flex;flex:1;">Description</label>
        <div style="display:flex;flex:1;"><input id="description" name="description" type="text" /></div>
    </div>
    <div style="display:flex;width:100%;background-color:lightgrey;margin-top:20px;">
        <label for="calories" style="display:flex;flex:1;">Calories</label>
        <div style="display:flex;flex:1;"><input id="calories" name="calories" type="number" /></div>
    </div>
    <div style="display:flex;margin-top:40px;">
        <button type="submit">Save</button>
        <button type="button" style="margin-left:10px;" onclick="window.history.back();">Cancel</button>
    </div>
</form>
<br>
</body>
</html>