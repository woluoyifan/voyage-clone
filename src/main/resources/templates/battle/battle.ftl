<center>
    <form method="POST">
        <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tbody>
            <tr>
                <td align="center">
                    <input type="hidden" name="reload" value="703.623290713043">
                    袭击　　<input type="submit" value="OK" class="button">
                </td>
            </tr>
            <tr>
                <td align="left">
                    <#if opponentList?? && (opponentList?size>0)>
                        <#list opponentList as opponent>
                        <input type="radio" name="opponentId" value="${opponent.id}" <#if opponent_index == 0>checked</#if>
                        <img src="/img/man.gif">
                        <a href="/profile?id=${opponent.id}" onclick="return opWin('/profile?id=${opponent.id}','win6')" target="_blank">
                            ${opponent.username}</a><br>
                        </#list>
                    <#else>
                        附近没有发现其他舰队
                    </#if>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</center>
