<script>
    function check(key_msg) {
        document.form.content.value = key_msg
    }
</script>
<br>
<table width="100%" align=center bgcolor="#e9e2ce" border="1" bordercolor=#000000 cellspacing=0>
    <tr>
        <td align="center">聊天室</td>
    </tr>
    <tr>
        <td>
            <form name="form" method="POST" action="/chat" target="chat" onsubmit="check(keymsg.value);keymsg.value=''">
                <iframe src="/chat" width="100%" height="150" name="chat" frameborder="0"></iframe>
                <BR>
                <input name="keymsg" TYPE="text" SIZE="33" CLASS="input">
                <input type="submit" value="送出" class=button>
                <input type="button" value="更新" onclick="content.value='';submit()" class=button>
                <input name="content" TYPE="hidden" SIZE="33" CLASS="input">
            </form>
        </td>
    </tr>
</table>
