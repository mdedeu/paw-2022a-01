<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">

</head>
<body class="bg-storml overflow-x-hidden">
<jsp:include page="../../components/seller/sellerHeader.jsp"/>
<div class="flex flex-row mt-7">
    <c:set value="${trade.messageCollection}" var="messageCollection" scope="request"/>
    <c:url var="url" value="/chat/send"/>
    <jsp:include page="../../components/chat/chatSnippet.jsp">
        <jsp:param name="otherUsername" value="${otherUser.username.get()}"/>
        <jsp:param name="tradeId" value="${trade.tradeId}"/>
        <jsp:param name="otherUserId" value="${otherUser.id}"/>
        <jsp:param name="senderId" value="${trade.offer.seller.id}"/>
        <jsp:param name="url" value="${url}"/>
        <jsp:param name="otherLastLogin" value="${otherLastLogin}"/>
    </jsp:include>



<%--    Tarjeta con informacion del trade --%>
    <div class="flex flex-col justify-center px-10">
        <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between w-full mb-3">
            <messages:message code="offerInformation"/>
        </div>
        <div class="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mb-12 ">
            <div class="flex font-sans h-fit w-full mt-5">
                <c:if test="${trade.status == 'PENDING' }">
                    <div class="bg-nyellow  w-full text-white  text-center p-2"><messages:message
                            code="pending"/></div>
                </c:if>

                <c:if test="${trade.status == 'REJECTED' }">
                    <div class="bg-nred/[0.6] w-full text-white  text-center p-2"><messages:message
                            code="rejected"/></div>
                </c:if>

                <c:if test="${trade.status == 'ACCEPTED' }">
                    <div class="bg-ngreen w-full text-white text-center p-2"><messages:message
                            code="accepted"/></div>
                </c:if>

                <c:if test="${trade.status == 'SOLD' }">
                    <div class="bg-gray-400 w-full text-white text-center p-2"><messages:message
                            code="sold"/></div>
                </c:if>
            </div>

            <div class="flex flex font-sans my-3  w-56 mx-auto text-semibold">
                <h1 class="mx-auto">
                    <fmt:formatNumber type="number" maxFractionDigits="6" value="${trade.quantity/offer.askingPrice}"/>
                    <c:out value=" ${offer.crypto.code}"/> ⟶ <c:out
                        value="${trade.quantity} "/>ARS</h1>
            </div>

            <c:if test="${!(trade.status =='SOLD')}">
                <div class="flex flex-col">
                    <div class="flex">
                        <h1 class="font-sans mr-2"><messages:message code="buyerUsername"/>:</h1>
                        <h1 class="font-sans font-semibold"><c:out value="${trade.buyerUsername}"/></h1>
                    </div>
                    <div class="flex">
                        <h1 class="font-sans mr-2"><messages:message code="email"/>:</h1>
                        <h1 class="font-sans font-semibold"><c:out value="${trade.user.email}"/></h1>
                    </div>
                    <div class="flex">
                        <h1 class="font-sans mr-2"><messages:message code="phoneNumber"/>:</h1>
                        <h1 class="font-sans font-semibold"><c:out
                                value="${trade.user.phoneNumber}"/></h1>
                    </div>
                    <div class="flex">
                        <h1 class="font-sans mr-2"><messages:message code="rating"/>:</h1>
                        <h1 class="font-sans font-semibold"><c:out value="${trade.user.rating}"/></h1>
                    </div>
                </div>
            </c:if>
            <c:if test="${(trade.status =='SOLD')}">
                <a class="mx-auto bg-gray-200 text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40" href="<c:url value="/receiptDescription/${trade.tradeId}"/>">
                    <messages:message code="help"/>
                </a>
            </c:if>

<%--            &lt;%&ndash;                            CASE - PENDING&ndash;%&gt;--%>
<%--            <c:if test="${trade.status == 'PENDING'}">--%>
<%--                <c:url value="/seller/changeStatus" var="postUrl"/>--%>
<%--                <form:form modelAttribute="statusTradeForm" action="${postUrl}" method="post"--%>
<%--                           cssClass="flex justify-center mx-auto my-3">--%>
<%--                    <form:hidden path="newStatus" id="newStatus-${trade.tradeId}"--%>
<%--                                 value="${trade.status}"/>--%>
<%--                    <form:hidden path="tradeId" value="${trade.tradeId}"/>--%>

<%--                    <button onclick="updateStatus('REJECTED', ${trade.tradeId})" type="submit"--%>
<%--                            class="bg-red-400 text-white p-3  rounded-md font-sans mr-4">--%>
<%--                        <messages:message--%>
<%--                                code="rejectTrade"/></button>--%>
<%--                    <button onclick="updateStatus('ACCEPTED', ${trade.tradeId})" type="submit"--%>
<%--                            class="bg-ngreen text-white p-3 rounded-md font-sans "><messages:message--%>
<%--                            code="acceptTrade"/></button>--%>
<%--                </form:form>--%>
<%--            </c:if>--%>

<%--            &lt;%&ndash;                            CASE - ACCEPTED&ndash;%&gt;--%>
<%--            <c:if test="${trade.status == 'ACCEPTED' }">--%>
<%--                <c:url value="/closeTrade" var="formUrl"/>--%>
<%--                <form:form modelAttribute="soldTradeForm" action="${formUrl}" method="post"--%>
<%--                           cssClass="flex justify-center mx-auto my-3">--%>
<%--                    <form:hidden path="offerId" value="${offer.id}"/>--%>
<%--                    <form:hidden path="trade" value="${trade.tradeId}"/>--%>
<%--                    <button type="submit"--%>
<%--                            class="w-fit bg-frostdr text-white p-3 rounded-md font-sans mx-auto">--%>
<%--                        <messages:message code="soldTrade"/></button>--%>
<%--                </form:form>--%>
<%--            </c:if>--%>

<%--            &lt;%&ndash;                            CASE - REJECTED&ndash;%&gt;--%>
<%--            <c:if test="${!(trade.status =='ACCEPTED') && !(trade.status == 'PENDING')}">--%>
<%--                <div class="flex h-2/5 my-2"></div>--%>
<%--            </c:if>--%>
<%--        <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between mx-auto mb-5">--%>
<%--            Otras órdenes de compra de este comprador--%>
<%--        </div>--%>
<%--        <div class="flex flex-wrap justify-center">--%>
<%--            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2">--%>
<%--                Ejemplo <div class="bg-ngreen w-full text-white text-center p-2"><messages:message code="accepted"/></div>--%>
<%--            </div>--%>
<%--            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2 ">--%>
<%--                Ejemplo <div class="bg-nyellow w-full text-white text-center p-2"><messages:message code="pending"/></div>--%>
<%--            </div>--%>
<%--            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2 ">--%>
<%--                Ejemplo <div class="bg-nyellow w-full text-white text-center p-2"><messages:message code="pending"/></div>--%>
<%--            </div>--%>
<%--            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2">--%>
<%--                Ejemplo <div class="bg-ngreen w-full text-white text-center p-2"><messages:message code="accepted"/></div>--%>
<%--            </div>--%>
<%--            <div class="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl rounded-lg p-7 m-2">--%>
<%--                Ejemplo <div class="bg-nred w-full text-white text-center p-2"><messages:message code="rejected"/></div>--%>
<%--            </div>--%>
<%--        </div>--%>
        </div>
    </div>


</div>
</body>


</html>