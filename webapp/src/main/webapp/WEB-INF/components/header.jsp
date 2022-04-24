<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="bg-[#FAFCFF] border-gray-200 py-3 h-20"> <!--OK-->
    <div class="container flex flex-wrap mx-auto my-auto px-[10%] h-full justify-between">
        <div class="flex justify-center center w-[15%] my-auto xl:w-1/4">
            <div class="flex w-full my-2">
                <a href="<c:url value="/"/>"><img class='object-contain' src="<c:url value="/public/images/logo.png"/>" alt="logo"></a>
            </div>
        </div>
        <div class="flex justify-end">
            <ol class="flex justify-between mt-4">
                <li>
                    <a href="<c:url value="/"/>" class="py-2 pr-4 pl-3 text-polar text-xl font-bold hover:underline hover:decoration-frostdr hover:underline-offset-8">Explora</a>
                </li>
                <li>
                    <a href="<c:url value="/coins"/>" class="py-2 pr-4 pl-3 text-polar text-xl font-bold hover:underline hover:decoration-frostdr hover:underline-offset-8">Criptomonedas</a>
                </li>
                <li>
                    <a href="<c:url value="/contact"/>" class="py-2 pr-4 pl-3 text-polar font-bold text-xl hover:underline hover:decoration-frostdr hover:underline-offset-8 ">Contáctate</a>
                </li>
                <c:choose>
                    <c:when test="${param.username == null}">
                        <li class="mt-1">
                            <a href="<c:url value="/login"/>" class="py-2 pr-4 pl-3 border-2 border-polar text-storml font-bold rounded-lg bg-polar my-auto mx-3">Inicia Sesión</a>
                        </li>
                        <li class="mt-1">
                            <a href="<c:url value="/register"/>" class="py-2 pr-4 pl-3  text-polar font-bold rounded-lg bg-storml border-2 border-polar my-auto mx-auto">Registrate</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="<c:url value="/login"/>" class="py-2 pr-4 pl-3 text-polar font-bold text-xl">
                                <div class="flex flex-row">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="polar" stroke-width="2">
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                                    </svg>
                                    <div class="mx-2 font-sans text-sm text-polar">
                                            ${param.username}
                                    </div>

                                </div>

                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/logout"/>" class="py-2 pr-4 pl-3 text-polar font-bold text-xl hover:underline hover:decoration-frostdr hover:underline-offset-8 ">Logout</a>
                        </li>

                    </c:otherwise>
                </c:choose>
            </ol>
        </div>
    </div>
</nav>
