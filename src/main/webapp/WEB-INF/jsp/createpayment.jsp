<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/signin.css"%></style>
    </head>

    <body>
        <main class="form-signin" align="center">
            <form action="/payments?command=commit_payment_creation" method="post" autocomplete="off">
                <h1 class="h3 mb-3">Please create your payment</h1>
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInput" placeholder="Password" name="name">
                    <label for="floatingInput">name</label>
                </div>
                <div class="form-floating">
                    <input type="number" class="form-control" id="floatingInput" placeholder="Password" name="price">
                    <label for="floatingInput">price</label>
                </div>
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInput" placeholder="Password" name="destination">
                    <label for="floatingPassword">destination</label>
                </div>
                <div class="btn-group" role="group">
                  <button type="submit" class="btn btn-primary" name="action" value="saveAndCheckout">Save payment and proceed to checkout</button>
                  <button type="submit" class="btn btn-primary" name="action" value="save">Save payment</button>
                </div>
            </form>
        </main>
    </body>
<html>