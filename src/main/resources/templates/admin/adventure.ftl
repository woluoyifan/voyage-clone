<#include "../macro/page.ftl">
<@page>
    <center>
        <button onclick="window.location.href='/admin'">返回</button>
        <button onclick="window.location.href='/admin/adventure/create'">添加任务</button>
        <table>
            <thead>
            <tr>
                <td>名称</td>
                <td>说明</td>
                <td>价格</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <#list adventureList as adventure>
            <tr>
                <td>${adventure.name}</td>
                <td>${adventure.description}</td>
                <td>${adventure.price}</td>
                <td><button onclick="window.location.href='/admin/adventure/view?id=${adventure.id}'">查看</button></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </center>
</@page>
