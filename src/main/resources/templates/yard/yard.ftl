造船厂<br>
<#if yardOperation??>
    <form method="POST">
        <input type="hidden" name="yardOperation" value="${yardOperation}">
            <#switch yardOperation>
                <#case "PURCHASE">
                    <#include "purchase.ftl">
                    <#break>
                <#case "SELL">
                    <#include "sell.ftl">
                    <#break>
                <#case "REPAIR">
                    <#include "repair.ftl">
                    <#break>
            </#switch>
    </form>
<#else>
    <#list yardOperationList as yardOperation>
        <form method="POST">
            <input type="hidden" name="yardOperation" value="${yardOperation}">
            <input type="submit" value="${yardOperation.description}" class="button">
        </form>
    </#list>
</#if>
