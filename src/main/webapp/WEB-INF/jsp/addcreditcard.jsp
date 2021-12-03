<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="main" var="main"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>
<fmt:message bundle="${loc}" key="name" var="name"/>
<fmt:message bundle="${loc}" key="number" var="number"/>
<fmt:message bundle="${loc}" key="filldata" var="filldata"/>
<fmt:message bundle="${loc}" key="addcreditcard" var="addcreditcard"/>
<fmt:message bundle="${loc}" key="nametooshort" var="nametooshort"/>
<fmt:message bundle="${loc}" key="nametoolong" var="nametoolong"/>
<fmt:message bundle="${loc}" key="numberwronglength" var="numberwronglength"/>

<html>
    <head>
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/signin.css"%></style>
        <style><%@include file="/WEB-INF/css/navbar.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
    </head>

    <script>
        function validateform(){
        var name = document.getElementById("name").value;
        var number = document.getElementById("number").value;
        var nameShortFlag = (name == null || name == "" || name.length < 2);
        var nameLongFlag = (name.length > 15);
        var numberFlag = (number == null || number == "" || number.length != 16 || Number(number) < 0 || !Number.isInteger(Number(number)));
            if(nameShortFlag){
                document.getElementById("nametooshort").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("nametooshort").style.display = "none";
            }
            if(nameLongFlag){
                document.getElementById("nametoolong").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("nametoolong").style.display = "none";
            }
            if (numberFlag){
                document.getElementById("numberwronglength").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("numberwronglength").style.display = "none";
            }
            if(!(nameShortFlag || nameLongFlag || numberFlag)){
                document.getElementById("submitbutton").disabled = false;
            }
        };
        function allowOnlyEngl(){
            var name = document.getElementById("name");
            if (!/^[a-zA-Z]*$/g.test(name.value)) {
                name.value = name.value.slice(0, -1);
                return false;
            }
        };
        function onLoad(){
            var message = '${incorrect}';
            if(message != null && message.length > 0){
                alert(message);
            }
        }
    </script>

    <body class="d-flex flex-column h-100" onload="onLoad()">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="/payments?command=show_account">${main}</a>
                        </li>
                        <li class="nav-item ">
                            <a class="nav-link" href="/payments?command=signout">${logout}</a>
                        </li>
                    </ul>
                    <c:choose>
                        <c:when test="${sessionScope.lang == 'ru'}">
                            <form action="/payments?command=change_language" method="post" autocomplete="off">
                                <div class="btn-group" role="group"action="/payments?command=signout">
                                    <button type="submit" class="btn btn-primary" name="lang" value="eng" id="btnradio1" autocomplete="off">Eng</button>
                                    <button type="submit" class="btn btn-primary active" name="lang" value="ru" id="btnradio2" autocomplete="off">Ru</button>
                                </div>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="/payments?command=change_language" method="post" autocomplete="off">
                                <div class="btn-group" role="group"action="/payments?command=signout">
                                    <button type="submit" class="btn btn-primary active" name="lang" value="eng" id="btnradio1" autocomplete="off">Eng</button>
                                    <button type="submit" class="btn btn-primary" name="lang" value="ru" id="btnradio2" autocomplete="off">Ru</button>
                                </div>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </nav>
        <div class="primary-margin">
            <main class="form-signin form-margin-navbar" align="center">
                <form action="/payments?command=add_credit_card" method="post" autocomplete="off">
                    <h1 class="h5 mb-3">${filldata}</h1>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="name" placeholder="Password" name="name" oninput="validateform();allowOnlyEngl()">
                        <label for="name">${name}</label>
                        <div class="hidden error-message" id="nametooshort">
                            ${nametooshort}
                        </div>
                        <div class="hidden error-message" id="nametoolong">
                            ${nametoolong}
                        </div>
                    </div>
                    <div class="form-floating">
                        <input type="number" class="form-control" id="number" placeholder="Password" name="cardNumber" oninput="validateform()">
                        <label for="number">${number}</label>
                        <div class="hidden error-message" id="numberwronglength">
                            ${numberwronglength}
                        </div>
                    </div>
                    <button id="submitbutton" class="w-100 btn btn-lg btn-primary" type="submit">${addcreditcard}</button>
                </form>
            </main>
        </div>
        <jsp:include page="/WEB-INF/jsp/footer.html"></jsp:include>
    </body>
<html>