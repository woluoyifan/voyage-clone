<#include "macro/page.ftl">
<@page>
    <center>
        <form method="POST">
            <table width="40" border="0" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
                <tr>
                    <td align="center">
                        <a href="register">创建账户</a><br><a href="description" target=_blank>玩法说明</a></td>
                </tr>
                <tr>
                    <td align="center">用户名<br>
                        <input type="text" name="username" class="text" size="20" value="">
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        密码<br>
                        <input type="password" name="password" class="text" size="20" value="">
                    </td>
                </tr>
                <tr style="color: red">
                    <td align="center">
                        ${errMsg!}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <input type="submit" value="OK" class="button">
                    </td>
                </tr>
            </table>
        </form>
    </center>
    <center>
            <#include "rank.ftl">
    </center>

</@page>
