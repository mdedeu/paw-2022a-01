<%--
  Created by IntelliJ IDEA.
  User: marcos
  Date: 9/4/2022
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>P2P Crypto</title>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        polar: '#3B4252',
                        polard: '#2E3440',
                        polarl: '#434C5E',
                        polarlr: '#4C566A',

                        storm: '#E5E9F0',
                        stormd: '#D8DEE9',
                        storml: '#ECEFF4',

                        frostl: '#8FBCBB',
                        frost: '#88C0D0',
                        frostd: '#81A1C1',
                        frostdr: '#5E81AC',

                        nred: '#BF616A',
                        norange: '#D08770',
                        nyellow: '#EBCB8B',
                        ngreen: '#A3BE8C',
                        npurple: '#B48EAD'

                    },
                    fontFamily: {
                        sans: ['Roboto', 'sans-serif'],
                    },
                }
            }
        }
    </script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">

</head>
<body>
<jsp:include page="../components/header.jsp"/>
<div class="flex ">
    <div class="flex flex-col mx-auto mt-10">
        <h2 class="font-sans font-semibold text-polard text-2xl text-center">Estás por comprar</h2>
        <h1 class="text-center text-4xl font-bold">100000 ARS / ETH</h1>
    </div>
</div>
</body>
</html>
