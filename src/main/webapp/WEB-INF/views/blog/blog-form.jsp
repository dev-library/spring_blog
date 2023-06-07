<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <div class="container">
        <form method="POST"><!-- action속성을 주지 않아도 자동으로 현재 주소로 전송 -->
            <div class="row">
                <div class="col-3">
                    <label for="writer" class="form-label">글쓴이</label>
                    <input type="text" class="form-control" name="writer" id="writer" placeholder="글쓴이를 적어주세요.">
                </div>
                <div class="col-3">
                    <label for="title" class="form-label">제목</label>
                    <input type="text" class="form-control" name="blogTitle" id="title" placeholder="제목을 적어주세요.">
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <label for="content" class="form-label">본문</label>
                    <textarea class="form-control" id="content" name="blogContent" rows="10"></textarea>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <input type="submit" class="btn btn-primary" value="글쓰기">
                </div>
            </div>
        </form>
    </div>
</body>
</html>