<center>
    <form method="POST">
        <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tbody>
            <tr>
                <td align="center">
                    <input type="hidden" name="reload" value="7.89306110619137">
                    港口城镇「${user.port.userPortCity.name}」(${user.port.userPortCity.user.username}支配下)
                </td>
            </tr>
            <tr>
                <td align="center">
                    「${user.port.userPortCity.name}」HP：${user.port.userPortCity.hp}
                </td>
            </tr>
            <tr>
                <td>
                    <input type="radio" name="type" value="0" checked="">
                    武力攻击<br>[战斗决胜负]<br>
                    <input type="radio" name="type" value="1">
                    破坏工作<br>[花费 ${attackCost}G 可以造成 ${attackDamage} 点的破坏]<br>
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    <input type="submit" value="OK" class="button">
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</center>
