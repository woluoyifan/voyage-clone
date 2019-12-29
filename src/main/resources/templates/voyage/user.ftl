<table border="1" width="100%" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
    <tr>
        <td colspan="2" align="center">状态</td>
    </tr>
    <tr>
        <td width="80" align="center">舰队</td>
        <td>${user.username}的舰队</td>
    </tr>
    <tr>
        <td width="80" align="center">货舱容量</td>
        <td>全部：${user.totalVolume}　使用：${user.totalGoodsQuantity}　剩余：${user.remainVolume}</td>
    </tr>
    <tr>
        <td width="80" align="center">货物详情</td>
        <td>
        <#if user.userGoodsList??>
            <#list user.userGoodsList as userGoods>${userGoods.goods.name},${userGoods.quantity}<br></#list>
        </#if>
        </td>
    </tr>
    <tr>
        <td width="80" align="center">食物</td>
        <td>${user.food}</td>
    </tr>
    <tr>
        <td width="80" align="center">资金</td>
        <td>${user.money} G</td>
    </tr>
    <tr>
        <td width="80" align="center">水手</td>
        <td>${user.sailor}人</td>
    </tr>
    <tr>
        <td width="80" align="center">Lv</td>
        <td>冒险Lv：${user.adventureLevel}　战斗Lv：${user.battleLevel}　商人Lv：${user.tradeLevel}</td>
    </tr>
    <tr>
        <td width="80" align="center">战斗力</td>
        <td>${user.attack}</td>
    </tr>
    <tr>
        <td width="80" align="center">回避率</td>
        <td>${user.avoid} ％ (指挥力：${user.command})</td>
    </tr>
    <tr>
        <td width="80" align="center">速度</td>
        <td>${user.vector} 节 (航海力：${user.navigation})</td>
    </tr>
    <tr>
        <td width="80" align="center">所在地</td>
        <td>
            <#if user.port??>
                ${user.port.name}
                (物价：${user.port.priceIndex?string("0.##%")})
            <#else>
                ${user.area.name}
            </#if>
        </td>
    </tr>
    <tr>
        <td width="80" align="center">财宝</td>
        <td><br></td>
    </tr>
    <tr>
        <td width="80" align="center">名声</td>
        <td>冒险：${user.adventure}<br>战斗：${user.battle}<br>商人：${user.trade}</td>
    </tr>
    <#if msg??>
        <tr>
            <td colspan="2" align="center">
                ${msg?replace('\n','<br/>') }
            </td>
        </tr>
    </#if>
    <tr>
        <#include "operation.ftl">
    </tr>
</table>
