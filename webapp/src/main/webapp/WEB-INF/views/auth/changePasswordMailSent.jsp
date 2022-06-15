<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">

</head>
<body class="bg-storml overflow-x-hidden">
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../../components/anon/anonymousHeader.jsp"/>

<div class="w-1/3 mx-auto mt-20 rounded-lg bg-stormd/[0.9] flex flex-col justify-center border-2 border-polard">
    <h1 class="mt-10 text-center text-4xl font-semibold font-sans text-polar"><messages:message code="checkEmail"/></h1>
    <h2 class="mt-10 text-center text-xl font-sans text-polar">Te enviamos un mail con instrucciones para poder cambiar tu contraseña.</h2>

    <a class="flex w-full justify-center mt-5 mb-10" href="<c:url value="/"/>">
        <div class="w-1/7 rounded-lg bg-frost py-3 px-5 text-l font-sans text-center text-white cursor-pointer shadow-lg">
            Volver
        </div>
    </a>
</div>



<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>

</body>
</html>