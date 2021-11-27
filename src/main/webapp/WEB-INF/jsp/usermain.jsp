<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri = "tags" prefix = "m" %>
<%@ page import="com.epam.jwd.service.impl.UserService" %>
<%@ page import="com.epam.jwd.service.impl.AccountService" %>
<%@ page import="com.epam.jwd.service.dto.paymentdto.PaymentDto" %>

<html>

    <head>
        <title>Account</title>
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
                <li class="nav-item disabled">
                  <a class="nav-link active">Account</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/payments?command=show_credit_cards&currentPage=1">Credit Cards</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/payments?command=show_payments&currentPage=1">Payments</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/payments?command=signout">Log Out</a>
                </li>
              </ul>

              <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                <label class="btn btn-outline-primary" for="btnradio1">English</label>

                <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                <label class="btn btn-outline-primary" for="btnradio2">Russian</label>
              </div>
            </div>
          </div>
        </nav>
        <div class="account-content">
            <c:set var="user" scope="page" value="${UserService().getById(sessionScope.id)}"/>
            <c:if test="${user.accountId != null}">
                <c:set var="account" scope="page" value="${AccountService().getById(user.getAccountId())}"/>
                <h1>
                    ${account.getName()}
                    <a href="/payments?command=show_edit_account">link</a>

                </h1>
                <h1> ${account.getSurname()} </h1>
                <m:profileimage width="300" height="300" pictureId="${account.getProfilePictureId()}"/>
            </c:if>
            <c:if test="${user.accountId == null}">
                <h3>
                    You have no account yet. You can  <a href="/payments?command=show_create_account">create it now </a> or later
                </h3>
            </c:if>
        </div>
    </body>
</html>