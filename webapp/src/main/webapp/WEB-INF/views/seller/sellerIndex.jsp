<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="message" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="name" var="username"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
    <script src="<c:url value="/public/js/filterLink.js"/>" ></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
    <title>cryptuki</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>"/>
</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../../components/seller/sellerHeader.jsp"/>

<div class="flex h-full w-full px-20 my-10">
    <!-- Left Panel: chat and seller stats -->
    <div class="flex flex-col h-full mx-20 w-1/5">
        <div>
            <jsp:include page="../../components/seller/sellerStatsCard.jsp">
                <jsp:param name="username" value="${username}"/>
                <jsp:param name="email" value="${user.email}"/>
                <jsp:param name="phoneNumber" value="${user.phoneNumber}"/>
                <jsp:param name="rating" value="${user.rating}"/>
                <jsp:param name="ratingCount" value="${user.ratingCount}"/>
                <jsp:param name="isBuyer" value="${false}"/>
            </jsp:include>
        </div>
        <div class="mx-auto mt-10">
            <a href="<c:url value="/seller/upload"/>"
               class="py-2 pr-4 pl-3 text-xl text-white font-bold rounded-lg bg-frost border-2 border-white my-auto mx-auto">Subir anuncio</a>
        </div>
<%--        <div class="my-5">--%>
<%--            <jsp:include page="../../components/seller/sellerChatCard.jsp"/>--%>
<%--        </div>--%>
    </div>

    <!-- Middle Panel: trade -->

    <div class="flex flex-wrap h-full mr-20 w-3/5">

        <c:forEach var="offer" items="${offerList}">
            <jsp:include page="../../components/seller/sellerLargeOfferWithTradesCard.jsp">
                <jsp:param name="currency" value="${offer.crypto.code}"/>
                <jsp:param name="offerId" value="${offer.id}"/>
                <jsp:param name="askingPrice" value="${offer.askingPrice}"/>
                <jsp:param name="minCoinAmount" value="${offer.minQuantity}"/>
                <jsp:param name="maxCoinAmount" value="${offer.maxQuantity}"/>
                <jsp:param name="offerDate" value="${offer.date.toLocalDate()}"/>
                <jsp:param name="offerLocation" value="Caballito"/>
            </jsp:include>
        </c:forEach>



        <div  class="flex flex-col justify-center w-full mx-auto mt-10">
            <c:forEach var="trade" items="${tradeList}">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../../components/horizontalSellingTradeCard.jsp">
                    <jsp:param name="username" value="${username}"/>
                    <jsp:param name="askedPrice" value="${trade.askedPrice}"/>
                    <jsp:param name="quantity" value="${trade.quantity}"/>
                    <jsp:param name="tradeStatus" value="${trade.status.toString()}"/>
                    <jsp:param name="cryptoCurrencyCode" value="${trade.cryptoCurrency.code}"/>
                    <jsp:param name="tradeId" value="${trade.tradeId}"/>
                    <jsp:param name="offerId" value="${trade.offerId}"/>
                </jsp:include>
            </c:forEach>
        </div>

        <c:if test="${noSellingTrades}">
            <h2 class="text-center text-3xl font-semibold font-sans text-polar mt-4"><messages:message code="noSellingProposalReceived"/></h2>
            <a href="<c:url value="/seller/"/>" class="h-12 bg-frost text-white p-3 font-sans rounded-lg w-fit mx-auto mt-10"><messages:message code="seeAdvertisements"/></a>
        </c:if>

        <c:if test="${!noSellingTrades}">
            <div class="flex flex-col mt-3">
                <% request.setCharacterEncoding("utf-8"); %>
                <jsp:include page="../../components/paginator.jsp">
                    <jsp:param name="activePage" value="${activePage}"/>
                    <jsp:param name="pages" value="${pages}"/>
                    <jsp:param name="baseUrl" value="/mytrades"/>
                </jsp:include>
                <h1 class="mx-auto text-gray-400 mx-auto mt-3"><messages:message code="totalPageAmount"/>: ${pages}</h1>
            </div>
        </c:if>
    </div>

    <!-- Right Panel: crypto dashboard -->

<%--    <div class="flex flex-col h-full mr-10 w-1/5">--%>
<%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
<%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
<%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
<%--        <jsp:include page="../../components/seller/sellerCryptoMetric.jsp"/>--%>
<%--    </div>--%>



</div>





</body>


</html>