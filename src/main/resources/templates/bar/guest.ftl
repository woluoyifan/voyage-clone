<table width="100%" bgcolor="#e9e2ce" border="1" bordercolor="#000000" cellspacing="0">
    <tbody>
    <tr>
        <td align="center">
            来店者
        </td>
    </tr>
    <tr>
        <td align="center">
                <#if userList?? && (userList?size>0)>
                    <#list userList as user>
                        <img src="/img/man.gif">
                        <a href="/profile?id=${user.id}" onclick="return opWin('/profile?id=${user.id}','win6')" target="_blank">
                            ${user.username}</a><br>
                    </#list>
                <#else>
                    店里一个客人也没有...
                </#if>
        </td>
    </tr>
    </tbody>
</table>
