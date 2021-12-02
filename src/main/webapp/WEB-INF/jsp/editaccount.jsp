<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "tags" prefix = "m" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="main" var="main"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>
<fmt:message bundle="${loc}" key="name" var="name"/>
<fmt:message bundle="${loc}" key="surname" var="surname"/>
<fmt:message bundle="${loc}" key="savechanges" var="savechanges"/>
<fmt:message bundle="${loc}" key="choosenewprofilepicture" var="choosenewprofilepicture"/>
<fmt:message bundle="${loc}" key="deleteuser" var="deleteuser"/>
<fmt:message bundle="${loc}" key="nametooshort" var="nametooshort"/>
<fmt:message bundle="${loc}" key="surnametooshort" var="surnametooshort"/>
<fmt:message bundle="${loc}" key="nametoolong" var="nametoolong"/>
<fmt:message bundle="${loc}" key="surnametoolong" var="surnametoolong"/>

<html>
    <head>
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/grid.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
        <style><%@include file="/WEB-INF/css/navbar.css"%></style>
    </head>

    <script>
        function validateform(){
            var name = document.getElementById("name").value;
            var surname = document.getElementById("surname").value;
            var nameShortFlag = (name == null || name == "" || name.length < 2);
            var surnameShortFlag = (surname == null || surname == "" || surname.length < 2);
            var nameLongFlag = (name.length > 15);
            var surnameLongFlag = (surname == null || surname == "" || surname.length > 15);
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
            if (surnameShortFlag){
                document.getElementById("surnametooshort").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("surnametooshort").style.display = "none";
            }
            if (surnameLongFlag){
                document.getElementById("surnametoolong").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("surnametoolong").style.display = "none";
            }
            if(!(nameShortFlag || surnameShortFlag || nameLongFlag || surnameLongFlag)){
                document.getElementById("submitbutton").disabled = false;
            }
        };
        function allowOnlyEngl(){
            var name = document.getElementById("name");
            var surname = document.getElementById("surname");
            if (!/^[a-zA-Z]*$/g.test(name.value)) {
                name.value = name.value.slice(0, -1);
                return false;
            }
            if (!/^[a-zA-Z]*$/g.test(surname.value)) {
                surname.value = surname.value.slice(0, -1);
                return false;
            }
        };
    </script>

    <body class="d-flex flex-column h-100">
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
            <main class="account-edit" align="center">
                <a href="/payments?command=block_user"><button class="btn btn-primary" >${deleteuser}</button></a>
                <form action="/payments?command=commit_account_changes" method="post" autocomplete="off">
                    <h1 class="h3 mb-3">${editaccount}</h1>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="name" placeholder="Password" name="name" value="${requestScope.account.getName()}" oninput="validateform();allowOnlyEngl()">
                        <label for="name">${name}</label>
                        <div class="hidden error-message" id="nametooshort">
                            ${nametooshort}
                        </div>
                        <div class="hidden error-message" id="nametoolong">
                            ${nametoolong}
                        </div>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="surname" placeholder="Password" name="surname" value="${requestScope.account.getSurname()}" oninput="validateform();allowOnlyEngl()">
                        <label for="surname">${surname}</label>
                        <div class="hidden error-message" id="surnametooshort">
                            ${surnametooshort}
                        </div>
                        <div class="hidden error-message" id="surnametoolong">
                            ${surnametoolong}
                        </div>
                    </div>
                    <input type="hidden" name="accountId" value="${requestScope.account.getId()}">
                    <br/>
                    <h3>${choosenewprofilepicture}</h3>
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="1">
                                    <m:profileimage width="100" height="100" pictureId="${1}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="2">
                                    <m:profileimage width="100" height="100" pictureId="${2}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="3">
                                    <m:profileimage width="100" height="100" pictureId="${3}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="4">
                                    <m:profileimage width="100" height="100" pictureId="${4}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="5">
                                    <m:profileimage width="100" height="100" pictureId="${5}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="6">
                                    <m:profileimage width="100" height="100" pictureId="${6}"/>
                                </input>
                            </div>
                        </div>
                        <br/>
                        <div class="row">
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="7">
                                    <m:profileimage width="100" height="100" pictureId="${7}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="8">
                                    <m:profileimage width="100" height="100" pictureId="${8}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="9">
                                    <m:profileimage width="100" height="100" pictureId="${9}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="10">
                                    <m:profileimage width="100" height="100" pictureId="${10}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="11">
                                    <m:profileimage width="100" height="100" pictureId="${11}"/>
                                </input>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary" type="radio" name="pictureId" value="12">
                                    <m:profileimage width="100" height="100" pictureId="${12}"/>
                                </input>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <button id="submitbutton" type="submit" class="btn btn-primary" >${savechanges}</button>
                </form>
            </main>
        </div>
        <jsp:include page="/WEB-INF/jsp/footer.html"></jsp:include>
    </body>
<html>