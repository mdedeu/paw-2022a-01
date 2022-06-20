<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/apiCryptoPrices.js"/>"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Turret+Road:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">

</head>
<body class="bg-storml overflow-x-hidden flex flex-col">
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:include page="../components/seller/sellerHeader.jsp"/>
<div class=" flex flex-row bg-black text-center justify-between px-5">
    <c:forEach var="coin" items="${cryptocurrencies}" varStatus="i">
        <c:if test="${i.index % 2 == 0}">
            <div class="text-green-400 p-3 font-['Turret_Road']" title="TiendaCrypto">
                <p><c:out value="${coin.commercialName}"/>(<c:out value="${coin.code}"/>) </p>
                <p id="<c:out value="${coin.code}"/>">No data available</p>
            </div>
        </c:if>
        <c:if test="${i.index % 2 != 0}">
            <div class="text-red-400 p-3 font-['Turret_Road']" title="TiendaCrypto">
                <p><c:out value="${coin.commercialName}"/>(<c:out value="${coin.code}"/>) </p>
                <p id="<c:out value="${coin.code}"/>">No data available</p>
            </div>
        </c:if>


    </c:forEach>


</div>

<div class="flex flex-row divide-x w-full">
    <div class="flex flex-col w-full">
        <div class="flex">
            <h1 class="mx-auto my-10 text-4xl font-semibold font-sans text-polar"><messages:message code="uploadYourAdvertisement"/></h1>
        </div>
        <c:set var="cryptocurrencies" value="${cryptocurrencies}" scope="request"/>
        <% request.setCharacterEncoding("UTF-8"); %>
        <div class="flex mx-auto">
            <jsp:include page="../components/uploadForm.jsp"/>
        </div>
    </div>
<%--    <div class="flex flex-col w-full">--%>

<%--        <jsp:include page="../components/uploadMarketData.jsp"/>--%>
<%--    </div>--%>



</div>


<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
<script>
    window.onload = function wrapper() {
        let selectedCode = '<c:out value="${selectedCrypto}"/>';
        if(selectedCode !== ' ')
            updateVars(selectedCode);
        setCryptoPrice();
    }
</script>
</html>