<%--
  Created by IntelliJ IDEA.
  User: guillaume
  Date: 10/04/19
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Créer un utilisateur</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<form action="./register" method="post">

    <label> Nom </label>
    <input type="text" id="name" name="name"/>
    <br>

    <label> Email </label>
    <input type="text" id="email" name="email"/>
    <br>

    <label> Mot de passe </label>
    <input type="password" id="password" name="password"/>
    <br>

    <label> Société </label>
    <input type="text" id="company" name="company"/>
    <br>

    <label> Téléphone </label>
    <input type="text" id="phone" name="phone"/>
    <br>

    <label> Admin </label>
    <input type="checkbox" id="admin" name="admin"/>
    <br>

    <input type="submit" value="Submit">
</form>
<a href="./" name="accueil">Accueil</a>
</body>
</html>