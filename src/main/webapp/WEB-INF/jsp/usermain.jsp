<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri = "tags" prefix = "m" %>
<%@ page import="com.epam.jwd.service.impl.UserService" %>
<%@ page import="com.epam.jwd.service.impl.AccountService" %>
<%@ page import="com.epam.jwd.service.dto.paymentdto.PaymentDto" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="account" var="account"/>
<fmt:message bundle="${loc}" key="creditcards" var="creditcards"/>
<fmt:message bundle="${loc}" key="payments" var="payments"/>
<fmt:message bundle="${loc}" key="logout" var="logout"/>
<fmt:message bundle="${loc}" key="edit" var="edit"/>
<fmt:message bundle="${loc}" key="havenoaccountstart" var="havenoaccountstart"/>
<fmt:message bundle="${loc}" key="createaccount" var="createaccount"/>
<fmt:message bundle="${loc}" key="havenoaccountend" var="havenoaccountend"/>

<html>

    <head>
        <title>Account</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/navbar.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
    </head>

    <body class="d-flex flex-column h-100">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item disabled">
                            <a class="nav-link active">${account}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/payments?command=show_credit_cards&currentPage=1">${creditcards}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/payments?command=show_payments&currentPage=1">${payments}</a>
                        </li>
                        <li class="nav-item">
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
            <c:set var="user" scope="page" value="${UserService().getById(sessionScope.id)}"/>
            <c:if test="${user.accountId != null}">
                <c:set var="account" scope="page" value="${AccountService().getById(user.getAccountId())}"/>
                <section>
                        <div class="row d-flex justify-content-center align-items-center ">
                            <div class="col-md-12 col-xl-4">
                                <div class="card" style="border-radius: 15px;">
                                    <div class="card-body text-center">
                                        <div class="mt-3 mb-4">
                                            <m:profileimage width="100" height="100" pictureId="${account.getProfilePictureId()}"/>
                                        </div>
                                        <h4 class="mb-2">${account.name} ${account.surname}</h4>
                                        <a href="/payments?command=show_edit_account">
                                            <button type="button" class="btn btn-primary btn-rounded btn-lg">
                                                ${edit}
                                            </button>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                </section>
            </c:if>
            <c:if test="${user.accountId == null}">
                <h3 class="account-content">
                    ${havenoaccountstart}<a href="/payments?command=show_create_account"> ${createaccount}</a> ${havenoaccountend}
                </h3>
            </c:if>
        </div>
        <jsp:include page="/WEB-INF/jsp/footer.html"></jsp:include>
    </body>
</html>