<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <title>Abixen Platform</title>

    <link href="<c:url value="/lib/lib.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/toaster.min.css" />" rel="stylesheet">
    <link href="<c:url value="/login/application.min.css" />" rel="stylesheet">
</head>
<body ng-app="platformLoginApp">
<ui-view></ui-view>

<script src="<c:url value='/lib/angular.min.js' />"></script>
<script src="<c:url value='/lib/angular-route.min.js' />"></script>
<script src="<c:url value='/lib/angular-ui-router.min.js' />"></script>
<script src="<c:url value='/lib/toaster.min.js' />"></script>
<script src="<c:url value='/login/application.min.js' />"></script>
</body>
</html>
