<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "tags" prefix = "m" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
    <head>
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <style><%@include file="/WEB-INF/css/bootstrap.min.css"%></style>
        <style><%@include file="/WEB-INF/css/signin.css"%></style>
        <style><%@include file="/WEB-INF/css/grid.css"%></style>
        <style><%@include file="/WEB-INF/css/core.css"%></style>
    </head>

    <body>
        <main class="account-edit" align="center">
            <form action="/payments?command=commit_account_creation" method="post" autocomplete="off">
                <h1 class="h3 mb-3">Please edit your account</h1>
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInput" placeholder="Password" name="name">
                    <label for="floatingInput">name</label>
                </div>
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInput" placeholder="Password" name="surname">
                    <label for="floatingInput">price</label>
                </div>
                <br/>
                <h3>Choose new profile picture</h3>
                <div class="container">
                    <div class="row">
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="1">
                                <m:image width="100" height="100" pictureId="${1}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="2">
                                <m:image width="100" height="100" pictureId="${2}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="3">
                                <m:image width="100" height="100" pictureId="${3}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="4">
                                <m:image width="100" height="100" pictureId="${4}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="5">
                                <m:image width="100" height="100" pictureId="${5}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="6">
                                <m:image width="100" height="100" pictureId="${6}"/>
                            </input>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="7">
                                <m:image width="100" height="100" pictureId="${7}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="8">
                                <m:image width="100" height="100" pictureId="${8}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="9">
                                <m:image width="100" height="100" pictureId="${9}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="10">
                                <m:image width="100" height="100" pictureId="${10}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="11">
                                <m:image width="100" height="100" pictureId="${11}"/>
                            </input>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="radio" name="pictureId" value="12">
                                <m:image width="100" height="100" pictureId="${12}"/>
                            </input>
                        </div>
                    </div>
                </div>
                <br/>
                <button type="submit" class="btn btn-primary" >Create account</button>
            </form>
        </main>
    </body>
<html>