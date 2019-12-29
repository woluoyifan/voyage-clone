<#include "../macro/page.ftl">
<@page>
<center>
    <table border="1" width="90%" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
        <tbody>
        <tr>
            <td>
                <table border="0" cellspacing="0" cellpadding="1" width="100%">
                    <tbody>
                    <tr>
                        <td>名字：${user.username}</td>
                    </tr>
                    <tr>
                        <td>船只数量：${user.userShipList?size}</td>
                        <td>资金：${user.money} G</td>
                    </tr>
                    </tbody>
                </table>
                <table border="0" cellspacing="0" cellpadding="1" width="100%">
                    <tbody>
                    <tr>
                        <td>
                            <br>
                            冒险名声：${user.adventure}<br>
                            海贼名声：${user.battle}<br>
                            商人名声：${user.trade}<br><br>
                            财宝：<br><br><br>
                            所在海域：${user.area.name}海域
                        </td>
                        <td align="right">
                            <#include "../macro/fleet.ftl">
                            <@fleet userShipList = user.userShipList></@fleet>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <br>
                <br>
                <br>
            </td>
        </tr>
        </tbody>
    </table>
</center>
</@page>
