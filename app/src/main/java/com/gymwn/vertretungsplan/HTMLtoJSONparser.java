package com.gymwn.vertretungsplan;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLtoJSONparser {
    final String HTMLstring = "<html><head>\n" +
            "<title>Untis 2020 Vertretungsplan</title>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\n" +
            "<meta http-equiv=\"expires\" content=\"0\">\n" +
            "<style type=\"text/css\">\n" +
            "\n" +
            "body { margin-top: 0px; margin-left: 20px; margin-right: 20px;\n" +
            "background: #fff; color: #272727; font: 80% Arial, Helvetica, sans-serif; }\n" +
            "\n" +
            "h1 { color: #ee7f00; font-size: 150%; font-weight: normal;}\n" +
            "h1 strong { font-size: 200%; font-weight: normal; }\n" +
            "h2 { font-size: 125%;}\n" +
            "\n" +
            "h1, h2 { margin: 0; padding: 0;}\n" +
            "\n" +
            "th { background: #000; color: #fff; }\n" +
            "table.mon_list th, td { padding: 8px 4px;}\n" +
            "\n" +
            "\n" +
            ".mon_title \n" +
            "{ \n" +
            "\tfont-weight: bold; \n" +
            "\tfont-size: 120%; \n" +
            "\tclear: both; \n" +
            "\tmargin: 0; \n" +
            "}\n" +
            "\n" +
            ".inline_header\n" +
            "{\n" +
            "\tfont-weight: bold; \n" +
            "}\n" +
            "\n" +
            "table.info\n" +
            "{\n" +
            "\tcolor: #000000; \n" +
            "\tfont-size: 100%;\n" +
            "\tborder: 1px;\n" +
            "\tborder-style:solid;\n" +
            "\tborder-collapse:collapse;\n" +
            "\tpadding: 8px 4px;\n" +
            "}\n" +
            "\n" +
            "table.mon_list\n" +
            "{\n" +
            "\tcolor: #000000; \n" +
            "\twidth: 100%; \n" +
            "\tfont-size: 100%;\n" +
            "\tborder: 1px;\n" +
            "\tborder-style:solid;\n" +
            "\tborder-collapse:collapse;\n" +
            "}\n" +
            "\n" +
            "table.mon_head\n" +
            "{\n" +
            "\tcolor: #000000; \n" +
            "\twidth: 100%; \n" +
            "\tfont-size: 100%;\n" +
            "}\n" +
            "\n" +
            "td.info,\n" +
            "th.list,\n" +
            "td.list,\n" +
            "tr.list\n" +
            "{\n" +
            "\tborder: 1px;\n" +
            "\tborder-style: solid;\n" +
            "\tborder-color: black;\n" +
            "\tmargin: 0px;\n" +
            "\tborder-collapse:collapse;\n" +
            "\tpadding: 3px;\n" +
            "}\n" +
            "\n" +
            "tr.odd { background: #fad3a6; }\n" +
            "tr.even { background: #fdecd9; }\n" +
            "\n" +
            "</style>\n" +
            "<meta name=\"generator\" content=\"Untis 2020\">\n" +
            "<meta name=\"company\" content=\"Gruber &amp; Petters Software, A-2000 Stockerau, Austria, www.untis.at\">\n" +
            "<script>try {\n" +
            "                        Object.defineProperty(screen, \"availTop\", { value: 0 });\n" +
            "                    } catch (e) {}\n" +
            "                    try {\n" +
            "                        Object.defineProperty(screen, \"availLeft\", { value: 0 });\n" +
            "                    } catch (e) {}\n" +
            "                    try {\n" +
            "                        Object.defineProperty(screen, \"availWidth\", { value: 1920 });\n" +
            "                    } catch (e) {}\n" +
            "                    try {\n" +
            "                        Object.defineProperty(screen, \"availHeight\", { value: 1080 });\n" +
            "                    } catch (e) {}\n" +
            "                    try {\n" +
            "                        Object.defineProperty(screen, \"colorDepth\", { value: 24 });\n" +
            "                    } catch (e) {}\n" +
            "                    try {\n" +
            "                        Object.defineProperty(screen, \"pixelDepth\", { value: 24 });\n" +
            "                    } catch (e) {}\n" +
            "                    try {\n" +
            "                        Object.defineProperty(navigator, \"hardwareConcurrency\", { value: 8 });\n" +
            "                    } catch (e) {}\n" +
            "                    try {\n" +
            "                        Object.defineProperty(navigator, \"appVersion\", { value: \"5.0 (Windows)\" });\n" +
            "                    } catch (e) {}\n" +
            "                    try {\n" +
            "                        Object.defineProperty(navigator, \"doNotTrack\", { value: \"unspecified\" });\n" +
            "                    } catch (e) {}\n" +
            "                    \n" +
            "            try {\n" +
            "                window.screenY = 0\n" +
            "            } catch (e) { }\n" +
            "        \n" +
            "            try {\n" +
            "                window.screenTop = 0\n" +
            "            } catch (e) { }\n" +
            "        \n" +
            "            try {\n" +
            "                window.top.window.outerHeight = window.screen.height\n" +
            "            } catch (e) { }\n" +
            "        \n" +
            "            try {\n" +
            "                window.screenX = 485\n" +
            "            } catch (e) { }\n" +
            "        \n" +
            "            try {\n" +
            "                window.screenLeft = 485\n" +
            "            } catch (e) { }\n" +
            "        \n" +
            "            try {\n" +
            "                window.top.window.outerWidth = 977\n" +
            "            } catch (e) { }\n" +
            "        </script></head>\n" +
            "\n" +
            "<body>\n" +
            "<table class=\"mon_head\">\n" +
            "    <tbody><tr>\n" +
            "        <td valign=\"bottom\"><h1><strong>Untis</strong> 2020 <!-- Info-Stundenplan --></h1></td>\n" +
            "        <td valign=\"bottom\"></td>\n" +
            "        <td valign=\"bottom\" align=\"right\">\n" +
            "            <p>Gymnasium Neu Wulmstorf <span style=\"width:10px\">&nbsp;</span> D-21629,E.-Moritz-Arndt-Str.20<br>\n" +
            "            SJ 2020/2021<span style=\"width:10px\">&nbsp;</span>1. Halbjahr <span style=\"width:10px\">&nbsp;</span> Stand: 13.11.2020 12:45</p>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "</tbody></table>\n" +
            "\n" +
            "<center>\n" +
            "<div class=\"mon_title\">13.11.2020 Freitag, Woche B</div>\n" +
            "<table class=\"mon_list\">\n" +
            "<tbody><tr class=\"list\"><th class=\"list\" align=\"center\">Stunde</th><th class=\"list\" align=\"center\">Fach</th><th class=\"list\" align=\"center\">Raum</th><th class=\"list\" align=\"center\">Text</th></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">6-1</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\"><s>Mus</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">6-2</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\">Eng</td><td class=\"list\" align=\"center\">A115</td><td class=\"list\" align=\"center\">U-Besuch</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\"><s>Fln</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\">Eng</td><td class=\"list\">&nbsp;</td><td class=\"list\" align=\"center\">U-Besuch</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">6-3</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">3</td><td class=\"list\" align=\"center\"><s>Eng</s>?Aufg</td><td class=\"list\" align=\"center\">A111</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">4</td><td class=\"list\" align=\"center\"><s>Eng</s>?Aufg</td><td class=\"list\" align=\"center\">A111</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list inline_header\" colspan=\"4\">7-1</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">3</td><td class=\"list\" align=\"center\"><s>Fra</s>?Aufg</td><td class=\"list\" align=\"center\">A221</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">4</td><td class=\"list\" align=\"center\"><s>Fra</s>?Aufg</td><td class=\"list\" align=\"center\">A221</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">7-3</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">2</td><td class=\"list\" align=\"center\"><s>Kun</s>?Phy</td><td class=\"list\" align=\"center\">Ku2/A003</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">3</td><td class=\"list\" align=\"center\">Kun</td><td class=\"list\" align=\"center\">Ku2/A003</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">4</td><td class=\"list\" align=\"center\">Deu</td><td class=\"list\" align=\"center\">A217</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\"><s>Deu</s>?Vertr</td><td class=\"list\" align=\"center\">A217</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list inline_header\" colspan=\"4\">7-5</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\"><s>Mus</s></td><td class=\"list\" align=\"center\"><s>Mu1/A015</s>?---</td><td class=\"list\" align=\"center\">Aufgaben auf IServ</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\"><s>Mus</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">8-3</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">2</td><td class=\"list\" align=\"center\">Ges</td><td class=\"list\" align=\"center\">C205</td><td class=\"list\" align=\"center\">U-Besuch</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">9-2</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">4</td><td class=\"list\" align=\"center\"><s>Pol</s>?Aufg</td><td class=\"list\" align=\"center\">C203</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\">Pol</td><td class=\"list\" align=\"center\">C203</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\"><s>Eng</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">9-4</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">1 - 2</td><td class=\"list\" align=\"center\"><s>Spo</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">10-2</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">3</td><td class=\"list\" align=\"center\"><s>Che</s>?Eng</td><td class=\"list\" align=\"center\"><s>Ch1</s>?B105</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">4</td><td class=\"list\" align=\"center\"><s>Che</s>?Aufg</td><td class=\"list\" align=\"center\">Ch1</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\"><s>Eng</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">11-1</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\"><s>Bio</s>?Aufg</td><td class=\"list\" align=\"center\">Bi2</td><td class=\"list\" align=\"center\">Aufgaben auf IServ</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\"><s>Bio</s>?Aufg</td><td class=\"list\" align=\"center\">Bi2</td><td class=\"list\" align=\"center\">Aufgaben auf IServ</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list inline_header\" colspan=\"4\">11-3</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">1</td><td class=\"list\" align=\"center\"><s>Che</s>?Aufg</td><td class=\"list\" align=\"center\">Ch1</td><td class=\"list\" align=\"center\">Aufgaben auf IServ</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">2</td><td class=\"list\" align=\"center\"><s>Che</s>?Aufg</td><td class=\"list\" align=\"center\">Ch1</td><td class=\"list\" align=\"center\">Aufgaben auf IServ</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\"><s>Eng</s>?Pol</td><td class=\"list\" align=\"center\">C103</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\"><s>Pol</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">12</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">1 - 2</td><td class=\"list\" align=\"center\">fr1</td><td class=\"list\" align=\"center\">B107</td><td class=\"list\" align=\"center\">Klausur!</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">1 - 2</td><td class=\"list\" align=\"center\">en1</td><td class=\"list\" align=\"center\"><s>B103</s>?Bi2</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">3</td><td class=\"list\" align=\"center\">en1</td><td class=\"list\" align=\"center\">Bi2</td><td class=\"list\" align=\"center\">NTA</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\">wn21</td><td class=\"list\" align=\"center\"><s>B103</s>?B105</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">5</td><td class=\"list\" align=\"center\">re1</td><td class=\"list\" align=\"center\"><s>B101</s>?B106</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\">re1</td><td class=\"list\" align=\"center\"><s>B101</s>?B106</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">6</td><td class=\"list\" align=\"center\">wn21</td><td class=\"list\" align=\"center\"><s>B103</s>?B105</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">13</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">1 - 2</td><td class=\"list\" align=\"center\">fr1</td><td class=\"list\" align=\"center\">B107</td><td class=\"list\" align=\"center\">Klausur!</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">3 - 4</td><td class=\"list\" align=\"center\"><s>GE1</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">3</td><td class=\"list\" align=\"center\"><s>PO1</s>?Aufg</td><td class=\"list\" align=\"center\">A109</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list\" align=\"center\">4</td><td class=\"list\" align=\"center\"><s>PO1</s>?Aufg</td><td class=\"list\" align=\"center\">A109</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">5 - 6</td><td class=\"list\" align=\"center\"><s>BI1</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "<tr class=\"list odd\"><td class=\"list inline_header\" colspan=\"4\">Verw</td></tr>\n" +
            "<tr class=\"list even\"><td class=\"list\" align=\"center\">1</td><td class=\"list\" align=\"center\"><s>SGSE</s></td><td class=\"list\" align=\"center\">---</td><td class=\"list\" align=\"center\">&nbsp;</td></tr>\n" +
            "</tbody></table>\n" +
            "<p>\n" +
            "<font size=\"3\" face=\"Arial\">\n" +
            "Periode4   13.11.2020 B   \n" +
            "</font></p></center>\n" +
            "\n" +
            "<p></p><center><font size=\"2\" face=\"Arial\"><a href=\"http://www.untis.at/\" target=\"_blank\">Untis Stundenplan Software</a></font></center>\n" +
            "\n" +
            "\n" +
            "\n" +
            "</body></html>";


}
