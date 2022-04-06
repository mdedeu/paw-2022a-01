<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<nav class="bg-white border-gray-200 py-3 h-20"> <!--OK-->
    <div class="container flex flex-wrap mx-auto my-auto px-[10%] h-full justify-between">
        <div class="flex justify-center center w-1/4">
            <div class="flex w-full">
                <a href="<c:url value="/"/>"><img class='object-contain' src="public/images/logo.png" alt="logo"></a>
            </div>
        </div>
        <div class="justify-self-center flex justify-end w-2/3">
            <ul class="flex justify-between content-self-center flex-wrap w-5/6 mt-4">
                <li>
                    <a href="#Cryptos" class="py-2 pr-4 pl-3 text-polarlr font-sans text-lg font-bold">�Qu� monedas puedo comprar?</a>
                </li>
                <li>
                    <a href="#FAQs" class="py-2 pr-4 pl-3 text-polarlr font-sans  font-bold text-lg ">Soporte</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
