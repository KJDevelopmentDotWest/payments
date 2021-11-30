<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.jwd.service.impl.PaymentService" %>
<%@ taglib uri = "tags" prefix = "m" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="name" var="name"/>
<fmt:message bundle="${loc}" key="price" var="price"/>
<fmt:message bundle="${loc}" key="destination" var="destination"/>
<fmt:message bundle="${loc}" key="time" var="time"/>
<fmt:message bundle="${loc}" key="committed" var="committed"/>
<fmt:message bundle="${loc}" key="edit" var="edit"/>
<fmt:message bundle="${loc}" key="first" var="first"/>
<fmt:message bundle="${loc}" key="last" var="last"/>
<fmt:message bundle="${loc}" key="account" var="account"/>
<fmt:message bundle="${loc}" key="creditcards" var="creditcards"/>
<fmt:message bundle="${loc}" key="payments" var="payments"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>
<fmt:message bundle="${loc}" key="createpayment" var="createpayment"/>
<fmt:message bundle="${loc}" key="usernopayments" var="usernopayments"/>


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
                            <a class="nav-link" href="/payments?command=show_account">${account}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/payments?command=show_credit_cards&currentPage=1">${creditcards}</a>
                        </li>
                        <li class="nav-item disabled">
                            <a class="nav-link active">${payments}</a>
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

        <a href="/payments?command=show_create_payment" ><button class="btn btn-primary">${createpayment}</button></a>

        <c:if test="${requestScope.payments.size() > 0}">

            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th class="active"scope="col"><a href="/payments?command=show_payments&currentPage=${requestScope.currentPage}&sortBy=name" class="active">${name}</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_payments&currentPage=${requestScope.currentPage}&sortBy=price" class="active">${price}</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_payments&currentPage=${requestScope.currentPage}&sortBy=destination" class="active">${destination}</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_payments&currentPage=${requestScope.currentPage}&sortBy=time" class="active">${time}</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_payments&currentPage=${requestScope.currentPage}&sortBy=committed" class="active">${committed}</a></th>
                        <th scope="col">${edit}</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" begin="0" end="${requestScope.payments.size()-1}">
                        <c:choose>
                            <c:when test="${requestScope.payments.get(i).getCommitted()}">
                                <tr>
                                    <td>${(requestScope.currentPage - 1) * 5 + i + 1}</td>
                                    <m:paymentoutput paymentDto="${requestScope.payments.get(i)}"/>
                                    <td></td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td>${(requestScope.currentPage - 1) * 5 + i + 1}</td>
                                    <m:paymentoutput paymentDto="${requestScope.payments.get(i)}"/>
                                    <td>
                                        <form class="inline" method="post" action="/payments?command=show_edit_payment" >
                                            <button class="btn btn-exsm btn-primary" type="submit">${edit}</button>
                                            <input type="hidden" name="paymentId" value="${requestScope.payments.get(i).getId()}">
                                        </form>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tbody>
            </table>

            <c:if test="${requestScope.currentPage == 1 && requestScope.lastPage != 1}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link">${first}</a>
                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link">1</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=2&sortBy=${requestScope.sortBy}">2</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">${last}</a>
                        </li>
                    </ul>
                </nav>
            </c:if>

            <c:if test="${requestScope.currentPage == requestScope.lastPage && requestScope.lastPage != 1}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=1&sortBy=${requestScope.sortBy}">${first}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=${requestScope.lastPage-1}&sortBy=${requestScope.sortBy}">${requestScope.lastPage-1}</a>
                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">${requestScope.lastPage}</a>
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link">${last}</a>
                        </li>
                    </ul>
                </nav>
            </c:if>

            <c:if test="${requestScope.lastPage == 1}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link">${first}</a>
                        </li>
                        <li class="page-item active">
                            <a class="page-link">1</a>
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link">${last}</a>
                        </li>
                    </ul>
                </nav>
            </c:if>

            <c:if test="${requestScope.currentPage > 1 && requestScope.currentPage < requestScope.lastPage}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=1&sortBy=${requestScope.sortBy}">${first}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=${requestScope.currentPage-1}&sortBy=${requestScope.sortBy}">${requestScope.currentPage-1}</a>
                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=${requestScope.currentPage}&sortBy=${requestScope.sortBy}">${requestScope.currentPage}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=${requestScope.currentPage+1}&sortBy=${requestScope.sortBy}">${requestScope.currentPage+1}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_payments&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">${last}</a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </c:if>
        <c:if test="${requestScope.payments.size() == 0 || requestScope.payments == null}">
            <h3>${usernopayments}</h3>
        </c:if>
        <jsp:include page="/WEB-INF/jsp/footer.html"></jsp:include>
    </body>
</html>