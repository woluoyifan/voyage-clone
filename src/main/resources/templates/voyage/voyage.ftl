<#include "../macro/page.ftl">
<@page>
    <center>
        <table width="90%">
            <tr align="center" valign="top">
                <td>
                    <table width="200">
                        <tr>
            <#include "worldMap.ftl">
                        </tr>
                        <tr>
                            <td>
            <#include "../macro/fleet.ftl"/>
                        <@fleet userShipList = user.userShipList/>
            <#include "tactic.ftl"/>
                            </td>
                        </tr>
                    </table>
                <td width="40%">
            <#include "user.ftl">
                    <br>
            <#include "point.ftl">
            <#include "../chat/chat.ftl">
                </td>
                <td width="30%">
            <#if user.moving>
                <#include "moving.ftl">
            <#else>
                <#switch user.point>
                    <#case "PORT">
                        <#include "../port/port.ftl">
                        <#break>
                    <#case "YARD">
                        <#include "../yard/yard.ftl">
                        <#break>
                    <#case "TRADE">
                        <#include "../trade/trade.ftl">
                        <#break>
                    <#case "BAR">
                        <#include "../bar/bar.ftl">
                        <#break>
                    <#case "CITY">
                        <#include "../city/city.ftl">
                        <#break>
                    <#case "BATTLE">
                        <#include "../battle/battle.ftl">
                        <#break>
                </#switch>
            </#if>
                </td>
            </tr>
        </table>
    </center>
</@page>
