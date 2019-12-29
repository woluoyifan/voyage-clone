<#if barOperation?? && barOperation == "ADVENTURE">
    <#include "adventureInfo.ftl">
<#else>
    <#include "trade.ftl">
    <#include "adventure.ftl">
    <#include "guest.ftl">
</#if>
