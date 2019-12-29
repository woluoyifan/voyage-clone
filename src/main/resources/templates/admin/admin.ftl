<#include "../macro/page.ftl">
<@page>
    <center>
        <table>
            <tbody>
            <tr>
                <td>
                    <button onclick="window.location.href='/admin/adventure'">管理玩家账户</button>
                </td>
            </tr>
            <tr>
                <td>
                    <button onclick="window.location.href='/admin/adventure'">管理员专用创建账户</button>

                </td>
            </tr>
            <tr>
                <td>
                    <button onclick="window.location.href='/admin/adventure'">发送消息</button>

                </td>
            </tr>
            <tr>
                <td>
                    <button onclick="window.location.href='/admin/adventure'">任务管理</button>
                </td>
            </tr>
            <tr>
                <td>
                    <button onclick="window.location.href='/admin/logout'">登出</button>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="?mode=ahistory&amp;ps=0123" onclick="return opWin('?mode=ahistory&amp;ps=0123','win6')" target="_blank">訊息接收</a><br>
                    <input type="submit" value="OK">
                </td>
            </tr>
            </tbody>
        </table>
    </center>
</@page>
