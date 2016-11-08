<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.abixen.platform.core.model.enumtype.ResourceLocation" %>
<%@ page import="com.abixen.platform.core.model.enumtype.ResourceType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Abixen Platform</title>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="dashboard sample app">

    <link href="<c:url value="/stylesheets/application/application.min.css" />" rel="stylesheet">
    <link href="<c:url value="/application/modules/abixen/modules.css" />" rel="stylesheet">
    <link href="<c:url value="/stylesheets/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/stylesheets/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/stylesheets/common/fonts/roboto.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/toaster/toaster.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/angular-xeditable/xeditable.css" />" rel="stylesheet">

    <c:forEach var="resource" items="${resources}">
        <c:if test="${resource.resourceLocation == ResourceLocation.HEADER && resource.resourceType == ResourceType.JAVASCRIPT}">
            <script src="<c:url value='${resource.relativeUrl}' />"></script>
        </c:if>
        <c:if test="${resource.resourceLocation == ResourceLocation.HEADER && resource.resourceType == ResourceType.CSS}">
            <link href="<c:url value='${resource.relativeUrl}' />" rel="stylesheet" type="text/css">
        </c:if>
    </c:forEach>

</head>
<body ng-app="platformApplication">

<div ui-view></div>

<script src="<c:url value='/lib/angular.min.js' />"></script>
<script src="<c:url value='/lib/angular-resource.min.js' />"></script>
<script src="<c:url value='/lib/angular-animate.min.js' />"></script>
<script src="<c:url value='/lib/angular-ui-grid/angular-ui-grid.min.js' />"></script>
<script src="<c:url value='/lib/angular-ui-router.min.js' />"></script>
<script src="<c:url value="/lib/ui-bootstrap-tpls.min.js" />"></script>
<script src="<c:url value='/lib/angular-aside.min.js' />"></script>
<script src="<c:url value='/lib/sortable.min.js' />"></script>
<script src="<c:url value='/lib/show-errors.min.js' />"></script>
<script src="<c:url value='/lib/toaster/toaster.min.js' />"></script>
<script src="<c:url value='/lib/angular-xeditable/xeditable.min.js' />"></script>
<script src="<c:url value='/lib/angular-file-upload.min.js' />"></script>
<script src="<c:url value='/lib/angular-cookies.js' />"></script>

<script src="<c:url value='/common/modules.js' />"></script>
<script src="<c:url value='/application/application.js' />"></script>
<script src="<c:url value='/application/modules.js' />"></script>

<c:forEach var="resource" items="${resources}">
    <c:if test="${resource.resourceLocation == ResourceLocation.BODY && resource.resourceType == ResourceType.JAVASCRIPT}">
        <script src="<c:url value='${resource.relativeUrl}' />"></script>
    </c:if>
</c:forEach>

</body>
</html>
