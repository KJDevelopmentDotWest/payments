<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="greeting" var="greeting"/>
<fmt:message bundle="${loc}" key="newto" var="newto"/>
<fmt:message bundle="${loc}" key="signin" var="signin"/>
<fmt:message bundle="${loc}" key="signup" var="signup"/>
<fmt:message bundle="${loc}" key="logintooshort" var="logintooshort"/>
<fmt:message bundle="${loc}" key="passwordtooshort" var="passwordtooshort"/>
<fmt:message bundle="${loc}" key="logintoolong" var="logintoolong"/>
<fmt:message bundle="${loc}" key="passwordtoolong" var="passwordtoolong"/>

<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/signin.css"%></style>
        <style><%@include file="/WEB-INF/css/navbar.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
    </head>

    <script>
        function validateform(){
            var login = document.getElementById("login").value;
            var password = document.getElementById("password").value;
            var loginShortFlag = (login == null || login == "" || login.length < 3);
            var loginLongFlag = (login.length > 15);
            var passwordShortFlag = (password == null || password == "" || password.length < 5);
            var passwordLongFlag = (password.length > 15);

            if (loginShortFlag){
                document.getElementById("logintooshort").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("logintooshort").style.display = "none";
            }
            if(passwordShortFlag){
                document.getElementById("passwordtooshort").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("passwordtooshort").style.display = "none";
            }
            if (loginLongFlag){
                document.getElementById("logintoolong").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("logintoolong").style.display = "none";
            }
            if(passwordLongFlag){
                document.getElementById("passwordtoolong").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("passwordtoolong").style.display = "none";
            }
            if(!(loginShortFlag || passwordShortFlag || loginLongFlag || passwordLongFlag)){
                document.getElementById("submitbutton").disabled = false;
            }
        };
        function allowOnlyEngl(){
            var login = document.getElementById("login");
            var password = document.getElementById("password");
            if (!/^[a-zA-Z0-9]*$/g.test(login.value)) {
                login.value = login.value.slice(0, -1);
                return false;
            }
            if (!/^[a-zA-Z0-9]*$/g.test(password.value)) {
                password.value = password.value.slice(0, -1);
                return false;
            }
        };
        function tempAlert() {
            var message = '${incorrect}';
            if(message != null && message.length > 0){
                var el = document.createElement("div");
                el.setAttribute("style","position: absolute; text-align: center; top:70%; margin-right:10%; right:0px");
                el.classList.add('alert');
                el.classList.add('alert-danger');
                el.innerHTML = message;
                setTimeout(function(){
                    el.parentNode.removeChild(el);
                },3000);
                document.body.appendChild(el);
            }
        };
    </script>

    <body class="d-flex flex-column h-100" onload="tempAlert()">
        <form class="top-right" action="/payments?command=change_language" method="post" autocomplete="off">
            <div class="btn-group" role="group"action="/payments?command=signout">
                <button type="submit" class="btn btn-primary" name="lang" value="eng" id="btnradio1" autocomplete="off">Eng</button>
                <button type="submit" class="btn btn-primary" name="lang" value="ru" id="btnradio2" autocomplete="off">Ru</button>
            </div>
        </form>
        <div class="primary-margin">
            <main class="form-signin form-margin-no-navbar" align="center">
                <form id= "form" action="/payments?command=signin" method="post">
                    <h1 class="h3 mb-3 fw-normal">${greeting}</h1>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="login" placeholder="Login" name="login"  oninput="validateform();allowOnlyEngl()">
                        <label for="login">${login}</label>
                        <div class="hidden error-message" id="logintooshort">
                            ${logintooshort}
                        </div>
                        <div class="hidden error-message" id="logintoolong">
                            ${logintoolong}
                        </div>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" id="password" placeholder="Password" name="password"  oninput="validateform();allowOnlyEngl()">
                        <label for="password">${password}</label>
                        <div class="hidden error-message" id="passwordtooshort">
                            ${passwordtooshort}
                        </div>
                        <div class="hidden error-message" id="passwordtoolong">
                            ${passwordtoolong}
                        </div>
                    </div>
                    ${newto} <a href="/payments?command=show_create_user">${signup}</a>
                    <button id="submitbutton" class="w-100 btn btn-lg btn-primary" type="submit">${signin}</button>
                </form>
            </main>
        </div>
        <jsp:include page="/WEB-INF/jsp/footer.html"></jsp:include>
    </body>
</html>
