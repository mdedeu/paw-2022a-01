<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="flex flex-col">
    <c:url value="/buy" var="postUrl"/>
    <%--@elvariable id="offerBuyForm" type="ar.edu.itba.paw.cryptuki.form.OfferBuyForm"--%>
    <form:form modelAttribute="offerBuyForm" action="${postUrl}" method="post" class="flex flex-col">

        <input type="hidden" name="email"  id="email" value="${param.userEmail}">
        <div class="flex flex-col justify-center mt-3">
            <form:label  path="amount" class="text-xl font-sans text-polard font-semibold mb-3 text-center">¿Cuántos ARS quieres gastar? *</form:label>
            <form:errors cssClass=" mx-auto text-red-500"/>
            <form:errors path="amount" cssClass=" mx-auto text-red-500"/>

            <div class="flex flex-col justify-center mx-auto">
                <form:input type="number" path="amount" class="h-10 justify-center rounded-lg p-3 mx-5" step=".01"/>
                <div class="flex flex-row mx-auto">
                    <h1 class="my-auto" id="coinAmount">~0.00000</h1>
                    <p class="my-auto mx-2 font-semibold"><c:out value="${param.coin}"/> </p>
                </div>
            </div>
        </div>
        <div class="flex flex-col mt-3 mx-52">
            <form:errors path="message" cssClass="text-red-500 mx-auto"/>
            <form:label path="message" class="text-xl font-sans text-polard font-semibold mb-3 text-center ">Tu mensaje: *</form:label>
            <div class="flex-row justify-center">
                <form:textarea class="min-w-full h-36 rounded-lg mx-auto p-5"  path="message" placeholder="Juntemonos en el Starbucks de 9 de Julio." />
            </div>

        </div>
        <div class="flex flex-row p-5 mx-60">
            <a class="bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto" href="<c:url value="/"/>">Cancelar</a>
            <button type="submit" class="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans min-w-[25%] mx-auto active:cursor-progress">Abrir trade</button>
        </div>
        <form:input type="hidden" path="offerId"  value="${param.offerId}"/>
    </form:form>
</div>

<script>
    document.getElementsByName('amount')[0].addEventListener('change', changeVal)
    function changeVal(){
            let value = document.getElementById('amount').value;

            if( value >= 0){
                let newVal =  value / ${param.price};
                document.getElementById('coinAmount').innerHTML = newVal.toFixed(5);
            }
            let amountCheck = ${param.maxCoinAmount * param.price};
            let minAmount = ${param.minCoinAmount * param.price};
            if ( value > amountCheck){
                console.log("max violation")
                document.getElementById('maxError').classList.remove("hidden");
            }
            if ( value < minAmount ){
                console.log("min violation")
                document.getElementById('minError').classList.remove("hidden");
            }
            if( value >= minAmount && value <= amountCheck){
                console.log("ok")
                document.getElementById('maxError').classList.add("hidden");
                document.getElementById('minError').classList.add("hidden");
            }
    }
</script>