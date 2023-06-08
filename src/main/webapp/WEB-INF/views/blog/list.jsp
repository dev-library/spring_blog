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
    <div class="container">
        <h1 class="text-center">게시물 목록</h1>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>글번호</th>
                    <th>글제목</th>
                    <th>글쓴이</th>
                    <th>작성일</th>
                    <th>수정일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="blog" items="${blogList}">
                    <tr>
                        <td>${blog.blogId}</td>
                        <td><a href="/blog/detail/${blog.blogId}">${blog.blogTitle}</a></td>
                        <td>${blog.writer}</td>
                        <td>${blog.publishedAt}</td>
                        <td>${blog.updatedAt}</td>
                        <td>${blog.blogCount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table><!-- .table table-hover -->
        <div class="row">
            <div class="col-1">
                <a href="/blog/insert" class="btn btn-primary">글쓰기</a>
            </div>
        </div>
    </div><!-- .container -->
</body>
</html>