<#include "../macro/page.ftl">
<@page>
    <center>
        <div>
            查看冒险任务
            <button onclick="window.location.href='/admin/adventure'">返回</button>
            <#if adventure??>
                <form method="POST" action="/admin/adventure/del">
                    <input name="id" value="${adventure.id}" hidden>
                    <button>删除</button>
                </form>
            </#if>
        </div>
        <br/>
        <#if adventure??>
            <div>
                    <div>
                        <label>名称：${adventure.name}</label>
                        <label>价格：${adventure.price}</label>
                        <label>宝物：<#if adventure.item??>${adventure.item.name}</#if></label>
                    </div>
                    <div>
                        <label>说明：${adventure.description}</label>
                    </div>
                    <div id="areaContainer">
                        开始海域：
                <#list adventure.areaAdventureList as areaAdventure>
                    ${areaAdventure.area.name}&nbsp;&nbsp;
                </#list>
                    </div>
                    <div id="portContainer">
                        开始港口：
                <#list adventure.portAdventureList as portAdventure>
                    ${portAdventure.port.name}&nbsp;&nbsp;
                </#list>
                    </div>
                    <br/>
                    <div id="detailContainer">
                        任务明细：
                <#list adventure.adventureDetailList as adventureDetail>
                    <div>
                        <label>海域：<#if adventureDetail.area??>${adventureDetail.area.name}</#if></label>
                        <label>港口：<#if adventureDetail.port??>${adventureDetail.port.name}</#if></label>
                        <label>位置：${adventureDetail.point.description}</label>
                    </div>
                    <div>
                        <label>描述：${adventureDetail.description}</label>
                    </div>
                    <br/>
                </#list>
                    </div>
                </div>
        <#else>
            <div>任务不存在！</div>
        </#if>
    </center>

</@page>
