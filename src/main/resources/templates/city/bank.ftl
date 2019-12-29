<center>
    <table width="100%" border="1" bgcolor="#e9e2ce" bordercolor="#000000" cellspacing="0">
        <tbody>
        <tr>
            <td align="center">
                银行(手续费：取款金额×${user.port.userPortCity.taxRate}％)
            </td>
        </tr>
        <tr>
            <td align="center">
                存款金額：${depositAmount} G<br><br>
                <form method="POST">
                    存款　<input type="text" name="depositAmount" class="number" size="15">G
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    <input type="hidden" name="reload" value="885.326193486438">
                    <input type="submit" value="OK" class="button">
                </form>
                <form method="POST">
                    取款　<input type="text" name="withdrawalAmount" class="number" size="15">G
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    <input type="hidden" name="reload" value="885.326193486438">
                    <input type="submit" value="OK" class="button">
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</center>
