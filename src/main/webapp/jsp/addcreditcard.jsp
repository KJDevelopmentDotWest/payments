<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/css/bootstrap.min.css"%></style>
        <style><%@include file="/css/signin.css"%></style>
    </head>

    <body>
        <main class="form-signin" align="center">
            <form action="/payments?command=add_credit_card" method="post" autocomplete="off">
                <h1 class="h3 mb-3">Please add credit card</h1>
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInput" placeholder="Password" name="name">
                    <label for="floatingInput">name</label>
                </div>
                <div class="form-floating">
                    <input type="number" class="form-control" id="floatingInput" placeholder="Password" name="cardNumber">
                    <label for="floatingInput">credit card number</label>
                </div>
                <button class="w-100 btn btn-lg btn-primary" type="submit">Add credit card</button>
            </form>
        </main>
    </body>
<html>