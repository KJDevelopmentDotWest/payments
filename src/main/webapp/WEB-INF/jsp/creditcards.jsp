<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.jwd.service.impl.PaymentService" %>
<%@ taglib uri = "tags" prefix = "m" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="name" var="name"/>
<fmt:message bundle="${loc}" key="number" var="number"/>
<fmt:message bundle="${loc}" key="date" var="date"/>
<fmt:message bundle="${loc}" key="balance" var="balance"/>
<fmt:message bundle="${loc}" key="state" var="state"/>
<fmt:message bundle="${loc}" key="addfunds" var="addfunds"/>
<fmt:message bundle="${loc}" key="first" var="first"/>
<fmt:message bundle="${loc}" key="last" var="last"/>
<fmt:message bundle="${loc}" key="account" var="account"/>
<fmt:message bundle="${loc}" key="creditcards" var="creditcards"/>
<fmt:message bundle="${loc}" key="payments" var="payments"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>
<fmt:message bundle="${loc}" key="addcreditcard" var="addcreditcard"/>
<fmt:message bundle="${loc}" key="usernocreditcards" var="usernocreditcards"/>

<html>
    <head>
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/navbar.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
    </head>

    <script>
        function validateform(){
            var funds = document.getElementById("funds").value;
            var fundsFlag = (funds == null || funds == "" || !Number.isInteger(Number(funds)));
            var fundsLongFlag = (funds.length > 9);
            if(!(fundsLongFlag || fundsFlag)){
                document.getElementById("submitbutton").disabled = false;
            } else {
                document.getElementById("submitbutton").disabled = true;
            }
        };
        function setSortedBy(){
            var sortBy = '${sortBy}';
            if(sortBy == "" || sortBy == "name"){
                document.getElementById("name").classList.add('col-chosen');
            }
            if(sortBy == "number"){
                document.getElementById("number").classList.add('col-chosen');
            }
            if(sortBy == "date"){
                document.getElementById("date").classList.add('col-chosen');
            }
            if(sortBy == "balance"){
                document.getElementById("balance").classList.add('col-chosen');
            }
            if(sortBy == "state"){
                document.getElementById("state").classList.add('col-chosen');
            }
        }
    </script>

    <body class="d-flex flex-column h-100" onload="setSortedBy()">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="/payments?command=show_account">${account}</a>
                        </li>
                        <li class="nav-item disabled">
                            <a class="nav-link active">${creditcards}</a>
                        </li>
                        <li class="nav-item ">
                            <a class="nav-link" href="/payments?command=show_payments&currentPage=1">${payments}</a>
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
            <a href="/payments?command=show_add_credit_card&currentPage=1"><button class="btn btn-primary">${addcreditcard}</button></a>

            <c:if test="${requestScope.creditcards.size() > 0}">

                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th id="name" class="active"scope="col"><a class="href-unchanged" href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=name">${name}</a></th>
                            <th id="number" class="active"scope="col"><a class="href-unchanged" href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=number">${number}</a></th>
                            <th id="date" class="active"scope="col"><a class="href-unchanged" href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=date">${date}</a></th>
                            <th id="balance" class="active"scope="col"><a class="href-unchanged" href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=balance">${balance}</a></th>
                            <th id="state" class="active"scope="col"><a class="href-unchanged" href="/payments?command=show_credit_cards&currentPage=${requestScope.currentPage}&sortBy=state">${state}</a></th>
                            <th scope="col"></th>
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
                                <a class="page-link">${first}</a>
                            </li>
                            <li class="page-item active" aria-current="page">
                                <a class="page-link">1</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/payments?command=show_credit_cards&currentPage=2&sortBy=${requestScope.sortBy}">2</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">${last}</a>
                            </li>
                        </ul>
                    </nav>
                </c:if>

                <c:if test="${requestScope.currentPage == requestScope.lastPage && requestScope.lastPage != 1}">
                    <nav>
                        <ul class="pagination justify-content-center">
                            <li class="page-item">
                                <a class="page-link" href="/payments?command=show_credit_cards&currentPage=1&sortBy=${requestScope.sortBy}">${first}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.lastPage-1}&sortBy=${requestScope.sortBy}">${requestScope.lastPage-1}</a>
                            </li>
                            <li class="page-item active" aria-current="page">
                                <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">${requestScope.lastPage}</a>
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
                                <a class="page-link" href="/payments?command=show_credit_cards&currentPage=1&sortBy=${requestScope.sortBy}">${first}</a>
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
                                <a class="page-link" href="/payments?command=show_credit_cards&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">${last}</a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </c:if>
            <c:if test="${requestScope.creditcards.size() == 0 || requestScope.creditcards.size() == null}">
                <h3>${usernocreditcards}</h3>
            </c:if>
        </div>
        <jsp:include page="/WEB-INF/jsp/footer.html"></jsp:include>
    </body>
</html>