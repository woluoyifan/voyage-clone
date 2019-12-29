<center>
    <form method="POST">
        <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tbody>
            <tr>
                <td align="center">
                    <input type="hidden" name="reload" value="388.831529623957">
                    交易：购入　　<input type="submit" value="OK" class="button">
                </td>
            </tr>
            <tr>
                <td align="left">
                    <#list purchasableGoodsList as goods>
                        <input type="radio" name="goodsId" value="${goods.id}" <#if goods_index == 0>checked</#if>>
                    <#--floor:向下取整-->
                    <#--ceiling:向上取整-->
                        ${goods.name}：${goods.price} G (可购入${(user.money / goods.price)?floor}个)
                        <br>
                    </#list>
                    <br>
                    <div align="right">购入数量：<input type="number" name="quantity" size="10"></div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</center>
<center>
    <form method="POST">
        <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tbody>
            <tr>
                <td align="center">
                    <input type="hidden" name="reload" value="388.831529623957">
                    交易：卖出　　<input type="submit" value="OK" class="button">
                </td>
            </tr>
            <tr>
                <td align="left">
                        <#list saleableGoodsList as goods>
                            <input type="radio" name="userGoodsId" value="${goods.id}" <#if goods_index == 0>checked</#if>>
                            ${goods.name}：${goods.price} G
                            <br>
                        </#list>
                    <br>
                    <div align="right">卖出数量：<input type="number" name="quantity" size="10"></div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</center>
