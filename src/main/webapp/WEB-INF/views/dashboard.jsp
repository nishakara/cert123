<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title> | Certmonitor | </title>

    <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/nprogress/nprogress.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/build/css/custom.min.css" />" rel="stylesheet">
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div id="monitorModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="monitorModalTitle">Modal title</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <pre style="white-space: pre-wrap;" id="monitorModalContent">Modal content message</pre>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <!-- <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span></span></a> -->
                </div>

                <div class="clearfix"></div>

                <!-- menu profile quick info -->
                <div class="profile clearfix">
                    <div class="profile_pic">
                        <img src="<c:url value="/resources/images/img.jpg" />" alt="..." class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>Welcome,</span>
                        <h2>User</h2>
                    </div>
                </div>
                <br/>

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <!-- <h3>General</h3> -->
                        <ul class="nav side-menu">
                            <li><a><i class="fa fa-home"></i> Status <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="<c:url value="/" />">Dashboard</a></li>
                                </ul>
                            </li>
                            <li><a><i class="fa fa-edit"></i> Add New <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="<c:url value="/monitors" />">Add New Monitor</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <div class="menu_section">
                        <ul class="nav side-menu">
                            </li>
                        </ul>
                    </div>

                </div>
                <!-- /sidebar menu -->

                <!-- /menu footer buttons -->
                <div class="sidebar-footer hidden-small">
                    <%--              <a data-toggle="tooltip" data-placement="top" title="Settings">--%>
                    <%--                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>--%>
                    <%--              </a>--%>
                    <%--              <a data-toggle="tooltip" data-placement="top" title="FullScreen">--%>
                    <%--                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>--%>
                    <%--              </a>--%>
                    <%--              <a data-toggle="tooltip" data-placement="top" title="Lock">--%>
                    <%--                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>--%>
                    <%--              </a>--%>
                    <a data-toggle="tooltip" data-placement="top" title="Logout" href="<c:url value="/login" />"
                       id="LogoutButton">
                        <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                    </a>
                </div>
                <!-- /menu footer buttons -->
            </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <div class="nav toggle">
                    <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                </div>
                <nav class="nav navbar-nav">
                    <ul class=" navbar-right">
                        <li class="nav-item dropdown open" style="padding-left: 15px;">
                            <a href="javascript:;" class="user-profile dropdown-toggle" aria-haspopup="true"
                               id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">
                                <img src="<c:url value="/resources/images/img.jpg" />" alt="">User
                            </a>
                            <div class="dropdown-menu dropdown-usermenu pull-right" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="javascript:;"> Profile</a>
                                <a class="dropdown-item" href="javascript:;">
                                    <span class="badge bg-red pull-right">50%</span>
                                    <span>Settings</span>
                                </a>
                                <a class="dropdown-item" href="javascript:;">Help</a>
                                <a class="dropdown-item" href="login.html"><i class="fa fa-sign-out pull-right"></i> Log
                                    Out</a>
                            </div>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="row" style="display: inline-block;">
                    <div class="top_tiles">
                        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 ">

                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Uptime Status <small>24 Hours progress</small></h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="col-md-12 col-sm-12 ">
                                    <div class="demo-container" style="height:280px">
                                        <div id="chart_plot_02" class="demo-placeholder"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 col-sm-12 ">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>Active Monitors</small></h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="card-box table-responsive">
                                        <table id="monitors-table"
                                               class="table table-striped table-bordered bulk_action"
                                               style="width:100%">
                                            <thead>
                                            <tr>
                                                <th>Enabled</th>
                                                <th>Name</th>
                                                <th>Type</th>
                                                <th>Status</th>
                                                <th>URL</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody id="monitors-table-body">

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <%--        <!-- footer content -->--%>
                <%--        <footer>--%>
                <%--          <div class="pull-right col-md-12">--%>
                <%--            <a href="Validation Tool">Validation Tool</a>--%>
                <%--          </div>--%>
                <%--          <div class="clearfix"></div>--%>
                <%--        </footer>--%>
                <%--        <!-- /footer content -->--%>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js" />"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.bundle.min.js" />"></script>
<!-- FastClick -->
<script src="<c:url value="/resources/vendors/fastclick/lib/fastclick.js" />"></script>
<!-- NProgress -->
<script src="<c:url value="/resources/vendors/nprogress/nprogress.js" />"></script>
<!-- Chart.js -->
<script src="<c:url value="/resources/vendors/Chart.js/dist/Chart.min.js" />"></script>
<!-- jQuery Sparklines -->
<script src="<c:url value="/resources/vendors/jquery-sparkline/dist/jquery.sparkline.min.js" />"></script>
<!-- Flot -->
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.js" />"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.pie.js" />"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.time.js" />"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.stack.js" />"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.resize.js" />"></script>
<!-- Flot plugins -->
<script src="<c:url value="/resources/vendors/flot.orderbars/js/jquery.flot.orderBars.js" />"></script>
<script src="<c:url value="/resources/vendors/flot-spline/js/jquery.flot.spline.min.js" />"></script>
<script src="<c:url value="/resources/vendors/flot.curvedlines/curvedLines.js" />"></script>
<!-- DateJS -->
<script src="<c:url value="/resources/vendors/DateJS/build/date.js" />"></script>
<!-- bootstrap-daterangepicker -->
<script src="<c:url value="/resources/vendors/moment/min/moment.min.js" />"></script>
<script src="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.js" />"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.js" />"></script>
<script src="<c:url value="/resources/js/common.js" />"></script>
<script src="<c:url value="/resources/js/dashboard.js" />"></script>
<script src="<c:url value="/resources/js/monitors.js" />"></script>
<script type="text/javascript">
    function getContextPath() {
        return "<c:out value="${pageContext.request.contextPath}" />";
    }
</script>
</body>
</html>