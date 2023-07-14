<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
</head>
<body>
    <div class=".container">
        <form action="/login" method="POST">
            <div class="col-3">
                <!--아이디는 username, 비밀번호는 password로 고정-->
                <input type="text" name="username" placeholder="아이디">
            </div>
            <div class="col-3">
                <input type="password" name="password" placeholder="비밀번호">
            </div>
            <input type="submit" value="회원가입하기">
        </form>
    </div>
</body>
</html>
