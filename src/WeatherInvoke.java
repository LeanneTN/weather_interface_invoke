import client.GetRegionProvince;
import client.GetWeather;
import client.WeatherWSLocator;

import javax.xml.rpc.ServiceException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.javafx.binding.StringFormatter;
import org.jdom.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import sun.util.calendar.BaseCalendar;

public class WeatherInvoke {
    public static void main(String[] args) throws ServiceException, RemoteException,
            IOException {
        WeatherWSLocator locator = new WeatherWSLocator();

        String[] weather = locator.getWeatherWSSoap().getWeather("上海", "");
        System.out.println();

        Document document = new Document();
        Element weatherOfShanghai = new Element("weather");
        document.addContent(weatherOfShanghai);

        Comment comment = new Comment("本文件为接口所获取到的上海天气");
        weatherOfShanghai.addContent(comment);

        Element province = new Element("province");
        weatherOfShanghai.addContent(province);
        Attribute province_attr = new Attribute("id", "province");
        province.setAttribute(province_attr);
        province.setText(weather[0]);

        Element area = new Element("area");
        weatherOfShanghai.addContent(area);
        Attribute area_attr = new Attribute("id", "area");
        area.setAttribute(area_attr);
        area.setText(weather[1]);

        Element currentTime = new Element("current_time");
        weatherOfShanghai.addContent(currentTime);
        Attribute currentTimeAttr = new Attribute("id", "current_time");
        currentTime.setAttribute(currentTimeAttr);
        currentTime.setText(weather[3]);


        Element todayWeather = new Element("weather_today");
        Attribute todayWeatherAttr = new Attribute("id", "weather_today");
        todayWeather.setAttribute(todayWeatherAttr);
        weatherOfShanghai.addContent(todayWeather);

        Element currentWeather = new Element("current_weather");
        Attribute currentWeatherAttr = new Attribute("id", "current_weather");
        currentWeather.setAttribute(currentWeatherAttr);
        currentWeather.setText(weather[4]);
        todayWeather.addContent(currentWeather);

        Element UV = new Element("uv_index");
        Attribute uvAttr = new Attribute("id", "uv_index");
        UV.setAttribute(uvAttr);
        UV.setText(weather[5]);
        todayWeather.addContent(UV);

        String[] advices = weather[6].split("\n");
        Element advice = new Element("advice");
        todayWeather.addContent(advice);
        Attribute adviceAttr = new Attribute("id", "advice");
        advice.setAttribute(adviceAttr);
        Element coldIndex = new Element("cold_index");
        Attribute coldAttr = new Attribute("id", "cold_index");
        coldIndex.setAttribute(coldAttr);
        coldIndex.setText(advices[0]);
        advice.setContent(coldIndex);
        Element sportIndex = new Element("sport_index");
        Attribute sportAttr = new Attribute("id", "sport_index");
        sportIndex.setAttribute(sportAttr);
        sportIndex.setText(advices[1]);
        advice.addContent(sportIndex);
        Element allergicIndex = new Element("allergic_index");
        Attribute allergicAttr = new Attribute("id", "allergic_index");
        allergicIndex.setAttribute(allergicAttr);
        allergicIndex.setText(advices[2]);
        advice.addContent(allergicIndex);

        Element briefReport = new Element("brief_report");
        Attribute attribute = new Attribute("id", "brief_report");
        briefReport.setAttribute(attribute);
        todayWeather.addContent(briefReport);
        int i = 7;
        while (i <= 29){
            Element report = new Element("report_in_3_days");

            Element weatherCondition = new Element("weather_condition");
            weatherCondition.setText(weather[i++]);
            report.addContent(weatherCondition);

            Element temperature = new Element("temperature");
            temperature.setText(weather[i++]);
            report.addContent(temperature);

            Element wind = new Element("wind");
            wind.setText(weather[i++]);
            report.addContent(wind);

            briefReport.addContent(report);
            i += 2;
        }

        Format format = Format.getPrettyFormat();
        format.setEncoding("utf-8");
        format.setIndent("  ");

        XMLOutputter out = new XMLOutputter(format);
        SimpleDateFormat format1 = new SimpleDateFormat("HH-mm-ss");
        Date date = new Date();
        out.output(document, new FileOutputStream(String.format("weather%s.xml", format1.format(date))));

    }
}
