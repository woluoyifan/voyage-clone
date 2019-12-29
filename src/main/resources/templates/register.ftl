<#include "macro/page.ftl">
<@page>
<input type="button" value="返回" onClick="window.location.href='/'">
    <form method="POST">
        <table width="300">
            <tr>
                <td>
                    <div align="center">
                        创建账户
                    </div>
                    <div style="color: red">
                        ${errMsg!}
                    </div>
                    <br>
                    船长名字<br>
                    <input type="text" name="username" class="text" size="20" value="${username!}"><br/>
                    船长密码<br>
                    <input type="password" name="password" class="text" size="20"><br/>
                    电子邮件（可不填）<br>
                    <input type="text" name="email" class="text" size="35" value="${email!}"><br/>
                    <br>出发地点：
                    <select name="portId">
                        <#list birthplaceList as birthplace>
                            <option value="${birthplace.id}" <#if portId ?? && portId == birthplace.id>selected</#if>>${birthplace.name}</option>
                        </#list>
                    </select>
                    <br>船长能力（合计共100）：<br>
                    战斗力<input type="number" name="attack" class="text" size="5" value="${attack!}"><br>
                    指揮力<input type="number" name="command" class="text" size="5" value="${command!}"><br>
                    航海力<input type="number" name="navigation" class="text" size="5" value="${navigation!}">
                    <input type="submit" value="OK" class="button">
                </td>
            </tr>
        </table>
    </form>
</@page>
