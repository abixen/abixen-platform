<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="platformAdminApplication">
<head lang="en">
    <meta charset="UTF-8">
    <title>Admin Panel</title>

    <link href="<c:url value="/lib/lib.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/angular-ui-grid/angular-ui-grid.min.css" />" rel="stylesheet">
    <link href="<c:url value="/lib/toaster/toaster.min.css" />" rel="stylesheet">
    <link href="<c:url value="/admin/application.min.css" />" rel="stylesheet">
    <link href="<c:url value="/admin/modules/abixen/modules.css" />" rel="stylesheet">
</head>
<body>
<ui-view></ui-view>

<script src="<c:url value="/lib/pdfmake/pdfmake.min.js" />"></script>
<script src="<c:url value="/lib/pdfmake/vfs-fonts.js" />"></script>

<script src="<c:url value='/lib/angular.min.js' />"></script>
<script src="<c:url value='/lib/angular-animate.min.js' />"></script>
<script src="<c:url value='/lib/angular-touch.min.js' />"></script>
<script src="<c:url value='/lib/angular-route.min.js' />"></script>
<script src="<c:url value='/lib/angular-resource.min.js' />"></script>

<script src="<c:url value='/lib/angular-ui-grid/angular-ui-grid.min.js' />"></script>
<script src="<c:url value='/lib/angular-translate.min.js' />"></script>
<script src="<c:url value='/lib/angular-ui-router.min.js' />"></script>
<script src="<c:url value="/lib/ui-bootstrap-tpls.min.js" />"></script>
<script src="<c:url value='/lib/show-errors.min.js' />"></script>
<script src="<c:url value='/lib/toaster/toaster.min.js' />"></script>


<script src="<c:url value='/common/modules.min.js' />"></script>
<script src="<c:url value='/admin/application.min.js' />"></script>
<script src="<c:url value='/admin/modules/abixen/modules.js' />"></script>

</body>
</html>
