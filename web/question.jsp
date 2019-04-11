<%--
  Created by IntelliJ IDEA.
  User: guillaume
  Date: 10/04/19
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Créer une question</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div>TODO write content</div>
<form action="./question" method="post">

    <label for="questionnaire"> De quel questionnaire ? </label>
    <select id="questionnaire" name="questionnaire">
    </select>
    <br />

    <label for="enonce"> Enoncé :</label>
    <textarea id="enonce" name="enonce"></textarea>
    <br />

    <input type="submit" value="Submit">
</form>
</body>
</html>
