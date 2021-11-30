<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.jwd.service.impl.PaymentService" %>
<%@ taglib uri = "tags" prefix = "m" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="main" var="main"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>
<fmt:message bundle="${loc}" key="name" var="name"/>
<fmt:message bundle="${loc}" key="number" var="number"/>
<fmt:message bundle="${loc}" key="date" var="date"/>
<fmt:message bundle="${loc}" key="pay" var="pay"/>
<fmt:message bundle="${loc}" key="balance" var="balance"/>
<fmt:message bundle="${loc}" key="usernocreditcards" var="usernocreditcards"/>
<fmt:message bundle="${loc}" key="choosecreditcard" var="choosecreditcard"/>
<fmt:message bundle="${loc}" key="currentpaymentprice" var="currentpaymentprice"/>
<fmt:message bundle="${loc}" key="addcreditcard" var="addcreditcard"/>

<html>
    <head>
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/navbar.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
    </head>
    <body class="d-flex flex-column h-100 primary-margin">
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
        <h4>${currentpaymentprice} ${requestScope.payment.price}</h4>
        <c:if test="${requestScope.creditcards != null}">
            <h4>${choosecreditcard}</h4>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">${name}</th>
                        <th scope="col">${number}</th>
                        <th scope="col">${balance}</th>
                        <th scope="col">${pay}</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" begin="0" end="${requestScope.creditcards.size()-1}">
                        <c:if test="${!requestScope.creditcards.get(i).bankAccount.getBlocked()}">
                            <tr>
                                <td>${i + 1}</td>
                                <td>${requestScope.creditcards.get(i).name}</td>
                                <td>${requestScope.creditcards.get(i).cardNumber}</td>
                                <td>${requestScope.creditcards.get(i).bankAccount.balance}</td>
                                <c:choose>
                                    <c:when test="${requestScope.creditcards.get(i).bankAccount.balance >= requestScope.payment.price}">
                                        <td>
                                            <form class="inline" method="post" action="/payments?command=checkout_payment" >
                                                <button class="btn btn-exsm btn-primary" type="submit">Pay</button>
                                                <input type="hidden" name="paymentId" value="${requestScope.payment.getId()}">
                                                <input type="hidden" name="creditCardId" value="${requestScope.creditcards.get(i).getId()}">
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>Insufficient Balance</td>
                                    </c:otherwise>
                                </c:choose>
                                <td></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${requestScope.creditcards == null}">
            <h4>${usernocreditcards} <a href="/payments?command=show_credit_cards"> ${addcreditcard}</a></h4>
        </c:if>
        <jsp:include page="/WEB-INF/jsp/footer.html"></jsp:include>
    </body>
</html>