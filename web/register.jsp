<%--
  Created by IntelliJ IDEA.
  User: guillaume
  Date: 10/04/19
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Créer un utilisateur</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<form action="./register" method="post">

    <label> Nom </label>
    <input type="text" name="name" value="<c:out value="${user.name}"/>" />

    <br>

    <label> Email </label>
    <input type="text" name="email" value="<c:out value="${user.email}"/>"/>

    <span>${form.errors['email']}</span>
    <br>

    <label> Mot de passe </label>
    <input type="password" name="password"/>
    <span>${form.errors['password']}</span>
    <br />

    <label> Confirmez le mot de passe </label>
    <input type="password" name="password_confirmation"/>
    <br>

    <label> Société </label>
    <input type="text" name="company" value="<c:out value="${user.company}"/>"/>
    <br>

    <label> Téléphone </label>
    <input type="text" name="phone" value="<c:out value="${user.phone}"/>"/>
    <br>

    <label> Admin </label>
    <input type="checkbox" name="admin" value="<c:out value="${user.is_admin}"/>"/>
    <br>

    <input type="submit" value="Submit">
    <br />

    <p class="${empty form.errors ? 'succes' : 'erreur'}">${form.result}</p>
</form>
<a href="./" name="accueil">Accueil</a>
</body>
</html>