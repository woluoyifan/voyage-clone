<#macro fleet userShipList>
    <table border="0" cellspacing="0" cellpadding="0" background="img/sea.gif" cols="5">
        <tr>
            <td height="40" width="40" align="center" valign="center"><br></td>
            <td height="40" width="40" align="center" valign="center"><br></td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[0]??><img src="${userShipList[0].ship.imgPath}" alt="${userShipList[0].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[1]??><img src="${userShipList[1].ship.imgPath}" alt="${userShipList[1].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center"><br></td>
        </tr>
        <tr>
            <td height="40" width="40" align="center" valign="center"><br></td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[2]??><img src="${userShipList[2].ship.imgPath}" alt="${userShipList[2].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[3]??><img src="${userShipList[3].ship.imgPath}" alt="${userShipList[3].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[4]??><img src="${userShipList[4].ship.imgPath}" alt="${userShipList[4].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[5]??><img src="${userShipList[5].ship.imgPath}" alt="${userShipList[5].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
        </tr>
        <tr>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[6]??><img src="${userShipList[6].ship.imgPath}" alt="${userShipList[6].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[7]??><img src="${userShipList[7].ship.imgPath}" alt="${userShipList[7].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[8]??><img src="${userShipList[8].ship.imgPath}" alt="${userShipList[8].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[9]??><img src="${userShipList[9].ship.imgPath}" alt="${userShipList[9].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[10]??><img src="${userShipList[10].ship.imgPath}" alt="${userShipList[10].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
        </tr>
        <tr>
            <td height="40" width="40" align="center" valign="center"><br></td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[11]??><img src="${userShipList[11].ship.imgPath}" alt="${userShipList[11].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[12]??><img src="${userShipList[12].ship.imgPath}" alt="${userShipList[12].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[13]??><img src="${userShipList[13].ship.imgPath}" alt="${userShipList[13].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[14]??><img src="${userShipList[14].ship.imgPath}" alt="${userShipList[14].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
        </tr>
        <tr>
            <td height="40" width="40" align="center" valign="center"><br></td>
            <td height="40" width="40" align="center" valign="center"><br></td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[15]??><img src="${userShipList[15].ship.imgPath}" alt="${userShipList[15].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center">
        <#if userShipList[16]??><img src="${userShipList[16].ship.imgPath}" alt="${userShipList[16].ship.name}"><#else><img src="img/ship/dam.gif"></#if>
            </td>
            <td height="40" width="40" align="center" valign="center"><br></td>
        </tr>
    </table>
</#macro>
