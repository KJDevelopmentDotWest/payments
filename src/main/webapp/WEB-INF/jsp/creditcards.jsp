<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.jwd.service.impl.PaymentService" %>
<%@ taglib uri = "tags" prefix = "m" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="greeting" var="greeting"/>
<fmt:message bundle="${loc}" key="listusers" var="listusers"/>

<html>
    <head>
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/navbar.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
    </head>

    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="/payments?command=show_account">Account</a>
                        </li>
                        <li class="nav-item disabled">
                            <a class="nav-link active">Credit Cards</a>
                        </li>
                        <li class="nav-item ">
                            <a class="nav-link" href="/payments?command=show_payments&currentPage=1">Payments</a>
                        </li>
                        <li class="nav-item ">
                            <a class="nav-link" href="/payments?command=signout">Log Out</a>
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


        <a href="/payments?command=show_add_credit_card&currentPage=1" class="btn btn-primary">Add Credit Card</a>
        <h1>${greeting}</h1>

        <c:if test="${requestScope.creditcards.size() > 0}">

            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th class="active"scope="col"><a href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=name" class="active">Name</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=number" class="active">Number</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=date" class="active">Expire Date</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=balance" class="active">Balance</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=state" class="active">State</a></th>
                        <th scope="col">Add Funds</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" begin="0" end="${requestScope.creditcards.size()-1}">
                        <tr>
                            <td>${(requestScope.currentPage - 1) * 5 + i + 1}</td>
                            <m:creditcardoutput creditCardDto="${requestScope.creditcards.get(i)}"/>
                            <td></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:if test="${requestScope.currentPage == 1 && requestScope.lastPage != 1}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link">First</a>
                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link">1</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=2&sortBy=${requestScope.sortBy}">2</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">Last</a>
                        </li>
                    </ul>
                </nav>
            </c:if>

            <c:if test="${requestScope.currentPage == requestScope.lastPage && requestScope.lastPage != 1}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=1&sortBy=${requestScope.sortBy}">First</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.lastPage-1}&sortBy=${requestScope.sortBy}">${requestScope.lastPage-1}</a>
                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">${requestScope.lastPage}</a>
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link">Last</a>
                        </li>
                    </ul>
                </nav>
            </c:if>

            <c:if test="${requestScope.lastPage == 1}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link">First</a>
                        </li>
                        <li class="page-item active">
                            <a class="page-link">1</a>
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link">Last</a>
                        </li>
                    </ul>
                </nav>
            </c:if>

            <c:if test="${requestScope.currentPage > 1 && requestScope.currentPage < requestScope.lastPage}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=1&sortBy=${requestScope.sortBy}">First</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage-1}&sortBy=${requestScope.sortBy}">${requestScope.currentPage-1}</a>

                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=${requestScope.sortBy}">${requestScope.currentPage}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage+1}&sortBy=${requestScope.sortBy}">${requestScope.currentPage+1}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">Last</a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </c:if>
        <c:if test="${requestScope.creditcards.size() == 0 || requestScope.creditcards.size() == null}">
            <h3>You have no credit cards yet</h3>
        </c:if>
    </body>
</html>