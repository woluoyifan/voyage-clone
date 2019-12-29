<#if pointList?? && (pointList?size>0)>
    <center>
        <form method="POST">
            <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
                <tr>
                    <td align="center">
                        港内移动　　<input type="submit" value="OK" class="button">
                    </td>
                </tr>
                <tr>
                    <td align="center">
            <#list pointList as point>
                <input type="radio" name="point" value="${point}" <#if user.point==point>checked</#if>>${point.description}
            </#list>
                        <input type="hidden" name="mode" value="ch_point">
                    </td>
                </tr>
            </table>
        </form>
    </center>
</#if>

