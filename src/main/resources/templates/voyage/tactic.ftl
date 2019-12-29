<center>
    <form method="POST">
        <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tr>
                <td align="center">
                    战术
                    <input type="submit" value="OK" class="button">
                    <input type="hidden" name="reload" value="664.400295660929">
                </td>
            </tr>
            <tr>
                <td align="center" valign="center">
                    <#list tacticList as tactic>
                        <#if (tactic_index > 2)><br></#if>
                        <input type="radio" name="tactic" value="${tactic}" <#if tactic_index == 0>checked</#if>>${tactic.description}
                    </#list>
                </td>
            </tr>
        </table>
    </form>
</center>
