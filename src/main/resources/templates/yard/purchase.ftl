<table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
    <tbody>
    <tr>
        <td align="center">
            <input type="hidden" name="reload" value="712.538174194382">
            造船厂：购入　　<input type="submit" value="OK" class="button">
        </td>
    </tr>
    <tr>
        <td align="left">
            <#list shipList as ship>
                <input type="radio" name="shipId" value="${ship.id}" <#if ship_index == 0>checked</#if>>
                <img src="${ship.imgPath}" height="15">${ship.name}：${ship.purchasePrice} G
                <br>
                     <#switch ship.type>
                         <#case "SHIP">
                                        [容量：${ship.volume} 耐久：${ship.hp} 速度：${ship.speed}]
                             <#break>
                         <#case "ATTACK">
                                        战斗力＋${ship.volume}
                             <#break>
                         <#case "COMMAND">
                                        指挥力＋${ship.volume}
                             <#break>
                         <#case "NAVIGATION">
                                        航海力＋${ship.volume}
                             <#break>
                     </#switch>
                <br>
            </#list>
        </td>
    </tr>
    </tbody>
</table>
