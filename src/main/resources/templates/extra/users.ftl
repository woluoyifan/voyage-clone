<#include "../macro/page.ftl">
<@page>
    <center>
        <table border="1" width="95%" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tbody>
            <tr>
                <td>
                    <#list sortList as _sort>
                        <a href="/users?sort=${_sort}">[${_sort.description}]</a>
                    </#list>
                    <br>
                    <#if userPage.hasPrevious()>
                    <b>
                        <a href="/users?page=${userPage.previousPageable().pageNumber+1}&sort=${sort!}">[上一页]</a>
                    </b>
                    </#if>
                    <#list 1..userPage.totalPages as i>
                    <b>
                        <#if (userPage.number+1) == i>
                            [${i}]
                        <#else>
                            <a href="/users?page=${i}&sort=${sort!}">[${i}]</a>
                        </#if>
                    </b>
                    </#list>
                    <#if userPage.hasNext()>
                    <b>
                        <a href="/users?page=${userPage.nextPageable().pageNumber+1}&sort=${sort!}">[下一页]</a>
                    </b>
                    </#if>
                    <br><br>
                    <div align="center">
                        舰队一览
                    </div>
                    <br>
                    <table align="center" width="100%">
                        <tbody>
                        <#list userPage.content as user>
                        <tr>
                            <td><a href="/profile?id=">${user.username}</a></td>
                            <td><img src="/img/man.gif"></td>
                            <td>${user.area.name}海域</td>
                            <td align="right" nowrap="">
                                    <#if sort??>
                                        <#switch sort>
                                            <#case "NAME">
                                                ${user.money} G
                                                <#break>
                                            <#case "MONEY">
                                                ${user.money} G
                                                <#break>
                                            <#case "ADVENTURE">
                                                ${user.adventure} 点
                                                <#break>
                                            <#case "BATTLE">
                                                ${user.battle} 点
                                                <#break>
                                            <#case "TRADE">
                                                ${user.trade} 点
                                                <#break>
                                            <#case "SHIP">
                                                ${user.userShipList?size} 条
                                                <#break>
                                            <#case "AREA">
                                                ${user.money} G
                                                <#break>
                                        </#switch>
                                    <#else>
                                        ${user.money} G
                                    </#if>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </center>
</@page>
