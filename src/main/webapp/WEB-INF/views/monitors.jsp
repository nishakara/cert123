<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title> | Certmonitor | </title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/resources/vendors/nprogress/nprogress.css" />" rel="stylesheet">
    <!-- iCheck -->
    <link href="<c:url value="/resources/vendors/iCheck/skins/flat/green.css" />" rel="stylesheet">
    <!-- bootstrap-wysiwyg -->
    <link href="<c:url value="/resources/vendors/google-code-prettify/bin/prettify.min.css" />" rel="stylesheet">
    <!-- Select2 -->
    <link href="<c:url value="/resources/vendors/select2/dist/css/select2.min.css" />" rel="stylesheet">
    <!-- Switchery -->
    <link href="<c:url value="/resources/vendors/switchery/dist/switchery.min.css" />" rel="stylesheet">
    <!-- starrr -->
    <link href="<c:url value="/resources/vendors/starrr/dist/starrr.css" />" rel="stylesheet">
    <!-- bootstrap-daterangepicker -->
    <link href="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.css" />" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css" />" rel="stylesheet">


    <style>
      body {font-family: Arial, Helvetica, sans-serif;}
      * {box-sizing: border-box;}
      
      /* Button used to open the contact form - fixed at the bottom of the page */
      .open-button {
        background-color: rgb(43, 52, 128);
        color: white;
        padding: 16px 20px;
        border: none;
        cursor: pointer;
        opacity: 0.8;
        position: fixed;
        bottom: 23px;
        right: 28px;
        width: 280px;
      }
      
      /* The popup form - hidden by default */
      .form-popup {
        display: none;
        position: fixed;
        bottom: 0;
        right: 15px;
        border: 3px solid #f1f1f1;
        z-index: 9;
      }
      
      /* Add styles to the form container */
      .form-container {
        max-width: 300px;
        padding: 10px;
        background-color: white;
      }
      
      /* Full-width input fields */
      .form-container input[type=text], .form-container input[type=password] {
        width: 100%;
        padding: 15px;
        margin: 5px 0 22px 0;
        border: none;
        background: #f1f1f1;
      }
      
      /* When the inputs get focus, do something */
      .form-container input[type=text]:focus, .form-container input[type=password]:focus {
        background-color: #ddd;
        outline: none;
      }
      
      /* Set a style for the submit/login button */
      .form-container .btn {
        background-color: #0d0b6d;
        color: white;
        padding: 8px 8px;
        border: none;
        cursor: pointer;
        width: 100%;
        margin-bottom:10px;
        opacity: 0.8;
      }
      
      /* Add a red background color to the cancel button */
      .form-container .cancel {
        background-color: rgb(53, 114, 228);
      }
      
      /* Add some hover effects to buttons */
      .form-container .btn:hover, .open-button:hover {
        opacity: 1;
      }
      </style>
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <!-- <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>Gentelella Alela!</span></a> -->
              <a href="<c:url value="/" />" class="site_title"><i class="fa fa-terminal"></i>Cert Monitor <span></span></a>

            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile clearfix">
<%--              <div class="profile_pic">--%>
<%--                <img src="<c:url value="/resources/images/img.jpg" />" alt="..." class="img-circle profile_img">--%>
<%--              </div>--%>
              <div class="profile_info">
<%--                <span>Welcome,</span>--%>
<%--                <h2>User</h2>--%>
              </div>
            </div>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
<%--                <h3>General</h3>--%>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-home"></i> Status <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="<c:url value="/" />">Status Dashboard</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-edit"></i> Monitors <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                    </ul>
                    <ul class="nav child_menu">
                        <li><a href="<c:url value="/monitors" />">Add New Monitor</a></li>
                      </ul>
                  </li>
                </ul>
              </div>
              <div class="menu_section">
                <!-- <h3>Live On</h3> -->
                <ul class="nav side-menu">

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
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="<c:url value="/login" />" id="LogoutButton">
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
<%--                  <li class="nav-item dropdown open" style="padding-left: 15px;">--%>
<%--                    <a href="javascript:;" class="user-profile dropdown-toggle" aria-haspopup="true" id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">--%>
<%--                      <img src="<c:url value="/resources/images/img.jpg" />" alt="">User--%>
<%--                    </a>--%>
<%--                    <div class="dropdown-menu dropdown-usermenu pull-right" aria-labelledby="navbarDropdown">--%>
<%--                      <a class="dropdown-item"  href="javascript:;"> Profile</a>--%>
<%--                        <a class="dropdown-item"  href="javascript:;">--%>
<%--                          <span class="badge bg-red pull-right">50%</span>--%>
<%--                          <span>Settings</span>--%>
<%--                        </a>--%>
<%--                    <a class="dropdown-item"  href="javascript:;">Help</a>--%>
<%--                      <a class="dropdown-item"  href="<c:url value="/login" />"><i class="fa fa-sign-out pull-right"></i> Log Out</a>--%>
<%--                    </div>--%>
<%--                  </li>--%>
  
                  <li role="presentation" class="nav-item dropdown open">
<%--                    <a href="javascript:;" class="dropdown-toggle info-number" id="navbarDropdown1" data-toggle="dropdown" aria-expanded="false">--%>
<%--                      <i class="fa fa-envelope-o"></i>--%>
<%--                      <span class="badge bg-green">6</span>--%>
<%--                    </a>--%>
                    <ul class="dropdown-menu list-unstyled msg_list" role="menu" aria-labelledby="navbarDropdown1">
<%--                      <li class="nav-item">--%>
<%--                        <a class="dropdown-item">--%>
<%--                          <span class="image"><img src="<c:url value="/resources/images/img.jpg" />" alt="Profile Image" /></span>--%>
<%--                          <span>--%>
<%--                            <span>John Smith</span>--%>
<%--                            <span class="time">3 mins ago</span>--%>
<%--                          </span>--%>
<%--                          <span class="message">--%>
<%--                            Film festivals used to be do-or-die moments for movie makers. They were where...--%>
<%--                          </span>--%>
<%--                        </a>--%>
<%--                      </li>--%>
<%--                      <li class="nav-item">--%>
<%--                        <a class="dropdown-item">--%>
<%--                          <span class="image"><img src="<c:url value="/resources/images/img.jpg" />" alt="Profile Image" /></span>--%>
<%--                          <span>--%>
<%--                            <span>John Smith</span>--%>
<%--                            <span class="time">3 mins ago</span>--%>
<%--                          </span>--%>
<%--                          <span class="message">--%>
<%--                            Film festivals used to be do-or-die moments for movie makers. They were where...--%>
<%--                          </span>--%>
<%--                        </a>--%>
<%--                      </li>--%>
<%--                      <li class="nav-item">--%>
<%--                        <a class="dropdown-item">--%>
<%--                          <span class="image"><img src="<c:url value="/resources/images/img.jpg" />" alt="Profile Image" /></span>--%>
<%--                          <span>--%>
<%--                            <span>John Smith</span>--%>
<%--                            <span class="time">3 mins ago</span>--%>
<%--                          </span>--%>
<%--                          <span class="message">--%>
<%--                            Film festivals used to be do-or-die moments for movie makers. They were where...--%>
<%--                          </span>--%>
<%--                        </a>--%>
<%--                      </li>--%>
<%--                      <li class="nav-item">--%>
<%--                        <a class="dropdown-item">--%>
<%--                          <span class="image"><img src="<c:url value="/resources/images/img.jpg" />" alt="Profile Image" /></span>--%>
<%--                          <span>--%>
<%--                            <span>John Smith</span>--%>
<%--                            <span class="time">3 mins ago</span>--%>
<%--                          </span>--%>
<%--                          <span class="message">--%>
<%--                            Film festivals used to be do-or-die moments for movie makers. They were where...--%>
<%--                          </span>--%>
<%--                        </a>--%>
<%--                      </li>--%>
<%--                      <li class="nav-item">--%>
<%--                        <div class="text-center">--%>
<%--                          <a class="dropdown-item">--%>
<%--                            <strong>See All Alerts</strong>--%>
<%--                            <i class="fa fa-angle-right"></i>--%>
<%--                          </a>--%>
<%--                        </div>--%>
<%--                      </li>--%>
                    </ul>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Monitors</h3>
              </div>
              <div class="col-md-12 col-sm-12 ">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Active Monitors</small></h2>
                    <ul class="nav navbar-right panel_toolbox">

                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <div class="row">
                      <div class="col-sm-12">
                        <div class="card-box table-responsive">

                          <table id="monitors-table" class="table table-striped table-bordered bulk_action" style="width:100%">
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



            </div>
            <div class="clearfix"></div>
                <div class="x_panel">
                  <div class="col-md-6 ">
                    
                    <button class="open-button" onclick="openForm()">Create New Monitor</button>
                    
                    <div class="form-popup" id="myForm">
                      <form action="/action_page.php" class="form-container">
                        <!-- <h4>Create Monitor</h4> -->
                    
                        <label for="monitorName"><b>Monitor Name</b></label>
                        <input type="text" placeholder="Monitor Name" name="monitorName" required>
                    
                        <label for="hostName"><b>Host Name</b></label>
                        <input type="text" placeholder="Host Name" name="hostName" required>
                    
                        <button type="submit" class="btn">Create</button>
                        <button type="button" class="btn cancel" onclick="closeForm()">Close</button>
                      </form>
                    </div>
                    <div class="x_panel">
                      <div class="x_title">
                        <h2>Create Monitor</h2>
                        <ul class="nav navbar-right panel_toolbox">
                        </ul>
                        <div class="clearfix"></div>
                      </div>
                      <div class="x_content">
                        <br />
                        <form name="createMonitorForm" id="createMonitorForm" class="form-horizontal form-label-left"  method="post">
    
                          <div class="form-group row ">
                            <label class="control-label col-md-3 col-sm-3 ">Monitor Name</label>
                            <div class="col-md-9 col-sm-9 ">
                              <input type="text" class="form-control" required="required" placeholder="" name="monitorName" />
                            </div>
                          </div>
                          <div class="form-group row ">
                            <label class="control-label col-md-3 col-sm-3 ">Hostname</label>
                            <div class="col-md-9 col-sm-9 ">
                              <input type="text" class="form-control" required="required" placeholder="" name="hostName"
                                     pattern="https?://.+" title="Include https://"/>
                            </div>
                          </div>
                          <div class="form-group row ">
                            <label class="control-label col-md-3 col-sm-3 ">Alert Days Prior</label>
                            <div class="col-md-9 col-sm-9 ">
                              <input type="text" class="form-control" required="required" placeholder="" name="alertDays"
                                     pattern="[0-9]{1,4}" title="Should be max 4 digit number">
                            </div>
                          </div>
                            <div class="form-group row ">
                                <label class="control-label col-md-3 col-sm-3 ">Group Email</label>
                                <div class="col-md-9 col-sm-9 ">
                                    <input type="email" class="form-control" required="required" placeholder="" name="groupEmail"
                                           pattern="[A-Za-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" title="Should be a valid email address">
                                </div>
                            </div>
                          <div class="form-group row ">
                            <label class="control-label col-md-3 col-sm-3 ">Port</label>
                            <div class="col-md-9 col-sm-9 ">
                              <input type="text" class="form-control"  placeholder="" name="port" pattern="[0-9]{0,}" title="Port should be a number">
                            </div>
                          </div>
                          <div class="ln_solid"></div>
                          <div class="form-group">
                            <div class="col-md-9 col-sm-9  offset-md-3">
                              <button type="reset" class="btn btn-primary">Reset</button>
                              <button type="submit" class="btn btn-success" id="CreateMonitorSubmit">Submit</button>
                            </div>
                          </div>
    
                        </form>
                      </div>
                    </div>
                  </div>


                  <div class="x_content">

                  </div>
                </div>


              </div>
            </div>

             </div>
            </div>
          </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <!-- <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div> -->
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js" />" ></script>
    <!-- Bootstrap -->
   <script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.bundle.min.js" />" ></script>
    <!-- FastClick -->
    <script src="<c:url value="/resources/vendors/fastclick/lib/fastclick.js" />" ></script>
    <!-- NProgress -->
    <script src="<c:url value="/resources/vendors/nprogress/nprogress.js" />" ></script>
    <!-- bootstrap-progressbar -->
    <script src="<c:url value="/resources/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js" />" ></script>
    <!-- iCheck -->
    <script src="<c:url value="/resources/vendors/iCheck/icheck.min.js" />" ></script>
    <!-- bootstrap-daterangepicker -->
    <script src="<c:url value="/resources/vendors/moment/min/moment.min.js" />" ></script>
    <script src="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.js" />" ></script>
    <!-- bootstrap-wysiwyg -->
    <script src="<c:url value="/resources/vendors/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js" />" ></script>
    <script src="<c:url value="/resources/vendors/jquery.hotkeys/jquery.hotkeys.js" />" ></script>
    <script src="<c:url value="/resources/vendors/google-code-prettify/src/prettify.js" />" ></script>
    <!-- jQuery Tags Input -->
    <script src="<c:url value="/resources/vendors/jquery.tagsinput/src/jquery.tagsinput.js" />" ></script>
    <!-- Switchery -->
    <script src="<c:url value="/resources/vendors/switchery/dist/switchery.min.js" />" ></script>
    <!-- Select2 -->
    <script src="<c:url value="/resources/vendors/select2/dist/js/select2.full.min.js" />" ></script>
    <!-- Parsley -->
    <script src="<c:url value="/resources/vendors/parsleyjs/dist/parsley.min.js" />" ></script>
    <!-- Autosize -->
    <script src="<c:url value="/resources/vendors/autosize/dist/autosize.min.js" />" ></script>
    <!-- jQuery autocomplete -->
    <script src="<c:url value="/resources/vendors/devbridge-autocomplete/dist/jquery.autocomplete.min.js" />" ></script>
    <!-- starrr -->
    <script src="<c:url value="/resources/vendors/starrr/dist/starrr.js" />" ></script>
    <!-- Custom Theme Scripts -->
    <script src="<c:url value="/resources/build/js/custom.js" />" ></script>

    <script src="<c:url value="/resources/js/monitors.js" />" ></script>
    <script src="<c:url value="/resources/js/common.js" />" ></script>

    <script type="text/javascript">
        function getContextPath() {
            return "<c:out value="${pageContext.request.contextPath}" />";
        }
    </script>
<script>
  function openForm() {
  document.getElementById("myForm").style.display = "block";
}

function closeForm() {
  document.getElementById("myForm").style.display = "none";
}
  </script>

    


  </body>
</html>
