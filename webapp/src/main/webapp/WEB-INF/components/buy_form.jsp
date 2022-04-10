<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex flex-col">
    <c:url value="/buy" var="postUrl"/>
    <form:form modelAttribute="offerBuyForm" action="${postUrl}" method="post" class="flex flex-col min-w-[50%]">

        <div class="flex flex-col justify-center mt-3">
            <form:errors path="email" cssClass=" mx-auto text-red-500"/>
            <form:label  path="email" class="text-xl font-sans text-polard font-semibold mb-3 text-center">Email *</form:label>
            <div class="flex-row justify-center mx-96">
                <form:input type="email" path="email" class=" min-w-full h-10 justify-center rounded-lg p-3 " placeholder="roman@gmail.com"/>
            </div>
        </div>

        <div class="flex flex-col justify-center mt-3">
            <form:errors path="amount" cssClass=" mx-auto text-red-500"/>
            <form:label  path="amount" class="text-xl font-sans text-polard font-semibold mb-3 text-center">¿Cuantos ARS querés gastar? *</form:label>
            <div class="flex-row justify-center mx-auto">
                <form:input type="number" path="amount" class=" min-w-full h-10 justify-center rounded-lg p-3"/>
            </div>

        </div>
        <div class="flex flex-col mt-3">
            <form:errors path="message" cssClass="text-red-500 mx-auto"/>
            <form:label path="message" class="text-xl font-sans text-polard font-semibold mb-3 text-center ">Mensaje *</form:label>
            <div class="flex-row justify-center mx-96">
                <form:textarea class="min-w-full h-36 rounded-lg mx-auto p-5"  path="message" placeholder="Soy de Quilmes como vos" />
            </div>

        </div>
        <div class="flex flex-row p-5 mx-60">
            <a class="bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" href="<c:url value="/"/>">Cancelar</a>
            <button type="submit" class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto">Enviar</button>
        </div>
        <form:input type="hidden" path="offerId"  value="${param.offerId}"/>
    </form:form>
</div>