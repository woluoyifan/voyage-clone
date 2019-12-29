<center>
    <table width="100%">
        <tbody>
        <tr>
            <td align="center">
                <form method="POST">★到达<input type="hidden" name="mode" value="play">
                    <input type="submit" value="OK" class="button">
                </form>
            </td>
        </tr>
        <tr>
            <td background="/img/sea.gif">
                <marquee behavior="slide" loop="1" height="${moveWidth}" direction="up"
                         scrollamount="<#if user.movingSecond == 0>${moveWidth}<#else>1</#if>"
                         scrolldelay="${user.movingSecond *1000 /moveWidth}" truespeed>
                    <center><img src="/img/move.gif"></center>
                </marquee>
            </td>
        </tr>
        </tbody>
    </table>
    剩余时间<input id="moveSecond" size="10">
    <script language="JavaScript">
        var xx = ${user.movingSecond};

        function down() {
            if (xx < 0) xx = 0;
            sec = xx % 60;
            min = ((xx - sec) / 60) % 60;
            hor = (xx - min * 60 - sec) / 3600;
            if (hor < 10) hor = "0" + hor;
            if (min < 10) min = "0" + min;
            if (sec < 10) sec = "0" + sec;
            xx--;
            document.getElementById('moveSecond').value = hor + ":" + min + ":" + sec;
            setTimeout('down()', 1000);
        }

        down();
    </script>
</center>
