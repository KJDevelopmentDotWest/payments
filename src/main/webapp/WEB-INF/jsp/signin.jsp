<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="greeting" var="greeting"/>
<fmt:message bundle="${loc}" key="newto" var="newto"/>
<fmt:message bundle="${loc}" key="signin" var="signin"/>
<fmt:message bundle="${loc}" key="signup" var="signup"/>

<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/signin.css"%></style>
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
        <style><%@include file="/WEB-INF/css/navbar.css"%></style>
    </head>

    <body >
        <form class="top-right" action="/payments?command=change_language" method="post" autocomplete="off">
            <div class="btn-group" role="group"action="/payments?command=signout">
                <button type="submit" class="btn btn-primary" name="lang" value="eng" id="btnradio1" autocomplete="off">Eng</button>
                <button type="submit" class="btn btn-primary" name="lang" value="ru" id="btnradio2" autocomplete="off">Ru</button>
            </div>
        </form>
        <main class="form-signin form-margin-no-navbar" align="center">
            <form action="/payments?command=signin" method="post">
                <h1 class="h3 mb-3 fw-normal">${greeting}</h1>
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInput" placeholder="Login" name="login">
                    <label for="floatingInput">${login}</label>
                </div>
                <div class="form-floating">
                    <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
                    <label for="floatingPassword">${password}</label>
                </div>
                ${newto} <a href="/payments?command=show_create_user">${signup}</a>
                <button class="w-100 btn btn-lg btn-primary" type="submit">${signin}</button>
            </form>
        </main>
    </body>
</html>
