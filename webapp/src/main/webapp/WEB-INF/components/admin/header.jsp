<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="w-80 bg-frostdr h-full flex flex-col fixed">
    <a href="<c:url value="/admin"/>"/>
        <img src="<c:url value="/public/images/logo_white.png"/>" class="w-60 mx-auto mt-10">
    </a>

    <ol class="flex flex-col divide-y mt-10">
        <li></li>
        <li class="flex py-10">
            <a class="flex flex-col mx-auto" href="<c:url value="/admin"/>">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mx-auto" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <h1 class="text-white text-center"><messages:message code="pendingClaims"/></h1>
            </a>
        </li>
        <li class="flex py-10">
            <a class="flex flex-col mx-auto" href="<c:url value="/admin/assigned"/>">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mx-auto" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                </svg>
                <h1 class="text-white text-center"><messages:message code="self-assignedClaims"/></h1>
            </a>
        </li>
        <li class="flex py-10">
            <a class="flex flex-col mx-auto" href="<c:url value="/admin/solved"/>">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mx-auto" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7" />
                </svg>
                <h1 class="text-white text-center"><messages:message code="solvedClaims"/></h1>
            </a>
        </li>
        <li class="flex py-10">
            <a class="flex flex-col mx-auto" href="<c:url value="/logout"/>">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mx-auto" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                </svg>
                <h1 class="text-white text-center"><messages:message code="logout"/></h1>
            </a>
        </li>
        <li></li>
    </ol>
</nav>
