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
    <input type="text" id="name" name="name" value="<c:out value="${user.name}"/>" />

    <br>

    <label> Email </label>
    <input type="text" id="email" name="email" value="<c:out value="${user.email}"/>"/>

    <span>${form.errors['email']}</span>
    <br>

    <label> Mot de passe </label>
    <input type="password" id="password" name="password"/>

    <span>${form.errors['password']}</span>
    <br>

    <label> Société </label>
    <input type="text" id="company" name="company" value="<c:out value="${user.company}"/>"/>
    <br>

    <label> Téléphone </label>
    <input type="text" id="phone" name="phone" value="<c:out value="${user.phone_number}"/>"/>
    <br>

    <label> Admin </label>
    <input type="checkbox" id="admin" name="admin" value="<c:out value="${user.is_admin}"/>"/>
    <br>

    <input type="submit" value="Submit">
    <br />

    <p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p>
</form>
<a href="./" name="accueil">Accueil</a>
</body>
</html>