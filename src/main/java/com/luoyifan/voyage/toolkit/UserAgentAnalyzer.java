package com.luoyifan.voyage.toolkit;

import com.luoyifan.voyage.constants.BrowserEnum;
import com.luoyifan.voyage.constants.PlatformEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author EvanLuo
 */
@Data
public class UserAgentAnalyzer {
    private static final Pattern macOsXVersionPattern = Pattern.compile("OS ([\\d_]+?) like Mac OS X");

    private static final String CHROME_FEATURE = "Chrome";
    private static final String FIREFOX_FEATURE = "FireFox";
    private static final String IE_FEATURE = "MSIE";
    private static final String SAFARI_FEATURE = "Version";
    private static final String QQ_FATURE = "MQQBrowser";
    private static final String UC_FATURE = "UC";
    private static final String QH360_FATURE = "360SE";
    private static final String QH360_FATURE2 = "360EE";
    private static final String MAXTHON_FATURE = "Maxthon";

    private static final Pattern chromeVersionPattern = Pattern.compile("Chrome/([A-Za-z\\d.]+)");
    private static final Pattern fireFoxVersionPattern = Pattern.compile("FireFox/([A-Za-z\\d.]+)");
    private static final Pattern ieVersionPattern = Pattern.compile("MSIE ([A-Za-z\\d.]+);");
    private static final Pattern safariVersionPattern = Pattern.compile("Version/([A-Za-z\\d.]+)");
    private static final Pattern maxthonVersionPattern = Pattern.compile("Maxthon/([A-Za-z\\d.]+)");

    private String userAgent = "";
    private BrowserEnum browserType = BrowserEnum.UNKNOWN;//浏览器类型
    private String browserVersion = "";//浏览器版本
    private PlatformEnum platformType = PlatformEnum.UNKNOWN;//平台类型
    private String platformSeries = "";//平台系列
    private String platformVersion = "";//平台版本

    /**
     * 用途：根据客户端 User Agent Strings 判断其浏览器、操作平台
     * if 判断的先后次序：
     * 根据设备的用户使用量降序排列，这样对于大多数用户来说可以少判断几次即可拿到结果：
     * >>操作系统:Windows > 苹果 > 安卓 > LINUX > ...
     * >>Browser:CHROME > FF > IE > ...
     */
    public UserAgentAnalyzer(String userAgent) {
        this.userAgent = userAgent;
        if (StringUtils.isBlank(userAgent)) {
            return;
        }
        judgePlatform();
        judgeBrowser();
    }

    private void judgePlatform() {
        final String userAgent = this.userAgent;

        if (userAgent.contains("Windows")) {//主流应用靠前
            /*
             * ******************
             * 台式机 Windows 系列
             * ******************
             * Windows NT 10.0   -   Windows 10
             * Windows NT 6.2   -   Windows 8
             * Windows NT 6.1   -   Windows 7
             * Windows NT 6.0   -   Windows Vista
             * Windows NT 5.2   -   Windows Server 2003; Windows XP x64 Edition
             * Windows NT 5.1   -   Windows XP
             * Windows NT 5.01  -   Windows 2000, Service Pack 1 (SP1)
             * Windows NT 5.0   -   Windows 2000
             * Windows NT 4.0   -   Microsoft Windows NT 4.0
             * Windows 98; Win 9x 4.90  -   Windows Millennium Edition (Windows Me)
             * Windows 98   -   Windows 98
             * Windows 95   -   Windows 95
             * Windows CE   -   Windows CE
             * 判断依据:http://msdn.microsoft.com/en-us/library/ms537503(v=vs.85).aspx
             */
            setPlatformType(PlatformEnum.WINDOWS);
            if (userAgent.contains("Windows NT 10.0")) {//Windows 10
                setPlatformSeries("10");
            }else if (userAgent.contains("Windows NT 6.2")) {//Windows 8
                setPlatformSeries("8");
            } else if (userAgent.contains("Windows NT 6.1")) {//Windows 7
                setPlatformSeries("7");
            } else if (userAgent.contains("Windows NT 6.0")) {//Windows Vista
                setPlatformSeries("Vista");
            } else if (userAgent.contains("Windows NT 5.2")) {//Windows XP x64 Edition
                setPlatformSeries("XP");
                setPlatformVersion("x64 Edition");
            } else if (userAgent.contains("Windows NT 5.1")) {//Windows XP
                setPlatformSeries("XP");
            } else if (userAgent.contains("Windows NT 5.01")) {//Windows 2000, Service Pack 1 (SP1)
                setPlatformSeries("2000");
                setPlatformVersion("SP1");
            } else if (userAgent.contains("Windows NT 5.0")) {//Windows 2000
                setPlatformSeries("2000");
            } else if (userAgent.contains("Windows NT 4.0")) {//Microsoft Windows NT 4.0
                setPlatformSeries("NT 4.0");
            } else if (userAgent.contains("Windows 98; Win 9x 4.90")) {//Windows Millennium Edition (Windows Me)
                setPlatformSeries("ME");
            } else if (userAgent.contains("Windows 98")) {//Windows 98
                setPlatformSeries("98");
            } else if (userAgent.contains("Windows 95")) {//Windows 95
                setPlatformSeries("95");
            } else if (userAgent.contains("Windows CE")) {//Windows CE
                setPlatformSeries("CE");
            }
            if(userAgent.contains("Win64")||userAgent.contains("x64")){
                setPlatformVersion("x64");
            }else if(userAgent.contains("Win32")||userAgent.contains("x86")){
                setPlatformVersion("x86");
            }
            return;
        }
        if (userAgent.contains("Mac OS X")) {
            /*
             * ********
             * 苹果系列
             * ********
             * iPod -       Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8G4 SAFARI/6533.18.5
             * iPad -       Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b SAFARI/531.21.10
             * iPad2    -       Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 SAFARI/7534.48.3
             * iPhone 4 -   Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 SAFARI/6531.22.7
             * iPhone 5 -   Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 SAFARI/7534.48.3
             * 判断依据:http://www.useragentstring.com/pages/Safari/
             */
            setPlatformType(PlatformEnum.MAC);
            if (userAgent.contains("iPad")) {
                setPlatformSeries("iPad");
            } else if (userAgent.contains("iPhone")) {
                setPlatformSeries("iPhone");
            } else if (userAgent.contains("iPod")) {
                setPlatformSeries("iPod");
            }
            Matcher matcher = macOsXVersionPattern.matcher(userAgent);
            if (matcher.find()) {
                setPlatformVersion(matcher.group(1));
            }
            return;
        }
        if (userAgent.contains("Linux")) {
            if (userAgent.contains("Android")) {
                setPlatformType(PlatformEnum.ANDROID);
                return;
            }
            setPlatformType(PlatformEnum.LINUX);
        }
    }

    /**
     * 用途：根据客户端 User Agent Strings 判断其浏览器
     */
    private void judgeBrowser() {
        final String userAgent = this.userAgent;

        if (userAgent.contains(CHROME_FEATURE)) {
            /*
             * ***********
             * CHROME 系列
             * ***********
             * CHROME 24.0.1295.0   -   Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.15 (KHTML, like Gecko) CHROME/24.0.1295.0 SAFARI/537.15
             * CHROME 24.0.1292.0   -   Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.14 (KHTML, like Gecko) CHROME/24.0.1292.0 SAFARI/537.14
             * CHROME 24.0.1290.1   -   Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.13 (KHTML, like Gecko) CHROME/24.0.1290.1 SAFARI/537.13
             * 判断依据:http://www.useragentstring.com/pages/Chrome/
             */
            setBrowserType(BrowserEnum.CHROME);
            Matcher matcher = chromeVersionPattern.matcher(userAgent);
            if (matcher.find()) {
                setBrowserVersion(matcher.group(1));
            }
            return;
        }
        if (userAgent.contains(FIREFOX_FEATURE)) {
            /*
             * *******
             * FF 系列
             * *******
             * Firefox 16.0.1   -   Mozilla/5.0 (Windows NT 6.2; Win64; x64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1
             * Firefox 15.0a2   -   Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20120716 Firefox/15.0a2
             * Firefox 15.0.2   -   Mozilla/5.0 (Windows NT 6.2; WOW64; rv:15.0) Gecko/20120910144328 Firefox/15.0.2
             * 判断依据:http://www.useragentstring.com/pages/Firefox/
             */
            setBrowserType(BrowserEnum.FIRE_FOX);
            Matcher matcher = fireFoxVersionPattern.matcher(userAgent);
            if (matcher.find()) {
                setBrowserVersion(matcher.group(1));
            }
            return;
        }
        if (userAgent.contains(IE_FEATURE)) {
            /*
             * *******
             * IE 系列
             * *******
             * MSIE 10.0    -   Internet Explorer 10
             * MSIE 9.0 -   Internet Explorer 9
             * MSIE 8.0 -   Internet Explorer 8 or IE8 Compatibility View/Browser Mode
             * MSIE 7.0 -   Windows Internet Explorer 7 or IE7 Compatibility View/Browser Mode
             * MSIE 6.0 -   Microsoft Internet Explorer 6
             * 判断依据:http://msdn.microsoft.com/en-us/library/ms537503(v=vs.85).aspx
             */
            if (userAgent.contains(QH360_FATURE) || userAgent.contains(QH360_FATURE2)) {
                setBrowserType(BrowserEnum.QH360);
                return;
            }
            if (userAgent.contains(MAXTHON_FATURE)) {
                setBrowserType(BrowserEnum.MAXTHON);
                Matcher matcher = maxthonVersionPattern.matcher(userAgent);
                if (matcher.find()) {
                    setBrowserVersion(matcher.group(1));
                }
                return;
            }
            setBrowserType(BrowserEnum.IE);
            Matcher matcher = ieVersionPattern.matcher(userAgent);
            if (matcher.find()) {
                setBrowserVersion(matcher.group(1));
            }
            return;
        }
        if (userAgent.contains(SAFARI_FEATURE)) {
            /*
              Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Version/3.1 SAFARI/525.13
              Mozilla/5.0 (iPhone; U; CPU like Mac OS X) AppleWebKit/420.1 (KHTML, like Gecko) Version/3.0 Mobile/4A93 SAFARI/419.3
             */
            setBrowserType(BrowserEnum.SAFARI);
            Matcher matcher = safariVersionPattern.matcher(userAgent);
            if (matcher.find()) {
                setBrowserVersion(matcher.group(1));
            }
            return;
        }
        if (userAgent.contains(UC_FATURE)) {
            setBrowserType(BrowserEnum.UC);
            return;
        }
        if (userAgent.contains(QQ_FATURE)) {
            setBrowserType(BrowserEnum.QQ);
            return;
        }
    }
}
