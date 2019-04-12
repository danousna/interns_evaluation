<%--
  Created by IntelliJ IDEA.
  User: guillaume
  Date: 11/04/19
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Créer une réponse</title>
</head>
<body>
<form action="./reponse" method="post">

    <label for="question"> De quel question ? </label>
    <select id="question" name="question">
    </select>
    <br />

    <label for="reponse"> Réponse :</label>
    <input type="text" id="reponse" name="reponse"/>
    <br />

    <input type="checkbox" id="correct" name="correct"/>
    <label for="correct">Correct</label>
    <br />

    <input type="submit" value="Submit">
</form>
</body>
</html>
