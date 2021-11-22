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
        <h4>Current payment price is ${requestScope.payment.price}</h4>
        <c:if test="${requestScope.creditcards != null}">
            <h4>Choose your credit card</h4>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Number</th>
                        <th scope="col">Balance</th>
                        <th scope="col">Pay</th>
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
            <h4>You have no credit credit cards, but can add one <a href="/payments?command=show_credit_cards">here </a></h4>
        </c:if>
    </body>
</html>