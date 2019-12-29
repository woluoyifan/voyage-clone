<center>
    <form method="POST">
        <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tbody>
            <tr>
                <td align="center">
                    <input type="hidden" name="reload" value="809.414614065648">
                    冒险情报　　<input type="submit" value="OK" class="button">
                </td>
            </tr>
            <tr>
                <td align="left">
                    <#list adventureList as adventure>
                        <input type="radio" name="adventureId" value="${adventure.id}" <#if adventure_index == 0>checked</#if>>${adventure.price} G
                        <br>
                    </#list>
                    <input type="hidden" name="barOperation" value="ADVENTURE">
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</center>
