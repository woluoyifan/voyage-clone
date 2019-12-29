<table>
    <tr>
        <td>
            <table border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>冒险王</td>
                    <td><#if (rank.adventureList?size>0)>${rank.adventureList[0].username}</#if><img src="img/crown.gif"></td>
                    <td align="right" nowrap><#if (rank.adventureList?size>0)>${rank.adventureList[0].adventure}</#if> pt</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>大冒险家</td>
                    <td><#if (rank.adventureList?size>1)>${rank.adventureList[1].username}</#if></td>
                    <td align="right" nowrap><#if (rank.adventureList?size>1)>${rank.adventureList[1].adventure}</#if> pt</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>冒险家</td>
                    <td><#if (rank.adventureList?size>2)>${rank.adventureList[2].username}</#if></td>
                    <td align="right" nowrap><#if (rank.adventureList?size>2)>${rank.adventureList[2].adventure}</#if> pt</td>
                </tr>
            </table>
        </td>
        <td>
            <table border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>海战王</td>
                    <td><#if (rank.battleList?size>0)>${rank.battleList[0].username}</#if><img src="img/crown.gif"></td>
                    <td align="right" nowrap><#if (rank.battleList?size>0)>${rank.battleList[0].battle}</#if> pt</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>大海战家</td>
                    <td><#if (rank.battleList?size>1)>${rank.battleList[1].username}</#if></td>
                    <td align="right" nowrap><#if (rank.battleList?size>1)>${rank.battleList[1].battle}</#if> pt</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>海战家</td>
                    <td><#if (rank.battleList?size>2)>${rank.battleList[2].username}</#if></td>
                    <td align="right" nowrap><#if (rank.battleList?size>2)>${rank.battleList[2].battle}</#if> pt</td>
                </tr>
            </table>
        </td>
        <td>
            <table border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>交易王</td>
                    <td><#if (rank.tradeList?size>0)>${rank.tradeList[0].username}</#if><img src="img/crown.gif"></td>
                    <td align="right" nowrap><#if (rank.tradeList?size>0)>${rank.tradeList[0].trade}</#if> pt</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>大商人</td>
                    <td><#if (rank.tradeList?size>1)>${rank.tradeList[1].username}</#if></td>
                    <td align="right" nowrap><#if (rank.tradeList?size>1)>${rank.tradeList[1].trade}</#if> pt</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>商人</td>
                    <td><#if (rank.tradeList?size>2)>${rank.tradeList[2].username}</#if></td>
                    <td align="right" nowrap><#if (rank.tradeList?size>2)>${rank.tradeList[2].trade}</#if> pt</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<br>
<table>
        <tr>
            <td>
                <table border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
                    <tr>
                        <td><img src="img/man.gif"></td>
                        <td>资金王</td>
                        <td><#if (rank.moneyList?size>0)>${rank.moneyList[0].username}</#if><img src="img/crown.gif"></td>
                        <td align="right" nowrap><#if (rank.moneyList?size>0)>${rank.moneyList[0].money}</#if> G</td>
                    </tr>
                    <tr>
                        <td><img src="img/man.gif"></td>
                        <td>大富豪</td>
                        <td><#if (rank.moneyList?size>1)>${rank.moneyList[1].username}</#if></td>
                        <td align="right" nowrap><#if (rank.moneyList?size>1)>${rank.moneyList[1].money}</#if> G</td>
                    </tr>
                    <tr>
                        <td><img src="img/man.gif"></td>
                        <td>富豪</td>
                        <td><#if (rank.moneyList?size>2)>${rank.moneyList[2].username}</#if></td>
                        <td align="right" nowrap><#if (rank.moneyList?size>2)>${rank.moneyList[2].money}</#if> G</td>
                    </tr>
                </table>
        </tr>
        <tr>
            <table border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
                <tr>
                    <td colspan="4" align="center">新人排行</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>新人冒险家</td>
                    <td><#if rank.adventureRookie??>${rank.adventureRookie.username}</#if></td>
                    <td align="right" nowrap><#if rank.adventureRookie??>${rank.adventureRookie.adventure}</#if> pt</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>新人海战家</td>
                    <td><#if rank.battleRookie??>${rank.battleRookie.username}</#if></td>
                    <td align="right" nowrap><#if rank.battleRookie??>${rank.battleRookie.battle}</#if> pt</td>
                </tr>
                <tr>
                    <td><img src="img/man.gif"></td>
                    <td>新人商人</td>
                    <td><#if rank.tradeRookie??>${rank.tradeRookie.username}</#if></td>
                    <td align="right" nowrap><#if rank.tradeRookie??>${rank.tradeRookie.trade}</#if> pt</td>
                </tr>
            </table>
        </tr>
    </table>
<br>
