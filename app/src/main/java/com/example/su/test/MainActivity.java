package com.example.su.test;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * @author bigsmile
 * @package_name com.example.su
 * @date 2016/4/18
 * @desc TODO
 */

public class MainActivity extends AppCompatActivity {

    private EditText machine_code;   //机器码
    private EditText software_code;  //设备码
    private EditText hareware_code;  //本地机器码
    private TextView register_code;  //注册码
    private String machine_str;      //机器码
    private String software_str;    //设备码
    private String hareware_str;    //本地机器码
    private String register_str;    //注册码

    private Button btnGenerate;     //生成


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initData();
        hareware_code.setText(formatMachineCode(hareware_str));

//     点击生成注册码
       btnGenerate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               machine_str = machine_code.getText().toString();
               software_str = software_code.getText().toString();
               String register_str = GenrateCode(software_str, machine_str);
               register_code.setText(formatMachineCode(register_str));
               Toast.makeText(MainActivity.this, "生成成功", Toast.LENGTH_LONG).show();

           }
       });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //生成本地机器码方法
    private void initData() {
        hareware_str = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();//获取机器码
        Log.i("fjasmin + ", hareware_str);
        System.out.print(hareware_str);
    }

    private void  initView(){
        machine_code = (EditText) findViewById(R.id.machine_code);
        software_code = (EditText) findViewById(R.id.software_code);
        hareware_code = (EditText)findViewById(R.id.hardware_code);
        register_code = (TextView) findViewById(R.id.register_code);
        btnGenerate =(Button) findViewById(R.id.btnGenerate);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public static String GenrateCode(String software_str, String machine_str) {
        char[] codeList = new char[16];

        machine_str = machine_str.replace("-", "");

        int machineCount = 0;

        byte machineIdBytes[] = software_str.getBytes();
        byte hardwareIdBytes[] = machine_str.getBytes();

        for (int i = 0; i < machineIdBytes.length; i++) {
            machineCount += machineIdBytes[i];
        }

        for (int i = 0; i < codeList.length; i++) {
            int t = machineCount + hardwareIdBytes[i] * i + i;
            codeList[i] =(char) Integer.toHexString((t % 16)).getBytes()[0];
        }

        StringBuffer code = new StringBuffer();
        for (int i = 0; i < codeList.length; i++) {
            code.append(codeList[i]);
        }
        return code.toString().toUpperCase();
    }

    //格式化机器码
    private String formatMachineCode(String machine_code){
        StringBuilder sb=new StringBuilder();
//        sb.append("机器码：");
        sb.append(machine_code.substring(0,4));
        sb.append("-");
        sb.append(machine_code.substring(4,8));
        sb.append("-");
        sb.append(machine_code.substring(8,12));
        sb.append("-");
        sb.append(machine_code.substring(12,16));
        return sb.toString().toUpperCase();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.su.test/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.su.test/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
