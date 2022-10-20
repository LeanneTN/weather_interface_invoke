import client.GetRegionProvince;
import client.GetWeather;
import client.WeatherWSLocator;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.rmi.RemoteException;
import org.jdom.*;

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



        return;
    }
}
