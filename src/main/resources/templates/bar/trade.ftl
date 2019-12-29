<center>
    <form method="POST">
        <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
            <tbody>
            <tr>
                <td align="center">
                    <input type="hidden" name="reload" value="105.438044601218">
                    酒馆　　<input type="submit" value="OK" class="button">
                </td>
            </tr>
            <tr>
                <td align="left">
                    <input type="radio" name="barOperation" value="EMPLOY" checked>雇佣水手(${employPrice}G 每个)<br>
                    <input type="radio" name="barOperation" value="FIRE">解雇水手<br>
                    <input type="radio" name="barOperation" value="PURCHASE_FOOD">购买食物(${foodPrice}G 每个)<br>
                    <input type="radio" name="barOperation" value="THROW_FOOD">丢弃食物<br>
                    <div align="right">指定数量：<input type="number" name="quantity" size="10"></div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</center>
