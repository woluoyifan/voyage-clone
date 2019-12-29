<#include "../macro/page.ftl">
<@page>
    <center>
        <div>
            添加冒险任务
            <button onclick="window.location.href='/admin/adventure'">返回</button>
        </div>
        <br/>
        <div>
            <form method="POST">
                <div>
                    <label>名称<input name="name"></label>
                    <label>价格<input type="number" name="price"></label>
                    <label>宝物
                        <select name="itemId">
                            <option value="">无</option>
                    <#list itemList as item>
                        <option value="${item.id}">${item.name}</option>
                    </#list>
                        </select>
                    </label>
                </div>
                <div>
                    <label>说明<textarea name="description"></textarea></label>
                </div>
                <div id="areaContainer">
                    开始海域<button type="button" onclick="addArea()">添加</button>
                </div>
                <div id="portContainer">
                    开始港口<button type="button" onclick="addPort()">添加</button>
                </div>
                <br/>
                <div id="detailContainer">
                    任务明细<button type="button" onclick="addDetail()">添加</button>
                </div>
                <div>
                    <button type="submit">提交</button>
                </div>
            </form>
        </div>

        <label id="areaTemplate" hidden>
            海域
            <select name="areaIdList">
                <option value="">无</option>
                    <#list areaList as area>
                        <option value="${area.id}">${area.name}</option>
                    </#list>
            </select>
            <button type="button" onclick="removeArea(this)">删除</button>
        </label>

        <label id="portTemplate" hidden>
            港口
            <select name="portIdList">
                <option value="">无</option>
                    <#list portList as port>
                        <option value="${port.id}">${port.name}</option>
                    </#list>
            </select>
            <button type="button" onclick="removePort(this)">删除</button>
        </label>

        <div id="detailTemplate" hidden>
            <div>
                <label>
                    海域
                    <select name="detailAreaIdList">
                        <option value="0">无</option>
                    <#list areaList as area>
                        <option value="${area.id}">${area.name}</option>
                    </#list>
                    </select>
                </label>
                <label>
                    港口
                    <select name="detailPortIdList">
                        <option value="0">无</option>
                    <#list portList as port>
                        <option value="${port.id}">${port.name}</option>
                    </#list>
                    </select>
                </label>
                <label>
                    位置
                    <select name="detailPointList">
                    <#list pointList as point>
                        <option value="${point}">${point.description}</option>
                    </#list>
                    </select>
                </label>
                <button type="button" onclick="removeDetail(this)">删除</button>
            </div>
            <div>
                <label>描述<textarea name="detailDescriptionList"></textarea></label>
            </div>
            <br/>
        </div>
    </center>

    <script>
        function addArea(){
            let node = document.querySelector('#areaTemplate').cloneNode(true);
            node.hidden=false;
            document.querySelector('#areaContainer')
                    .appendChild(node);
        }

        function addPort(){
            let node = document.querySelector('#portTemplate').cloneNode(true);
            node.hidden=false;
            document.querySelector('#portContainer')
                    .appendChild(node);
        }

        function addDetail(){
            let node = document.querySelector('#detailTemplate').cloneNode(true);
            node.hidden=false;
            document.querySelector('#detailContainer')
                    .appendChild(node);
        }

        function removeArea(target){
            let node = target.parentNode;
            node.parentNode.removeChild(node);
        }

        function removePort(target){
            let node = target.parentNode;
            node.parentNode.removeChild(node);
        }

        function removeDetail(target){
            let node = target.parentNode.parentNode;
            node.parentNode.removeChild(node);
        }
    </script>
</@page>
