<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>



<!DOCTYPE html>
<html lang="en">


<style>
.hero-wrap .overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    content: '';
    opacity: 0;
    background: #000000;
}
</style>
<body style="background-image: url('<%=request.getContextPath() %>/front-end/front/images/pageBg.jpg');">
    <div class="py-1 bg-black top">
		<div class="container">
			<div
				class="row no-gutters d-flex align-items-start align-items-center px-md-0">
				<div class="col-lg-12 d-block">
					<div class="row d-flex">
						<div class="col-md pr-4 d-flex topper align-items-center">
							<div
								class="icon mr-2 d-flex justify-content-center align-items-center">
								<span class=""></span>
							</div>
							<span class="text"></span>
						</div>
						<div class="col-md pr-4 d-flex topper align-items-center"></div>
						<div class="">
							<p class="mb-0 register-link">
								<span>Open hours:</span> <span>Monday - Sunday</span><span>8:00AM
									- 9:00PM</span>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
        <div class="container">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/front-end/front/index.jsp">PoT</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="oi oi-menu"></span> Menu
            </button>
            <div class="collapse navbar-collapse" id="ftco-nav">
                <ul class="navbar-nav ml-auto">
                   <li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/index.jsp" class="nav-link">Home</a></li>
					<li class="nav-item"><a href="" class="nav-link">Menu</a></li>
					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/front_news-11.jsp" class="nav-link">最新消息</a></li>
<%-- 					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/front_ad2.jsp" class="nav-link">最新活動</a></li> --%>
					<li class="nav-item"><a href="" class="nav-link">評價總覽</a></li>
					<li class="nav-item"><a href="" class="nav-link">餐廳資訊</a></li>
					<li class="nav-item"><a href="" class="nav-link">候位狀況</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要訂餐</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要定位</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- END nav -->
    <section class="hero-wrap hero-wrap-2" style="background-image: url('<%=request.getContextPath() %>/front-end/front/images/33333.jpg');" data-stellar-background-ratio="0.5">
        <div class="overlay"></div>
        <div class="container">
            <div class="row no-gutters slider-text align-items-end justify-content-center">
                <div class="col-md-9 ftco-animate text-center mb-4">
                    <h1 class="mb-2 bread">最新活動</h1>
                    <p class="breadcrumbs"><span class="mr-2"><a href="<%=request.getContextPath()%>/front-end/front/index.jsp">Home <i class="ion-ios-arrow-forward"></i></a></span>
                </div>
            </div>
        </div>
    </section>
	
    </body>

</html>