<center>
    <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
        <tbody>
        <tr>
            <td align="center">
                <input type="hidden" name="reload" value="40.9753544527156">
                城镇管理
            </td>
        </tr>
        <tr>
            <td align="left">
                <form method="POST">
                    <input type="hidden" name="reload" value="40.9753544527156">
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    提取资金：<br>
                    <div align="right">
                        <input type="number" name="withdrawalAmount" class="text" size="10">G<br>
                        城镇资金：${user.port.userPortCity.money} G<br>
                        <input type="submit" value="OK" class="button"></div>
                    <br>
                </form>
                <form method="POST">
                    <input type="hidden" name="reload" value="40.9753544527156">
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    修复城镇：<br>
                    <div align="right">
                        <input type="number" name="hp" class="text" size="10">HP<br>
                        (修复${repairCost} G)<br>HP：(${user.port.userPortCity.hp}/${maxHp})<br>
                        <input type="submit" value="OK" class="button">
                    </div>
                </form>
                <br>
                <form method="POST">
                    <input type="hidden" name="reload" value="40.9753544527156">
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    放入货物：<br>
                    <#list user.userGoodsList as userGoods>
                                <input type="radio" name="userGoodsId" value="${userGoods.id}" <#if userGoods_index == 0>checked</#if>>
                        ${userGoods.goods.name}
                                <br>
                    </#list>
                    <div align="right">
                        放入量：<input type="number" name="quantity" class="text" size="10"><br>
                        设定价格：<input type="number" name="price" class="text" size="10"><br>
                        <input type="submit" value="OK" class="button">
                    </div>
                </form>
                <br>
                <form method="POST">
                    <input type="hidden" name="reload" value="40.9753544527156">
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    放入船舰：<br>
                    <#list user.userShipList as userShip>
                                <input type="radio" name="userShipId" value="${userShip.id}" <#if userShip_index == 0>checked</#if>>
                                    ${userShip.ship.name}
                                <br>
                                    [容量：${userShip.ship.volume} 耐久：${userShip.ship.hp} 速度：${userShip.ship.speed}]
                                <br>
                    </#list>
                    <div align="right">
                        设定价格：<input type="number" name="price" class="text" size="10"><br>
                        <input type="submit" value="OK" class="button">
                    </div>
                </form>
                <br>
                <form method="POST">
                    <input type="hidden" name="reload" value="40.9753544527156">
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    放入财宝：<br>
                    <#list user.userItemList as userItem>
                                <input type="radio" name="userItemId" value="${userItemId.id}" <#if userItem_index == 0>checked</#if>>
                                    ${userItem.item.name}
                                <br>
                    </#list>
                    <div align="right">
                        设定价格：<input type="number" name="price" class="text" size="10"><br>
                        <input type="submit" value="OK" class="button">
                    </div>
                </form>
                <br>
                <form method="POST">
                    <input type="hidden" name="reload" value="40.9753544527156">
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    出售城镇：<br>
                    <div align="right">
                        设定价格：<input type="number" name="cityPrice" class="text" size="10"><br>
                        <input type="submit" value="OK" class="button">
                    </div>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</center>
