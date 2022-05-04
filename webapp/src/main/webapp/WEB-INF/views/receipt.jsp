<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../components/header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<div class="flex flex-row divide-x-2 divide-polard mt-10">
    <div class="flex flex-col w-3/5">
        <div class="mx-auto">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-60 w-60 ml-14" fill="none" viewBox="0 0 24 24" stroke="#A3BE8C" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7" />
            </svg>
            <h1 class="text-ngreen text-4xl text-center">Listo!</h1>
        </div>
        <div class="mx-auto  text-polard text-2xl mt-10">
            El vendedor te va a transferir <b><c:out value="${trade.quantity}"/> <c:out value="${offer.crypto.code}"/></b> a tu wallet.
        </div>
        <div class="mt-10 mx-auto flex flex-col">
            <h1 class="text-polard font-extrabold text-2xl font-sans">Datos de la oferta:</h1>
            <div class="mx-auto">
                <h1 class="text-polard font-bold font-sans text-center text-lg">Criptomoneda</h1>
                <p class="text-polard font-sans text-center">Bitcoin</p>
            </div>
            <div class="mx-auto">
                <h1 class="text-polard font-bold font-sans text-center text-lg">Precio por Moneda</h1>
                <p class="text-polard font-sans text-center">200 ARS</p>
            </div>
            <div class="mx-auto">
                <h1 class="text-polard font-bold font-sans text-center text-lg">Fecha de la transacción</h1>
                <p class="text-polard font-sans text-center">2022-02-21</p>
            </div>
        </div>
        <div class="flex flex-row mt-10">
            <a class="bg-nred text-white p-3 font-sans rounded-lg mx-auto" href="#">Tuve un problema</a>
            <a class="bg-frost text-white p-3 font-sans rounded-lg mx-auto" href="<c:url  value="/"/>">Volver al Home</a>
        </div>


    </div>
    <div class="flex flex-row w-2/5">
        <jsp:include page="../components/seller_info.jsp">
            <jsp:param name="email" value="${offer.seller.email}"/>
            <jsp:param name="phone" value="${offer.seller.phoneNumber}"/>
            <jsp:param name="trades" value="${offer.seller.ratingCount}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>


