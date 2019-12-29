<#if (userShipList?size==0)>
    你已经没有船了
<#else>
    <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
        <tbody>
        <tr>
            <td align="center">
                <input type="hidden" name="reload" value="954.528696443052">
                造船所：售出　　<input type="submit" value="OK" class="button">
            </td>
        </tr>
        <tr>
            <td align="left">
                <#list userShipList as userShip>
                    <input type="radio" name="userShipId" value="${userShip.id}" <#if userShip_index == 0>checked</#if>>
                    ${userShip.ship.name}：${userShip.ship.sellPrice} G
                    <br>
                    [容量：${userShip.ship.volume} 耐久：${userShip.ship.hp} 速度：${userShip.ship.speed}
                    <br>
                </#list>
            </td>
        </tr>
        </tbody>
    </table>
</#if>
