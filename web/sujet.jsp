<%--
  Created by IntelliJ IDEA.
  User: guillaume
  Date: 11/04/19
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Créer un sujet</title>
</head>
<body>
<form action="./sujet" method="post">

    <label for="nom"> Nom du sujet :</label>
    <input type="text" id="nom" name="nom"/>
    <br />

    <input type="submit" value="Submit">
</form>
</body>
</html>
