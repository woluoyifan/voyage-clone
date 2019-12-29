<#if cityOperation??>
    <#switch cityOperation>
        <#case "CREATE">
            <#include "create.ftl">
            <#break>
        <#case "TRADE">
            <#include "trade.ftl">
            <#break>
        <#case "BANK">
            <#include "bank.ftl">
            <#break>
        <#case "ADMIN">
            <#include "admin.ftl">
            <#break>
        <#case "UPDATE_NAME">
            <#include "name.ftl">
            <#break>
        <#case "UPDATE_DESCRIPTION">
            <#include "description.ftl">
            <#break>
        <#case "TAX_RATE">
            <#include "taxRate.ftl">
            <#break>
        <#case "ATTACK">
            <#include "attack.ftl">
            <#break>
    </#switch>
<#elseif cityOperationList?? && (cityOperationList?size>0)>
    <#include "operationList.ftl">
<#else>
    <form method="POST">
        <input type="hidden" name="cityOperation" value="CREATE">
        <input type="submit" value="建立城鎮" class="button">
    </form>
    <font color="#FF0000">需要${cityPrice} G</font>
</#if>
