package com.gymwn.vertretungsplan;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class HTMLtoJSONparser {



    public String main(String HTMLstring) throws JSONException {
        HTMLstring = HTMLstring.replace("\n", "").replace("\r", "");
        HTMLstring = HTMLstring.replaceAll("\\<style type=\\\"text/css\\\">.*\\</style>", "");
        HTMLstring = HTMLstring.replaceAll("\\<script>.*\\</script>", "");
        HTMLstring = HTMLstring.replaceAll("\\<head>.*\\</head>", "");
        HTMLstring = HTMLstring.replaceAll("  ", "");
        HTMLstring = HTMLstring.replaceAll("\\<html><body><table class=\"mon_head\">.*\\</p></td></tr></table><center>", "");
        Pattern pattern = Pattern.compile("<div class=\"mon_title\">(.*?)</div>");
        Matcher matcher = pattern.matcher(HTMLstring);
        String date = "";
        if (matcher.find()) date = matcher.group(1);
        HTMLstring = HTMLstring.replaceAll("\\<div class=\"mon_title\">.*\\</div><table class=\"mon_list\" ><tr class='list'><th class=\"list\" align=\"center\">Stunde</th><th class=\"list\" align=\"center\">Fach</th><th class=\"list\" align=\"center\">Raum</th><th class=\"list\" align=\"center\">Text</th></tr>", "");
        HTMLstring = HTMLstring.replaceAll("\\</table>.*\\</body></html>", "");
        HTMLstring = HTMLstring.replace("list odd", "row").replace("list even", "row");
        String rowstart = "<tr class='row'><td class=\"list inline_header\" colspan=\"4\" >";
        String rowend = "</td></tr>";
        pattern = Pattern.compile(rowstart + "(.*?)" + rowend);
        matcher = pattern.matcher(HTMLstring);
        ArrayList<String> classes = new ArrayList<String>();
        while (matcher.find())
            for (int i = 1; i <= matcher.groupCount(); i++)
                classes.add(matcher.group(i));
        JSONObject json = new JSONObject();
        JSONObject JSONclasses = new JSONObject();
        for(int i = 0;  i < classes.size(); i++) {
            String cclass = classes.get(i);
            pattern = Pattern.compile(cclass + rowend + "(.*?)" + rowstart);
            matcher = pattern.matcher(HTMLstring + rowstart);
            String m = "";
            if (matcher.find()) m = matcher.group(1);
            String lesson = "";
            String subject = "";
            String room = "";
            String info = "";
            boolean exercises = false;
            boolean free = false;
            boolean replace = false;
            String elso = "";
            JSONObject lessionJSON = new JSONObject();
            String[] line = m.split("</td></tr>");
            for(int j = 0; j < line.length; j++) {
                String a = line[j].replace("<tr class='row'>", "").replace(" align=\"center\"", "").replace(" class=\"list\"", "").replace("<td>", "").replace("</td>", "#");
                String[] as = a.split("#");
                lesson = as[0];
                subject = as[1];
                room = as[2];
                info = as[3];
                exercises = (subject.toLowerCase().contains("?Aufg".toLowerCase())) ? true : false;
                free = (subject.toLowerCase().contains("<s>".toLowerCase())) ? true : false;
                replace = (subject.toLowerCase().contains("?Vertr".toLowerCase())) ? true : false;
                if(!exercises && !replace && free && subject.toLowerCase().contains("?".toLowerCase())) {
                    elso = subject.split("\\?")[1];
                }
                if(free) subject = extract(subject, "<s>", "</s>").get(0);
                info = (info.equals("&nbsp;")) ? "" : info;

                JSONObject detailsJSON = new JSONObject();
                detailsJSON.put("subject", subject);
                detailsJSON.put("exercises", exercises);
                detailsJSON.put("free", free);
                detailsJSON.put("replace", replace);
                detailsJSON.put("elso", elso);
                detailsJSON.put("room", room);
                detailsJSON.put("info", info);
                lessionJSON.put(lesson + "#" + hash(7), detailsJSON);
                elso = "";
            }
            JSONclasses.put(cclass, lessionJSON);
        }
        json.put(date, JSONclasses);
        return json.toString();
    }
    ArrayList<String> check = new ArrayList<String>();
    private String hash(int n) {
        String SALTCHARS = "abcdefghijklmnopqurstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < n) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        if(!Arrays.asList(check).contains(saltStr)) {
            check.add(saltStr);
            return saltStr;
        }
        else
            return hash(n);
    }

    private static ArrayList<String> extract(String HTMLstring, String a, String b) {
        ArrayList<String> o = new ArrayList<String>();
        Pattern pattern = Pattern.compile(a + "(.*?)" + b);
        Matcher matcher = pattern.matcher(HTMLstring);
        while (matcher.find())
            for (int k = 1; k <= matcher.groupCount(); k++)
                o.add(matcher.group(1));
        return o;
    }



}
