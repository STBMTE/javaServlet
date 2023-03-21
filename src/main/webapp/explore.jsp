<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>helo</title>
    <link rel="stylesheet" href="https://unpkg.com/tachyons@4.12.0/css/tachyons.min.css"/>
    <style type="text/css">
        html{
            font-family: "Roboto Light", serif;
            font-size: 18px;
        }
        li {
            list-style-type: none;
        }
        .icon{
            width: 1em;
            height: 1em;
        }
        .flex-row .pa2:not(:first-child){
            margin-top: 0;
            margin-left: 8px;
        }
        ul .inline-flex{
            margin-top: 8px;
        }
        a{
            text-decoration: none;
        }
    </style>
</head>
<body>
<h3 class="ma0 pa2 normal">${date}</h3>
<h1 class="ma0 pa2">${path}</h1>
<hr/>
<div class="flex flex-column pt2 pb2">
    <div class="inline-flex ml2">
        <i data-feather="arrow-up-circle" class="icon"></i>
        <a class="ml2" href="?path=${path.substring(0, path.lastIndexOf("\\") + (path.lastIndexOf("\\") != path.indexOf("\\") ? 0 : 1))}">Up</a><br>
    </div>
    <ul class="flex-row w-100 ma0 pa0">
        <li class="pa2">
            <span class="b">Directories</span>
            <ul class="flex-column flex ma0 pa0">
                <c:forEach var="directory" items="${directories}">
                    <li class="inline-flex">
                        <i data-feather="folder" class="red icon"></i>
                        <a href="?path=${directory.file.getAbsolutePath()}" class="ml2">${directory.file.getName()}/</a>
                        <span>${directory.length} байт</span>
                    </li>
                </c:forEach>
            </ul>
        </li>
    </ul>
    <ul class="flex-row w-100 ma0 pa0">
        <li class="pa2">
            <span class="b">Files</span>
            <ul class="flex-column flex ma0 pa0">
                <c:forEach var="file" items="${files}">
                    <li class="inline-flex">
                        <i data-feather="file" class="yellow icon"></i>
                        <div>
                            <a href="?path=${file.file.getAbsolutePath()}" class="ml2">${file.file.getName()}</a>
                            <span class="ml2">${file.length} байт</span>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </li>
    </ul>

</div>
</body>
<script src="https://unpkg.com/feather-icons"></script>
<script>
    feather.replace()
</script>
</html>