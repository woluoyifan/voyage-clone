<#setting number_format="#">
<#macro page>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Content-Style-Type" content="text/css">
    <title>${appName}</title>
    <SCRIPT LANGUAGE="JavaScript">
        <!--
        var w = window;

        function opWin(url, wname) {
            if ((w == window) || w.closed) {
                w = open(url, wname, "scrollbars=yes,resizable=yes,width=450,height=450");
            } else {
                w.location.replace(url);
                w.focus();
            }
            return (false);
        }

        //-->
    </SCRIPT>
    <STYLE TYPE="text/css">
        <!--
        body, tr, td, th {
            font-size: 10pt
        }

        A:link {
            text-decoration: none;
            color: #4169e1;
            font-weight: bold
        }

        A:visited {
            text-decoration: none;
            color: #4169e1;
            font-weight: bold
        }

        A:hover {
            text-decoration: none;
            color: #FF0000;
            font-weight: bold
        }

        .button {
            border: solid;
            border-width: 1pt 2pt;
            border-color: #633000;
            background-color: #fff3e6
        }

        -->
    </STYLE>
</head>
<body bgcolor=#eacd9f text=#000000>
<center><H2><font color=#4169e1>${appName}</font></H2><br></center>
    <#nested>
</body>
</html>
</#macro>
