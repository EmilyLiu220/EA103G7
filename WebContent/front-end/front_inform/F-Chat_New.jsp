<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.front_inform.model.*"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>F-Chat_New</title>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/open-iconic-bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/animate.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/owl.carousel.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/owl.theme.default.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/magnific-popup.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/aos.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/ionicons.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/bootstrap-datepicker.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/jquery.timepicker.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/flaticon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/icomoon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/fiNmsg.css">

</head>

<body>
    <div class="container">
        <div>
            <div class="container text-center">
                <div class="row">
                    <div class="round hollow text-center">
                        <a href="#" class="open-btn" id="addClass"> Click Here</a>
                    </div>
                </div>
            </div>
            <aside id="sidebar_secondary" class="tabbed_sidebar ng-scope chat_sidebar">
                <div class="popup-head">
                    <div class="popup-head-right pull-right">
                        <button data-widget="remove" id="removeClass" class="chat-header-button pull-right" type="button"><i class="glyphicon glyphicon-remove"></i></button>
                    </div>
                </div>
                <div id="chat" class="chat_box_wrapper chat_box_small chat_box_active" style="opacity: 1; display: block; transform: translateX(0px);">
                    <div class="chat_box touchscroll chat_box_colors_a">
                        <div class="chat_message_wrapper">
                            <div class="chat_user_avatar">
                                <img alt="Gurdeep Osahan (Web Designer)" title="Gurdeep Osahan (Web Designer)" src="https://bootdey.com/img/Content/avatar/avatar1.png" class="md-user-image">
                            </div>
                            <ul class="chat_message">
                                <li>
                                    <p> 您好，請問有什麼能為您服務的嗎？ </p>
                                </li>
                            </ul>
                        </div>
                        <div class="chat_message_wrapper chat_message_right">
                            <div class="chat_user_avatar">
                                <img alt="Gurdeep Osahan (Web Designer)" title="Gurdeep Osahan (Web Designer)" src="https://bootdey.com/img/Content/avatar/avatar1.png" class="md-user-image">
                            </div>
                            <ul class="chat_message">
                                <li>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Autem delectus distinctio dolor earum est hic id impedit ipsum minima mollitia natus nulla perspiciatis quae quasi, quis recusandae, saepe, sunt totam.
                                        <span class="chat_message_time">13:34</span>
                                    </p>
                                </li>
                            </ul>
                        </div>
                        <div class="chat_message_wrapper">
                            <div class="chat_user_avatar">
                                <img alt="Gurdeep Osahan (Web Designer)" title="Gurdeep Osahan (Web Designer)" src="https://bootdey.com/img/Content/avatar/avatar1.png" class="md-user-image">
                            </div>
                            <ul class="chat_message">
                                <li>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Atque ea mollitia pariatur porro quae sed sequi sint tenetur ut veritatis.https://www.facebook.com/iamgurdeeposahan
                                        <span class="chat_message_time">23 Jun 1:10am</span>
                                    </p>
                                </li>
                            </ul>
                        </div>
                        <div class="chat_message_wrapper chat_message_right">
                            <div class="chat_user_avatar">
                                <img alt="Gurdeep Osahan (Web Designer)" title="Gurdeep Osahan (Web Designer)" src="https://bootdey.com/img/Content/avatar/avatar1.png" class="md-user-image">
                            </div>
                            <ul class="chat_message">
                                <li>
                                    <p> Lorem ipsum dolor sit amet, consectetur. </p>
                                </li>
                                <li>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                                        <span class="chat_message_time">Friday 13:34</span>
                                    </p>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="chat_submit_box">
                    <div class="uk-input-group">
                        <div class="gurdeep-chat-box">
                            <input type="text" placeholder="Type a message" id="submit_message" name="submit_message" class="md-input">
                        </div>
                        <span class="uk-input-group-addon">
                            <a href="#"><i class="glyphicon glyphicon-send"></i></a>
                        </span>
                    </div>
                </div>
            </aside>
        </div>
    </div>
    <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $("#addClass").click(function() {
                $('#sidebar_secondary').addClass('popup-box-on');
            });

            $("#removeClass").click(function() {
                $('#sidebar_secondary').removeClass('popup-box-on');
            });
        })
    </script>
</body>

</html>