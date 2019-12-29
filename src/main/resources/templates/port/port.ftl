<form method="POST">
    <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
        <tr>
            <td align="center">
                <input type="hidden" name="reload" value="664.400295660929">
                港口移动　　<input type="submit" value="OK" class="button">
            </td>
        </tr>
        <tr>
            <td align="left">
                <#list portList as port>
                    <input type="radio" name="portId" value="${port.target.id}" <#if port_index == 0>checked</#if>>
                    ${port.target.name}(${port.totalSecond}秒)食物${port.food}
                    <br>
                </#list>
            </td>
        </tr>
    </table>
</form>
<form method="POST">
    <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
        <tr>
            <td align="center">
                <input type="hidden" name="reload" value="664.400295660929">
                海域移动　　<input type="submit" value="OK" class="button">
            </td>
        </tr>
        <tr>
            <td align="left">
        <#list areaList as area>
            <input type="radio" name="areaId" value="${area.target.id}" <#if area_index == 0>checked</#if>>
            ${area.target.name}(${area.totalSecond}秒)食物${area.food}
            <br>
        </#list>
            </td>
        </tr>
    </table>
</form>
