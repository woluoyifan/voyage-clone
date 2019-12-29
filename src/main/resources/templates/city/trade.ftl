<center>
    <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
        <tbody>
        <tr>
            <td align="center">
                <input type="hidden" name="reload" value="265.464597688155">
                港口城镇「${user.port.userPortCity.name}」(${user.port.userPortCity.user.username}支配下)
            </td>
        </tr>
        <tr>
            <td align="left">
                <form method="POST">
                    购入货物：<br>
                    <input type="hidden" name="reload" value="265.464597688155">
                    <#list userPortCityGoodsList as userPortCityGoods>
                         <input type="radio" name="userPortCityGoodsId" value="${userPortCityGoods.id}" <#if userPortCityGoods_index == 0>checked</#if>>
                        ${userPortCityGoods.goods.name}：${userPortCityGoods.price} G
                        (庫存：${userPortCityGoods.quantity})<br>
                    </#list>
                    <div align="right">购入量：<input type="text" name="quantity" class="text" size="10"><br>
                        <input type="hidden" name="cityOperation" value="${cityOperation}">
                        <input type="submit" value="OK" class="button">
                    </div>
                </form>
                <br>
                <form method="POST">
                    购入船舰：<br>
                    <input type="hidden" name="reload" value="265.464597688155">
                    <#list userPortCityShipList as userPortCityShip>
                        <input type="radio" name="userPortCityShipId" value="${userPortCityShip.id}" <#if userPortCityShip_index == 0>checked</#if>>
                        <img src="${userPortCityShip.ship.imgPath}" height="15">
                        ${userPortCityShip.ship.name}：${userPortCityShip.price} G<br>[容量：${userPortCityShip.volume}
                        耐久：${userPortCityShip.hp} 速度：${userPortCityShip.speed}]<br>
                    </#list>
                    <br>
                    秘宝船
                    <br>
                    <#list secretShipList as ship>
                        <input type="radio" name="shipId" value="${ship.id}">
                        <img src="${ship.imgPath}" height="15">
                        ${ship.name}：${ship.price} G<br>[容量：${ship.volume}
                        耐久：${ship.hp} 速度：${ship.speed}]<br>
                    </#list>
                    <div align="right">
                        <input type="hidden" name="cityOperation" value="${cityOperation}">
                        <input type="submit" value="OK" class="button">
                    </div>
                </form>
                <br>
                <form method="POST">
                    购入财宝：<br>
                    <input type="hidden" name="reload" value="183.112032785179">
                    <#list userPortCityItemList as userPortCityItem>
                    <input type="radio" name="userPortCityItemId" value="${userPortCityItem.id}" <#if userPortCityItem_index == 0>checked</#if>>
                        ${userPortCityItem.name}：${userPortCityItem.price} G<br>
                    <div align="right">
                        <input type="hidden" name="cityOperation" value="${cityOperation}">
                        <input type="submit" value="OK" class="button">
                    </div>
                    </#list>
                </form>
                <br>
                <form method="POST">
                    购入城镇：<br>
                    <input type="hidden" name="reload" value="701.563218449341">
                    <input type="radio" name="cbuy" value="1">購入町：1000123 G<br>
                    <div align="right">
                        <input type="hidden" name="cityOperation" value="${cityOperation}">
                        <input type="submit" value="OK" class="button">
                    </div>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</center>
