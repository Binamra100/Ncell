package com.teletalk.premiumsms;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Home_PremiumSms extends ListActivity {
    private final int internet_sms = 2;
    private final int horoscope_nepali = 14;
    private final int horoscope_english = 15;
    private final int serviceList = 3;
    private int statusSubscribe = 11111;
    private final int statusUnsubscribe = 10001;
    private final int call = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__premium_sms);
        final int operator =getTypeOfSim();
        setListAdapter(new MyAdapter<String>(this, android.R.layout.simple_list_item_1, R.id.row, getResources().getStringArray(R.array.service_list)));
        ListView lv = getListView();

        //ListView OnclickListener
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if (operator == 44) {
                     if (position == call) {
                        //For Phone Call

                        Intent intent = new Intent(Home_PremiumSms.this, VoiceCall.class);
                        intent.putExtra("voice_call", call);
                        startActivity(intent);
                    } else {
                        if (position == internet_sms) {
                            //For Internet SMS.
                            Intent intent = new Intent(Home_PremiumSms.this, InternetSMS.class);
                            intent.putExtra("internet_sms", internet_sms);
                            startActivity(intent);
                        } else {
                            if (position == horoscope_nepali || position == horoscope_english) {
                                //For Horoscope.
                                Intent intent = new Intent(Home_PremiumSms.this, Horoscope.class);
                                //System.out.println("HOROSCOPE");
                                intent.putExtra("service", position);
                                startActivity(intent);
                            } else {
                                //For News and other stuffs.
                                Intent intent = new Intent(Home_PremiumSms.this, SubscribeUnsubcribe.class);
                                System.out.println("servicelistIntet");

                                int val2 = 0;
                                intent.putExtra("service", serviceList);
                                intent.putExtra("val2", val2);
                                intent.putExtra("val3", position);
                                startActivity(intent);
                            }
                        }
                    }
                } else if (operator == 88) {

                    if (position == call || position == internet_sms || position == 0) {
                        //For Phone Call
                        Toast.makeText(getBaseContext(), "This service is not allowed for Ncell", Toast.LENGTH_LONG).show();
                    } else {
                        if (position == horoscope_nepali || position == horoscope_english) {
                            //For Horoscope.
                            Intent intent = new Intent(Home_PremiumSms.this, Horoscope.class);

                            intent.putExtra("service", position);
                            startActivity(intent);
                        }
                        else {
                            //For News and other stuffs.
                            Intent intent = new Intent(Home_PremiumSms.this, NcellMessage.class);
                            int val2 = 0;

                            intent.putExtra("val1", val2);
                            intent.putExtra("val2", position);
                            startActivity(intent);
                        }

                    }
                }
            }});
    }


    public class MyAdapter<T> extends ArrayAdapter<String> {

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         String[] strings) {
            super(context, resource, textViewResourceId, strings);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inflater.inflate(R.layout.custom_layout, parent, false);
            String[] items = getResources().getStringArray(R.array.service_list);
            TextView tv = (TextView) row.findViewById(R.id.row);
            tv.setText(items[position]);
            return row;
        }

    }
    private int getTypeOfSim() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //phone number line
        String OperatorName = tm.getSimOperatorName();


        if (OperatorName.equals("Namaste")) {
            return 88;

        } else if (OperatorName.equals("NCELL")) {
            return 44;
        } else
            return 0;
    }

}
