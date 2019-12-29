<table width='100%' bgcolor="#e9e2ce" border="1" bordercolor="#000000" cellspacing="0">
    <tr>
        <td align="center">
            港口城镇「${user.port.userPortCity.name}」(${user.port.userPortCity.user.username}支配下)
        </td>
    </tr>
    <tr>
        <td>${user.port.userPortCity.description}</td>
    </tr>
    <tr>
        <td align="center"><br>
            <#list cityOperationList as cityOperation>
                <form method="POST">
                    <input type="hidden" name="cityOperation" value="${cityOperation}">
                    <input type="submit" value="${cityOperation.description}" class="button">
                </form>
            <br>
            </#list>
        </td>
    </tr>
</table>
