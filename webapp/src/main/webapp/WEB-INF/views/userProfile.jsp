<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="messages" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<fmt:formatNumber type="number" var="stars" value="${user.rating/2 == 0 ? 1: user.rating/2}"/>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="<c:url  value="/public/js/tailwind.config.js"/>"></script>
  <script src="<c:url value="/public/js/feedback.js"/>"></script>
  <script src="<c:url value="/public/js/successMessageShow.js"/>"></script>
  <script src="<c:url value="/public/js/profilePic.js"/>"></script>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<c:url value="/public/css/blobs.css"/>">
  <title>cryptuki</title>
  <link rel="icon" type="image/x-icon" href="<c:url value="/public/images/favicon.ico"/>">
</head>
<body class="bg-storml overflow-x-hidden flex flex-col">
<sec:authentication property="name" var="username"/>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/buyer/buyerHeader.jsp"/>
<% request.setCharacterEncoding("utf-8"); %>
<c:if test="${updatedPass == true}">
  <c:set var="updatedPass"><messages:message code="updatedPass"/></c:set>
  <div id="confirmationToggle" class="hidden">
    <jsp:include page="../components/confirmationToggle.jsp">
      <jsp:param name="title" value="Contraseña cambiada con exito [TRADUCIR]"/>
    </jsp:include>
  </div>
</c:if>
<div class="flex">
  <h1 class="mx-auto my-10 text-4xl font-semibold font-sans text-polar"><messages:message code="myProfile"/></h1>
</div>
<div class=" py-12 px-40 mx-auto rounded-lg bg-stormd/[0.9] flex  border-2 border-polard flex flex-row">
  <div class="flex flex-col height-auto mx-auto my-auto">
    <div class="flex flex-col">
      <c:url value="/profilePicSelector" var="postUrl"/>
      <form:form modelAttribute="profilePicForm" action="${postUrl}" method="post" enctype="multipart/form-data">
        <form:errors path="multipartFile" cssClass=" mx-auto text-red-500"/>
        <div class="group flex flex-col">
          <form:label path="multipartFile">
            <div class="flex flex-col mx-auto">
              <img src="<c:url value="/profilepic/${username}"/>"  class=" shadow rounded-full w-36 object-cover mx-auto group-hover:opacity-60" alt="profile" >
              <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 invisible group-hover:visible mx-auto -mt-20" fill="none" viewBox="0 0 24 24" stroke="white" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z" ></path>
                <path stroke-linecap="round" stroke-linejoin="round" d="M15 13a3 3 0 11-6 0 3 3 0 016 0z" ></path>
              </svg>
            </div>
          </form:label>
          <form:input path="buyer" type="hidden" value="${true}"/>
          <form:input type="file" accept="image/jpeg image/gif image/png" path="multipartFile" cssClass="invisible before:bg-red-400 w-0" onchange="showSendButton()"/>
        </div>
        <button type="submit" id="sendProfile" class="invisible bg-frostdr text-white  mt-7 p-3 rounded-md font-sans  mx-auto active:cursor-progress"><messages:message code="save"/></button>
      </form:form>
      <p class="mx-auto font-bold text-polard text-2xl -mt-10"><c:out value="${username}"/></p>
    </div>
  </div>

  <div class="flex flex-col">
    <div class="flex  rounded-lg h-12 mb-5 mt-5 mr-5 mx-10">
      <div class="flex">
        <h2 class="text-2xl font-semibold font-sans text-polar my-auto"><messages:message code="emailAddress"/>: </h2>
      </div>
      <div class="flex">
        <h2 class="text-xl font-semibold font-sans text-polar text-left my-auto ml-2"><c:out value="${user.email}"/></h2>
      </div>
    </div>
    <div class="flex  rounded-lg h-12 mb-5 mt-5 mr-5 mx-10">
      <div class="flex">
        <h2 class="text-2xl font-semibold font-sans text-polar text-left my-auto" ><messages:message code="phoneNumber"/>: </h2>
      </div>
      <div class="flex">
        <h2 class="text-xl font-semibold font-sans text-polar my-auto ml-2"><c:out value="${user.phoneNumber}"/></h2>
      </div>
    </div>
    <c:if test="${user.ratingCount >0}">
      <div class="flex flex-col rounded-lg h-12 mb-5 mt-5 mr-5 mx-10">
        <div class="flex flex-col divide-x">
          <h4 class="text-gray-400 font-sans"><messages:message code="rating"/>: </h4>
          <div class="my-auto ml-2">
            <c:forEach begin="0" end="${stars-1}">
              <span class="fa fa-star" style="color: orange"></span>
            </c:forEach>
            <c:forEach begin="${stars}" end="4">
              <span class="fa fa-star" style="color: gray"></span>
            </c:forEach>
          </div>
        </div>
      </div>
    </c:if>

    <div class="flex  rounded-lg h-12 mb-5  mt-5 mr-5 ">
      <div class="flex">
        <h2 class="text-2xl font-semibold font-sans text-polar text-left my-auto"><messages:message code="tradeQuantity"/>:</h2>
      </div>
      <div class="flex">
        <h2 class="text-xl font-semibold font-sans text-polar my-auto ml-2"><c:out value="${user.ratingCount}"/></h2>
      </div>
    </div>
    <div class="flex mt-10 justify-center" >
      <div>
        <a href="<c:url value="/changePassword"/>" class="mx-auto bg-frost  hover:bg-frost/[.6] text-white p-3 rounded-md font-sans text-center"><messages:message code="changePassword"/></a>
      </div>
      <div>
        <a href="<c:url value="/complaints"/>" class="mx-auto bg-frostdr  hover:bg-frostdr/[.6] text-white p-3 rounded-md font-sans text-center ml-5"><messages:message code="seeComplaints"/></a>
      </div>
    </div>
  </div>
</div>

<div class="shape-blob"></div>
<div class="shape-blob one"></div>
<div class="shape-blob two"></div>
<div class="shape-blob" style="left: 50%"></div>

<div class="shape-blob" style="left: 5%; top: 80%"></div>
</body>
</html>