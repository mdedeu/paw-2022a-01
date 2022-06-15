<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:formatNumber type="number" maxFractionDigits="0" value="${param.rating /2 == 0 ? 1 : param.rating/2 }" var="stars"/>

<head>
<%--    <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>--%>
    <script src="<c:url value="/public/js/profilePic.js"/>"></script>

</head>

<%--<body>--%>
<div class="bg-white shadow rounded-lg py-1">
    <div class="flex flex-col">
        <div class="flex flex-col gap-1 items-center">
            <div class="w-full text-sm leading-normal text-gray-400 flex justify-end items-center mt-1">
                <a href="<c:url value="/changePassword"/>" class="mr-3 underline"><messages:message code="changeYourPassword"/></a>
            </div>
            <div class="flex flex-col mt-5">
                <c:url value="/profilePicSelector" var="postUrl"/>
                <form:form modelAttribute="profilePicForm" action="${postUrl}" method="post" enctype="multipart/form-data">
                    <form:errors path="multipartFile" cssClass=" mx-auto text-red-500"/>
                    <div class="group flex flex-col">
                        <form:label path="multipartFile">
                            <div class="flex flex-col mx-auto">
                                <img src="<c:url value="/profilepic/${param.username}"/>"  class=" shadow rounded-full w-36 object-cover mx-auto group-hover:opacity-60" alt="profile" >
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 invisible group-hover:visible mx-auto -mt-20" fill="none" viewBox="0 0 24 24" stroke="#000000" stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z" ></path>
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M15 13a3 3 0 11-6 0 3 3 0 016 0z" ></path>
                                </svg>
                            </div>
                        </form:label>
                        <form:input path="buyer" type="hidden" value="${param.isBuyer}"/>
                        <form:input type="file" path="multipartFile" cssClass="invisible before:bg-red-400 w-0" onchange="showSendButton()"/>
                    </div>
                    <button type="submit" id="sendProfile" class="hidden bg-frostdr text-white  mt-7 p-3 rounded-md font-sans  mx-auto active:cursor-progress"><messages:message code="save"/></button>
                </form:form>
                <p class="mx-auto font-bold text-polard text-2xl mt-2"><c:out value="${param.username}"/></p>
            </div>
        </div>
        <div class="flex flex-col justify-start ml-3 my-2">
<%--            <div class="text-sm leading-normal text-gray-400 flex justify-start items-center mt-1">--%>
<%--                <svg viewBox="0 0 24 24" width="16" height="16" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path><circle cx="12" cy="10" r="3"></circle></svg>--%>
<%--                <p class="ml-3">Caballito, Ciudad de Buenos Aires</p>--%>
<%--            </div>--%>
            <div class="text-sm leading-normal text-gray-400 flex justify-start items-center mt-1">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-telephone" viewBox="0 0 16 16">
                    <path d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.568 17.568 0 0 0 4.168 6.608 17.569 17.569 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.678.678 0 0 0-.58-.122l-2.19.547a1.745 1.745 0 0 1-1.657-.459L5.482 8.062a1.745 1.745 0 0 1-.46-1.657l.548-2.19a.678.678 0 0 0-.122-.58L3.654 1.328zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"/>
                </svg>
                <p class="ml-3"><c:out value="${param.phoneNumber}"></c:out></p>
            </div>
            <div class="text-sm leading-normal text-gray-400 flex justify-start items-center mt-1">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
                    <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
                </svg>
                <p class="ml-3"><c:out value="${param.email}"></c:out></p>
            </div>
        </div>
    </div>
    <div class="flex justify-center items-center gap-2 my-3">
        <div class="font-semibold text-center ">
            <p class="text-black">${param.ratingCount}</p>
            <span class="text-gray-400"><messages:message code="tradeQuantity"/></span>
        </div>
        <c:if test="${param.ratingCount > 0 }">
            <div class="flex flex-col">
                <div class="my-auto mx-2">
                    <c:forEach begin="0" end="${stars-1}">
                        <span class="fa fa-star" style="color: orange"></span>
                    </c:forEach>
                    <c:forEach begin="${stars}" end="4">
                        <span class="fa fa-star" style="color: gray"></span>
                    </c:forEach>
                </div>
                <h4 class=" font-semibold text-gray-400 font-sans text-center">
                    <messages:message code="rating"/></h4>
            </div>
        </c:if>
    </div>

</div>