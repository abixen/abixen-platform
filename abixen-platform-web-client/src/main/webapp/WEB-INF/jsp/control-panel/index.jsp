<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.abixen.platform.common.model.enumtype.ResourcePageLocation" %>
<%@ page import="com.abixen.platform.common.model.enumtype.ResourcePage" %>
<%@ page import="com.abixen.platform.common.model.enumtype.ResourceType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="platformAdminApplication">
<head lang="en">
    <meta charset="UTF-8">
    <title>Admin Panel</title>

    <link href="<c:url value="/lib/lib.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/codemirror.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/ui-grid.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/toaster.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/ng-scrollbar.min.css" />" rel="stylesheet">
    <link href="<c:url value="/control-panel/application.min.css" />" rel="stylesheet">

    <c:forEach var="resource" items="${resources}">
        <c:if test="${resource.resourcePage == ResourcePage.ADMIN && resource.resourcePageLocation == ResourcePageLocation.HEADER && resource.resourceType == ResourceType.JAVASCRIPT}">
            <script src="<c:url value='${resource.relativeUrl}' />"></script>
        </c:if>
        <c:if test="${resource.resourcePage == ResourcePage.ADMIN && resource.resourcePageLocation == ResourcePageLocation.HEADER && resource.resourceType == ResourceType.CSS}">
            <link href="<c:url value='${resource.relativeUrl}' />" rel="stylesheet" type="text/css">
        </c:if>
    </c:forEach>
</head>
<body>
<ui-view></ui-view>

<script src="<c:url value="/lib/pdfmake.min.js" />"></script>
<script src="<c:url value="/lib/vfs_fonts.js" />"></script>

<script src="<c:url value='/lib/angular.min.js' />"></script>
<script src="<c:url value='/lib/angular-animate.min.js' />"></script>
<script src="<c:url value='/lib/angular-touch.min.js' />"></script>
<script src="<c:url value='/lib/angular-route.min.js' />"></script>
<script src="<c:url value='/lib/angular-resource.min.js' />"></script>

<script src="<c:url value='/lib/ui-grid.min.js' />"></script>
<script src="<c:url value='/lib/angular-translate.min.js' />"></script>
<script src="<c:url value='/lib/angular-ui-router.min.js' />"></script>
<script src="<c:url value="/lib/ui-bootstrap-tpls.min.js" />"></script>
<script src="<c:url value='/lib/show-errors.min.js' />"></script>
<script src="<c:url value='/lib/toaster.min.js' />"></script>
<script src="<c:url value='/lib/codemirror.js' />"></script>
<script src="<c:url value='/lib/ui-codemirror.js' />"></script>
<script src="<c:url value='/lib/angular-file-upload.min.js' />"></script>
<script src="<c:url value='/lib/angular-cookies.min.js' />"></script>
<script src="<c:url value='/lib/ng-scrollbar.min.js' />"></script>
<script src="<c:url value='/lib/ckeditor/ckeditor.js' />"></script>
<script src="<c:url value='/common/modules.min.js' />"></script>
<script src="<c:url value='/lib/xml.js' />"></script>

<c:forEach var="resource" items="${resources}">
    <c:if test="${resource.resourcePage == ResourcePage.ADMIN && resource.resourcePageLocation == ResourcePageLocation.BODY && resource.resourceType == ResourceType.JAVASCRIPT}">
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

<script type="text/javascript">
    var externalAdminSidebarItems = [
        <c:forEach var="adminSidebarItem" items="${adminSidebarItems}">
        {
            title: '${adminSidebarItem.title}',
            state: '${adminSidebarItem.angularJsState}',
            orderIndex: ${adminSidebarItem.orderIndex},
            id: 0,
            iconClass: '${adminSidebarItem.iconClass}'
        },
        </c:forEach>
    ];
</script>

<script src="<c:url value='/control-panel/application.min.js' />"></script>

</body>
</html>
