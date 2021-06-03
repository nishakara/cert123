<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title> | Certmonitor | </title>

    <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/nprogress/nprogress.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/animate.css/animate.min.css" />" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css" />" rel="stylesheet">
  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form name="SubmitCredentialsForm" method="post" id="SubmitCredentialsForm">
              <h1>Login Form</h1>
              <div>
                <input type="text" class="form-control" placeholder="Username" required="" name="userName" />
              </div>
              <div>
                <input type="password" class="form-control" placeholder="Password" required="" name="password" />
              </div>
              <div>
<%--                <a class="btn btn-default submit" href="<c:url value="/SubmitCredentials" />">Log in</a>--%>
                <button type="submit" class="btn btn-success">Log in</button>
                <a class="reset_pass" href="#">Lost your password?</a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">New to site?
                  <a href="#signup" class="to_register"> Create Account </a>
                </p>

                <div class="clearfix"></div>
                <br />
                <div>
                  <h1> Certificate Validation Tool</h1>
                </div>
                
              </div>
            </form>
          </section>
        </div>

        <div id="register" class="animate form registration_form">
          <section class="login_content">
            <form method="post" id="CreateAccountForm">
              <h1>Create Account</h1>

              <div>
                <input type="email" class="form-control" placeholder="Email" required="required" name="email"
                       pattern="[A-Za-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" title="Should be a valid email address"/>
              </div>
              <div>
                <input type="password" class="form-control" placeholder="Password" required="required" name="pswd" />
              </div>
              <div>
                <input type="text" class="form-control" placeholder="GroupEmail" required="required" name="groupEmail"
                       pattern="[A-Za-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" title="Should be a valid email address"/>
              </div>
              <div>
                <button type="submit" class="btn btn-success">Create</button>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">Already a member ?
                  <a href="#signin" class="to_register"> Log in </a>
                </p>

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1> Certmonitor </h1>
                </div>
              </div>
            </form>
          </section>
        </div>
      </div>
    </div>

    <script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js" />" ></script>
    <script src="<c:url value="/resources/js/login.js" />" ></script>
    <script type="text/javascript">
      function getContextPath() {
        return "<c:out value="${pageContext.request.contextPath}" />";
      }
    </script>
  </body>
</html>
