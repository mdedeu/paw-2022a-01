
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%--    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" />--%>
</head>
<body>
<h2>Recover your password</h2>
<c:url value="/passwordRecovery" var="postPath"/>
<%--@elvariable id="EmailForm" type="ar.edu.itba.paw.cryptuki.form"--%>
<form:form modelAttribute="EmailForm" action="${postPath}" method="post">
    <div>
        <form:label path="email">Email: </form:label>
        <form:input type="text" path="email"/>
        <form:errors path="email" cssClass="formError" element="p"/>
    </div>
    <div>
        <input type="submit" value="Send"/>
    </div>
</form:form>
</body>
</html>