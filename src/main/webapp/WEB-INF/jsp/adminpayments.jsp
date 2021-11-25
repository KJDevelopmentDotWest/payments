<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.jwd.service.impl.PaymentService" %>
<%@ taglib uri = "tags" prefix = "m" %>

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
                            <a class="nav-link" href="/payments?command=show_admin_users&currentPage=1">Users</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/payments?command=show_admin_credit_cards&currentPage=1">Credit Cards</a>
                        </li>
                        <li class="nav-item disabled">
                            <a class="nav-link active">Payments</a>
                        </li>
                        <li class="nav-item ">
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

        <c:if test="${requestScope.payments.size() > 0}">

            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th class="active"scope="col"><a href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage}&sortBy=id" class="active">Id</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage}&sortBy=userId" class="active">User Id</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage}&sortBy=name" class="active">Name</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage}&sortBy=price" class="active">Price</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage}&sortBy=destination" class="active">Destination</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage}&sortBy=time" class="active">Transaction Time</a></th>
                        <th class="active"scope="col"><a href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage}&sortBy=committed" class="active">Committed</a></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" begin="0" end="${requestScope.payments.size()-1}">
                        <tr>
                            <td>${(requestScope.currentPage - 1) * 5 + i + 1}</td>
                            <m:adminpaymentoutput paymentDto="${requestScope.payments.get(i)}"/>
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
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=2&sortBy=${requestScope.sortBy}">2</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">Last</a>
                        </li>
                    </ul>
                </nav>
            </c:if>

            <c:if test="${requestScope.currentPage == requestScope.lastPage && requestScope.lastPage != 1}">
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=1&sortBy=${requestScope.sortBy}">First</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=${requestScope.lastPage-1}&sortBy=${requestScope.sortBy}">${requestScope.lastPage-1}</a>
                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">${requestScope.lastPage}</a>
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
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=1&sortBy=${requestScope.sortBy}">First</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage-1}&sortBy=${requestScope.sortBy}">${requestScope.currentPage-1}</a>
                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage}&sortBy=${requestScope.sortBy}">${requestScope.currentPage}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=${requestScope.currentPage+1}&sortBy=${requestScope.sortBy}">${requestScope.currentPage+1}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/payments?command=show_admin_payments&currentPage=${requestScope.lastPage}&sortBy=${requestScope.sortBy}">Last</a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </c:if>
        <c:if test="${requestScope.payments.size() == 0 || requestScope.payments == null}">
            <h3>No one has created payment yet</h3>
        </c:if>
    </body>
</html>