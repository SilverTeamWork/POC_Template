<!DOCTYPE html>
<% String contextPath = getServletConfig().getServletContext().getContextPath(); %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Silverpeas V6 POC</title>
  <meta name="description" content="The Famous Silverpeas Collaborative Platform"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <meta name="gwt:property" content="locale=fr">
  <link rel="<%=contextPath%>/apple-touch-icon" href="apple-touch-icon.png"/>
  <link rel="icon" href="<%=contextPath%>/favicon.ico" sizes="16x16" type="image/vnd.microsoft.icon">
  <link rel="stylesheet/less" type="text/css" href="<%=contextPath%>/css/knacss.less"/>
  <script src="<%=contextPath%>/js/less.min.js"></script>
  <script src="<%=contextPath%>/js/main.js"></script>
  <script src="//cdn.ckeditor.com/4.4.7/full/ckeditor.js"></script>
  <script type="text/javascript">
    var erraiBusRemoteCommunicationEnabled = false;
    var erraiPushStateEnabled = true;
    var erraiApplicationWebContext = "<%=contextPath%>";
  </script>
</head>
<body class="page-home w100 flexbox-v">
<iframe src="javascript:''" id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>
<script type="text/javascript" src="<%=contextPath%>/SilverpeasPoc/SilverpeasPoc.nocache.js"></script>
</body>
</html>
