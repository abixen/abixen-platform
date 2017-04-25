<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.abixen.platform.common.model.enumtype.ResourcePageLocation" %>
<%@ page import="com.abixen.platform.common.model.enumtype.ResourcePage" %>
<%@ page import="com.abixen.platform.common.model.enumtype.ResourceType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Abixen Platform</title>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="dashboard sample app">

    <link href="<c:url value="/lib/lib.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/ui-grid.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/toaster.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/xeditable.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/ng-scrollbar.min.css" />" rel="stylesheet">
    <link href="<c:url value="/application/application.min.css" />" rel="stylesheet">

    <c:forEach var="resource" items="${resources}">
        <c:if test="${resource.resourcePage == ResourcePage.APPLICATION && resource.resourcePageLocation == ResourcePageLocation.HEADER && resource.resourceType == ResourceType.JAVASCRIPT}">
            <script src="<c:url value='${resource.relativeUrl}' />"></script>
        </c:if>
        <c:if test="${resource.resourcePage == ResourcePage.APPLICATION && resource.resourcePageLocation == ResourcePageLocation.HEADER && resource.resourceType == ResourceType.CSS}">
            <link href="<c:url value='${resource.relativeUrl}' />" rel="stylesheet" type="text/css">
        </c:if>
    </c:forEach>
</head>
<body ng-app="platformApplication">

<div ui-view></div>
<script src="<c:url value="/lib/pdfmake.min.js" />"></script>
<script src="<c:url value="/lib/vfs_fonts.js" />"></script>
<script src="<c:url value='/lib/angular.min.js' />"></script>
<script src="<c:url value='/lib/angular-resource.min.js' />"></script>
<script src="<c:url value='/lib/angular-animate.min.js' />"></script>
<script src="<c:url value='/lib/angular-translate.min.js' />"></script>
<script src="<c:url value='/lib/angular-ui-router.min.js' />"></script>
<script src="<c:url value="/lib/ui-bootstrap-tpls.min.js" />"></script>
<script src="<c:url value='/lib/angular-aside.min.js' />"></script>
<script src="<c:url value='/lib/Sortable.min.js' />"></script>
<script src="<c:url value='/lib/show-errors.min.js' />"></script>
<script src="<c:url value='/lib/toaster.min.js' />"></script>
<script src="<c:url value='/lib/xeditable.min.js' />"></script>
<script src="<c:url value='/lib/angular-file-upload.min.js' />"></script>
<script src="<c:url value='/lib/angular-cookies.min.js' />"></script>
<script src="<c:url value='/lib/ui-grid.min.js' />"></script>
<script src="<c:url value='/lib/ng-scrollbar.min.js' />"></script>
<script src="<c:url value='/lib/moment/moment.js'/>"></script>
<script src="<c:url value='/lib/moment/locale/en-gb.js'/>"></script>
<script src="<c:url value='/lib/moment/locale/pl.js'/>"></script>
<script src="<c:url value='/lib/moment/locale/es.js'/>"></script>
<script src="<c:url value='/lib/moment/locale/ru.js'/>"></script>
<script src="<c:url value='/lib/moment/locale/uk.js'/>"></script>
<script src="<c:url value='/lib/angular-moment.js'/>"></script>
<script src="<c:url value='/common/modules.min.js' />"></script>

<c:forEach var="resource" items="${resources}">
    <c:if test="${resource.resourcePage == ResourcePage.APPLICATION && resource.resourcePageLocation == ResourcePageLocation.BODY && resource.resourceType == ResourceType.JAVASCRIPT}">
        <script src="<c:url value='${resource.relativeUrl}' />"></script>
    </c:if>
</c:forEach>

<script type="text/javascript">
    var externalModules = [
        <c:forEach var="angularJsModule" items="${angularJsModules}">
            '${angularJsModule}',
        </c:forEach>
    ];
</script>

<script src="<c:url value='/application/application.min.js' />"></script>

</body>
</html>