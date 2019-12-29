<#if (userShipList?size==0)>
    没有需要修理的船
<#else>
    <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
        <tbody>
        <tr>
            <td align="center">
                <input type="hidden" name="reload" value="499.122979193093">
                造船所：修理　　<input type="submit" value="OK" class="button">
            </td>
        </tr>
        <tr>
            <td align="left">
                <#list userShipList as userShip>
                    <input type="radio" name="userShipId" value="${userShip.id}" <#if userShip_index == 0>checked</#if>>
                    ${userShip.ship.name}：${userShip.repairPrice} G
                    <br>
                    [容量：${userShip.volume} 耐久：${userShip.hp} 速度：${userShip.speed}]
                    <br>
                    修理后　[容量：${userShip.repairedVolume} 耐久：${userShip.repairedHp}]
                    <br>
                </#list>
            </td>
        </tr>
        </tbody>
    </table>
</#if>
