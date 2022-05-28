<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authentication property="name" var="username"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="shadow-xl flex rounded-lg justify-between py-5 px-12 bg-[#FAFCFF] mt-3 mx-10">

    <div class="flex flex-col  font-sans justify-center">
        <h1 class="font-sans"><messages:message code="youWouldReceive"/>: </h1>
        <h3 class="font-sans font-semibold"><c:out value="${param.quantity}"/>$AR</h3>
    </div>

    <div class="flex flex-col font-sans justify-center">
        <h1 class="font-sans"><messages:message code="onExchangeOf"/>: </h1>
        <div class="flex">
            <h1 class="text-xl font-sans font-bold"><fmt:formatNumber type="number" maxFractionDigits="6" value="${param.quantity/param.askedPrice}"/></h1>
            <h1 class="text-xl font-sans font-bold mx-2"><c:out value="${param.cryptoCurrencyCode}"/></h1>
            <img src="<c:url value="/public/images/${param.cryptoCurrencyCode}.png"/>" alt="<c:out value="${param.cryptoCurrencyCode}"/>" class="w-7 h-7 mx-auto"/>
        </div>
    </div>

    <div class="flex font-sans my-auto">
        <c:if test="${param.tradeStatus == 'PENDING' }">
            <div class="bg-nyellow  w-full text-white  text-center p-2"><messages:message code="pending"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'REJECTED' }">
            <div class="bg-nred/[0.6] w-full text-white  text-center p-2"><messages:message code="rejected"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'ACCEPTED' }">
            <div class="bg-ngreen w-full text-white text-center p-2"><messages:message code="accepted"/> </div>
        </c:if>

        <c:if test="${param.tradeStatus == 'SOLD' }">
            <div class="bg-gray-400 w-full text-white text-center p-2"><messages:message code="sold"/> </div>
        </c:if>
    </div>

            <c:if test="${param.tradeStatus.equals('PENDING')}">
                <c:url value="/changeStatus" var="postUrl"/>
                <form:form modelAttribute="statusTradeForm" action="${postUrl}" method="post" cssClass="flex w-2/5 justify-center my-auto">
                    <form:hidden path="newStatus" value="${param.tradeStatus}"/>
                    <form:hidden path="tradeId" value="${param.tradeId}"/>

                    <button onclick="updateStatus('REJECTED')" type="submit"
                            class="bg-red-400 text-white p-3  rounded-md font-sans mr-4"><messages:message
                            code="rejectTrade"/></button>
                    <button onclick="updateStatus('ACCEPTED')" type="submit"
                            class="bg-ngreen text-white p-3 rounded-md font-sans "><messages:message
                            code="acceptTrade"/></button>
                </form:form>
            </c:if>
            <c:if test="${param.tradeStatus.equals('ACCEPTED')}">
                <c:url value="/closeTrade" var="formUrl"/>
                <form:form modelAttribute="soldTradeForm" action="${formUrl}" method="post" cssClass="flex w-2/5 justify-center my-auto" >
                    <form:hidden path="offerId" value="${param.offerId}"/>
                    <form:hidden path="trade" value="${param.tradeId}"/>
                    <button type="submit"
                            class="w-fit bg-frostdr text-white p-3 rounded-md font-sans mx-auto">
                        <messages:message code="soldTrade"/></button>
                </form:form>
            </c:if>

        <c:if test="${!(param.tradeStatus.equals('ACCEPTED')) && !(param.tradeStatus.equals('PENDING'))}">
            <div class="flex w-2/5"></div>
        </c:if>

</div>

<script>
    function updateStatus(newStatusName) {
        document.getElementById('newStatus').setAttribute('value',newStatusName)
    }
</script>

