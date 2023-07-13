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
                <c:forEach var="blog" items="${pageInfo.toList()}">
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
            <div class="col-10">
                <!--페이징 처리 버튼 자리-->
                <ul class="pagination justify-content-center">
                    
                    <!-- 이전페이지 버튼 -->
                    <!-- c:if 태그는 test 프로퍼티에 참,거짓을 판단할 수 있는 식을 넣어주면 참인경우만 해당 요소를 표시합니다. -->
                    <c:if test="${startPageNum != 1}">
                        <li class="page-item">
                            <a class="page-link" href="/blog/list/${startPageNum - 1}">이전으로</a>
                        </li>
                    </c:if>

                    <!-- 번호 버튼 까는 부분 
                    begin = 시작숫자, end = 끝숫자, var = 반복문 내에서 사용할 변수명-->
                    <c:forEach begin="${ startPageNum }" 
                                end="${ endPageNum }" 
                                var="btnNum">
                        <li class="page-item ${ currentPageNum == btnNum ? 'active' : '' }">
                            <a class="page-link" href="/blog/list/${btnNum}">${btnNum}</a>
                        </li>
                    </c:forEach>

                    <!-- 다음페이지 버튼 -->
                    <!-- 다음 페이지는, 현재 endPageNum이 전체적으로 봐도 마지만 번호인 경우에 보여주지 않으면 됩니다.-->
                    <c:if test="${endPageNum != pageInfo.getTotalPages()}">
                        <li class="page-item">
                            <a class="page-link" href="/blog/list/${endPageNum + 1}">다음으로</a>
                        </li>
                    </c:if>

                </ul>
            </div>
        </div>
    </div><!-- .container -->
</body>
</html>