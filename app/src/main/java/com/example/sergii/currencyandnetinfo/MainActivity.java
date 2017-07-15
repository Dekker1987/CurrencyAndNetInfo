package com.example.sergii.currencyandnetinfo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.sergii.currencyandnetinfo.interfaces.IDataDownloadFinish;
import com.example.sergii.currencyandnetinfo.helpers.DataDownloadHelper;
import com.example.sergii.currencyandnetinfo.models.CurrencyModel;
import com.example.sergii.currencyandnetinfo.models.IpInfoModel;
import com.example.sergii.currencyandnetinfo.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn_refresh;
    private TextView tv_ip;
    private TextView tv_isp;
    private TextView tv_timezone;
    private TextView tv_country;
    private TextView tv_country_code;
    private TextView tv_region_name;
    private TextView tv_city;
    private TextView tv_usd_currency_val;
    private TextView tv_euro_currency_value;
    private TextView tv_rur_currency_val;
    private TextView tv_bitcoin_currency_val;
    private Map<String, CurrencyModel> currlst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCurrencyContainer();
        initIpInfoViews();
        initCurrencyInfoViews();
        initRefreshButton();

        getCurrencyInfo();
        getIPinfo();
    }

    private void initCurrencyContainer(){
        currlst = new HashMap<>();
    }

    private void initRefreshButton(){
        btn_refresh = (Button)findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(refreshClickListener);
    }

    View.OnClickListener refreshClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            getIPinfo();
            getCurrencyInfo();
            resetViews();
        }
    };

    private void resetViews(){
        tv_ip.setText("IP: N/A");
        tv_isp.setText("ISP:  N/A");
        tv_timezone.setText("TimeZone: N/A");
        tv_country.setText("Country: N/A");
        tv_country_code.setText("Country Code: N/A");
        tv_region_name.setText("Region Name: N/A");
        tv_city.setText("City: N/A");
        tv_usd_currency_val.setText("Buy: N/A Sale: N/A");
        tv_euro_currency_value.setText("Buy: N/A Sale: N/A");
        tv_rur_currency_val.setText("Buy: N/A Sale: N/A");
        tv_bitcoin_currency_val.setText("Buy: N/A Sale: N/A");
        currlst.clear();
    }

    private void initIpInfoViews(){
        tv_ip = (TextView)findViewById(R.id.tv_ip);
        tv_isp = (TextView)findViewById(R.id.tv_isp);
        tv_timezone = (TextView)findViewById(R.id.tv_timezone);
        tv_country = (TextView)findViewById(R.id.tv_country);
        tv_country_code = (TextView)findViewById(R.id.tv_country_code);
        tv_region_name = (TextView)findViewById(R.id.tv_region_name);
        tv_city = (TextView)findViewById(R.id.tv_city);
    }

    private void initCurrencyInfoViews(){
        tv_usd_currency_val = (TextView)findViewById(R.id.tv_usd_currency_val);
        tv_euro_currency_value = (TextView)findViewById(R.id.tv_euro_currency_value);
        tv_rur_currency_val = (TextView)findViewById(R.id.tv_rur_currency_val);
        tv_bitcoin_currency_val = (TextView)findViewById(R.id.tv_bitcoin_currency_val);
    }

    private void getCurrencyInfo(){
       if(Utils.isInternetConnected(getApplicationContext())){
            new DataDownloadHelper(
                    "https://api.privatbank.ua/"
                   + "p24api/"
                   + "pubinfo"
                   + "?json&exchange"
                   + "&coursid=5"){{
                setOnTaskFinishListener(getCurrencyCallback);
            }}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void getIPinfo(){
        if(Utils.isInternetConnected(getApplicationContext())){
            new DataDownloadHelper("http://ip-api.com/json"){{
                setOnTaskFinishListener(getIPCallback);
            }}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    IDataDownloadFinish getCurrencyCallback = new  IDataDownloadFinish(){
        @Override
        public void onDownloadTaskCompleted(String results) {
            try{
                final JSONArray currencyInfo = new JSONArray(results);
                for(int i=0;i<currencyInfo.length();i++){
                    final JSONObject currencyInfoItem = (JSONObject)currencyInfo.get(i);
                    currlst.put(
                            currencyInfoItem.optString("ccy"),
                            new CurrencyModel(){{
                                setCcy(currencyInfoItem.optString("ccy"));
                                setBase_ccy(currencyInfoItem.optString("base_ccy"));
                                setBuy(currencyInfoItem.optString("buy"));
                                setSale(currencyInfoItem.optString("sale"));
                            }});
                }
                setDataToCurrencyViews();
            }catch(JSONException exc){
                exc.printStackTrace();
            }


        }
    };

    private void setDataToCurrencyViews(){
        for(Map.Entry<String, CurrencyModel> entry: currlst.entrySet()){
            if("USD".equals(entry.getKey())){
                tv_usd_currency_val.setText(entry.getValue().getStringToView());
            } else if("EUR".equals(entry.getKey())){
                tv_euro_currency_value.setText(entry.getValue().getStringToView());
            } else if("RUR".equals(entry.getKey())){
                tv_rur_currency_val.setText(entry.getValue().getStringToView());
            } else if("BTC".equals(entry.getKey())){
                tv_bitcoin_currency_val.setText(entry.getValue().getStringToView());
            }
        }
    }

    IDataDownloadFinish getIPCallback = new  IDataDownloadFinish(){
        @Override
        public void onDownloadTaskCompleted(String results) {
            try{
                final JSONObject result = new JSONObject(results);
                setDataToIpViews(
                        new IpInfoModel(){{
                            setIP(result.optString("query"));
                            setISP(result.optString("isp"));
                            setTimeZone(result.optString("timezone"));
                            setCountry(result.optString("country"));
                            setCountryCode(result.getString("countryCode"));
                            setRegionName(result.getString("regionName"));
                            setCity(result.optString("city"));
                        }});
            }catch(JSONException exc){
                exc.printStackTrace();
            }
        }
    };

    private void setDataToIpViews(IpInfoModel ipInfoModel){
        if(ipInfoModel!=null){
            tv_ip.setText("IP: " + ipInfoModel.getIP());
            tv_isp.setText("ISP: " + ipInfoModel.getISP());
            tv_timezone.setText("Time Zone: " + ipInfoModel.getTimeZone());
            tv_country.setText("Country: " + ipInfoModel.getCountry());
            tv_country_code.setText("Country Code: " + ipInfoModel.getCountryCode());
            tv_region_name.setText("Region Name: "+ ipInfoModel.getRegionName());
            tv_city.setText("City: " + ipInfoModel.getCity());
        }
    }
}
