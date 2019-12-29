<#include "../macro/page.ftl">
<@page>
    <center>
        <table border="1" width="95%" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tr>
                <td>
                    <div align="center">
                        发送消息
                    </div>
                    <br>
                    要给谁发消息？
                    <hr class="text">
                    <form method="POST">
                    <#--<input type="radio" name="uid" value="admin">管理者<br>-->
                        <#list friendshipList as friendship>
                        <input type="radio" name="uid" value="${friendship.friend.id}">
                        <a href="/profile?id=${friendship.friend.id}" onclick="return opWin('/profile?id=${friendship.friend.id}','win6')" target="_blank">
                            ${friendship.friend.username}
                        </a>
                            <br>
                        </#list>
                        <hr class="text">
                        <textarea name="message" cols="30" rows="5" class="text"></textarea><br>
                        资金援助：<input type="text" name="money" size="10"> G<br>
                        <div align="right">
                            <input type="submit" value="OK" class="button">
                        </div>
                    </form>
                </td>
            </tr>
        </table>
    </center>
</@page>
