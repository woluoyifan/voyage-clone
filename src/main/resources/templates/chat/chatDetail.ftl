<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title>聊天室</title>

    <style type="text/css">
    BODY {
        font-family: verdana, Arial;
        font-size: 12px;
        scrollbar-face-color: #eacd9f;
        scrollbar-highlight-color: #000000;
        scrollbar-shadow-color: #000000;
        scrollbar-3dlight-color: #eacd9f;
        scrollbar-arrow-color: #eacd9f;
        scrollbar-track-color: #000000;
        scrollbar-darkshadow-color: #eacd9f;
    }

    A {
        color:;
        text-decoration: none;
    }

    A:hover, A:active {
        color:;
    }

    .input {
        color:;
        font-size: 12px;
        background-color:;
        border-left: 1px solid;
        border-right: 1px solid;
        border-top: 1px solid;
        border-bottom: 1px solid;
    }

    textarea {
        color:;
        font-size: 12px;
        background-color:;
        border-left: 1px solid;
        border-right: 1px solid;
        border-top: 1px solid;
        border-bottom: 1px solid;
    }

    TABLE, TD {
        font-size: 12px;
    }

    .m {
        margin: 5px 20px 20px;
    }

    .m1 {
        margin: 5px 20px 20px;
    }

    .m2 {
        margin: 0px 0px 5px;
    }

    </style>
</head>
<body bgcolor="#eacd9f" text="#000000">
<div align="CENTER">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
            <td align="LEFT">
                <hr size="1" color="#000000">
                <#list chatRecordList as chatRecord>
                    <font color="#000000">${chatRecord.user.username}</font>：${chatRecord.content}
                <hr size="1" color="#000000">
                </#list>
            </td>
        </tr>
        </tbody>
    </table>
    <br>
</div>

</body>
</html>
