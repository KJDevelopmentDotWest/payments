<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="main" var="main"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>
<fmt:message bundle="${loc}" key="name" var="name"/>
<fmt:message bundle="${loc}" key="price" var="price"/>
<fmt:message bundle="${loc}" key="destination" var="destination"/>
<fmt:message bundle="${loc}" key="filldata" var="filldata"/>
<fmt:message bundle="${loc}" key="proceedtocheckout" var="proceedtocheckout"/>
<fmt:message bundle="${loc}" key="savepayment" var="savepayment"/>
<fmt:message bundle="${loc}" key="nametooshort" var="nametooshort"/>
<fmt:message bundle="${loc}" key="pricewronglength" var="pricewronglength"/>
<fmt:message bundle="${loc}" key="destinationtooshort" var="destinationtooshort"/>
<fmt:message bundle="${loc}" key="nametoolong" var="nametoolong"/>
<fmt:message bundle="${loc}" key="pricetoolong" var="pricetoolong"/>
<fmt:message bundle="${loc}" key="destinationtoolong" var="destinationtoolong"/>

<html>
    <head>
        <title>Edit Payments</title>
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
            var price = document.getElementById("price").value;
            var destination = document.getElementById("destination").value;
            var nameShortFlag = (name == null || name == "" || name.length < 2);
            var nameLongFlag = (name.length > 15);
            var priceFlag = (price == null || price == "" || Number(price) < 0 || !Number.isInteger(Number(price)));
            var priceLongFlag = (price.length > 9);
            var destinationShortFlag = (destination == null || destination == "" || destination.length < 2);
            var destinationLongFlag = (destination.length > 15);
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
            if (priceFlag){
                document.getElementById("pricewronglength").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("pricewronglength").style.display = "none";
            }
            if (priceLongFlag){
                document.getElementById("pricetoolong").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("pricetoolong").style.display = "none";
            }
            if (destinationShortFlag){
                document.getElementById("destinationtooshort").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("destinationtooshort").style.display = "none";
            }
            if (destinationLongFlag){
                document.getElementById("destinationtoolong").style.display = "flex";
                document.getElementById("submitbutton").disabled = true;
            } else {
                document.getElementById("destinationtoolong").style.display = "none";
            }
            if(!(nameShortFlag || nameLongFlag || priceFlag || priceLongFlag || destinationShortFlag || destinationLongFlag)){
                document.getElementById("submitbutton").disabled = false;
            }
        };
        function allowOnlyEngl(){
            var name = document.getElementById("name");
            var destination = document.getElementById("destination");
            if (!/^[a-zA-Z]*$/g.test(name.value)) {
                name.value = name.value.slice(0, -1);
                return false;
            }
            if (!/^[a-zA-Z/-/.@]*$/g.test(destination.value)) {
                destination.value = destination.value.slice(0, -1);
                return false;
            }
        };
    </script>

    <body class="d-flex flex-column h-100" onload="validateform()">
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
                </div>
            </div>
        </nav>
        <div class="primary-margin">
            <main class="form-signin form-margin-navbar" align="center">
                <form action="/payments?command=commit_payment_changes" method="post" autocomplete="off">
                    <h1 class="h3 mb-3">${filldata}</h1>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="name" placeholder="Password" name="name" value="${requestScope.payment.getName()}" oninput="validateform();allowOnlyEngl()">
                        <label for="name">${name}</label>
                        <div class="hidden error-message" id="nametooshort">
                            ${nametooshort}
                        </div>
                        <div class="hidden error-message" id="nametoolong">
                            ${nametoolong}
                        </div>
                    </div>
                    <div class="form-floating">
                        <input type="number" class="form-control" id="price" placeholder="Password" name="price" value="${requestScope.payment.getPrice()}" oninput="validateform()">
                        <label for="price">${price}</label>
                        <div class="hidden error-message" id="pricewronglength">
                            ${pricewronglength}
                        </div>
                        <div class="hidden error-message" id="pricetoolong">
                            ${pricetoolong}
                        </div>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="destination" placeholder="Password" name="destination" value="${requestScope.payment.getDestinationAddress()}" oninput="validateform();allowOnlyEngl()">
                        <label for="destination">${destination}</label>
                        <div class="hidden error-message" id="destinationtooshort">
                            ${destinationtooshort}
                        </div>
                        <div class="hidden error-message" id="destinationtoolong">
                            ${destinationtoolong}
                        </div>
                    </div>
                    <input type="hidden" name="paymentId" value="${requestScope.payment.getId()}">
                    <button id="submitbutton" type="submit" class="btn btn-primary w-100">${savepayment}</button>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="action" value="checkout" id="flexCheckDefault">
                        <label class="form-check-label" for="flexCheckDefault">
                            ${proceedtocheckout}
                        </label>
                    </div>
                </form>
            </main>
        </div>
        <jsp:include page="/WEB-INF/jsp/footer.html"></jsp:include>
    </body>
<html>