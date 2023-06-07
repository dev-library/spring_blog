<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    div {
        border: 1px solid black;
    }
</style>
</head>
<body>
    <div class="container">
        <div class="row first-row">
            <div class="col-1">
                글번호
            </div>
            <div class="col-1">
                ${blog.blogId}
            </div>
            <div class="col-2">
                글제목
            </div>
            <div class="col-4">
                ${blog.blogTitle}
            </div>
            <div class="col-1">
                작성자
            </div>
            <div class="col-1">
                ${blog.writer}
            </div>
            <div class="col-1">
                조회수
            </div>
            <div class="col-1">
                ${blog.blogCount}
            </div>
        </div><!--.first-row-->
        <div class="row second-row">
            <div class="col-1">
                작성일
            </div>
            <div class="col-5">
                ${blog.publishedAt}
            </div>
            <div class="col-1">
                수정일
            </div>
            <div class="col-5">
                ${blog.updatedAt}
            </div>
        </div><!-- .row second -->
        <div class="row third-row">
            <div class="col-1">
                본문
            </div>
            <div class="col-11">
                ${blog.blogContent}
            </div>
        </div><!-- .row third -->
        <div class="row fourth-row">
            <div class="col-1">
                <a href="/blog/list"><button class="btn btn-secondary">목록으로</button></a>
            </div>
            <div class="col-1">
                <form action="/blog/delete" method="POST">
                    <input type="hidden" name="blogId" value="${blog.blogId}">
                    <input type="submit" value="삭제하기" class="btn btn-warning">
                </form>
            </div>
            <div class="col-1">
                <form action="/blog/updateform" method="POST">
                    <input type="hidden" name="blogId" value="${blog.blogId}">
                    <input type="submit" value="수정하기" class="btn btn-info">
                </form>
            </div>
        </div><!-- .row fourth -->
    </div><!-- .container -->
</body>
</html>





