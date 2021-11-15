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
            <form action="/payments?command=commit_user_creation" method="post" autocomplete="off">
                <h1 class="h3 mb-3">Please create your payment</h1>
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInput" placeholder="Password" name="login">
                    <label for="floatingInput">login</label>
                </div>
                <div class="form-floating">
                    <input type="Password" class="form-control" id="floatingInput" placeholder="Password" name="password">
                    <label for="floatingInput">password</label>
                </div>

                <button type="submit" class="btn btn-primary">Create</button>
            </form>
        </main>
    </body>
<html>