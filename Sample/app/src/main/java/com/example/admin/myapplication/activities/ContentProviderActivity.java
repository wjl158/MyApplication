package com.example.admin.myapplication.activities;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContentProviderActivity extends AppCompatActivity {
    public TextView tv1;
    public String string = "";
    public static final int READ_MSG = 1;
    public static final int INSERT_MSG = 2;
    public static final int READ_CONTACTS = 3;
    public static final int READ_CONTACTS_BY_ID = 4;
    public static final int INSERT_CONTACTS = 4;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            switch (msg.what)
//            {
//                case READ_MSG:
//                    break;
//                case INSERT_MSG:
//                    break;
//                default:
//                    break;
//            }
            tv1.setText(string);
            Toast.makeText(getApplicationContext(), "完成", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        tv1 = (TextView)findViewById(R.id.tv1);
    }

    public void btnClick(View v)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (v.getId())
                {
                    case R.id.btnReadMsg:
                        getMsg();
                        break;
                    case R.id.btnInsertMsg:
                        insertMsg();
                        break;
                    case R.id.btnReadContacts:
                        readContacts();
                        break;
                    case R.id.btnReadContactsByID:
                        readContactsByID();
                        break;
                    case R.id.btnInsertContact:
                        insertContact();
                        break;
                }
            }
        }).start();
    }


    /**
     * 读取短信内容
     */
    public void getMsg()
    {
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        //获取的是哪些列的信息
        string = "";
        Cursor cursor = resolver.query(uri, new String[]{"address","date","type","body"}, null, null, null);
        while(cursor.moveToNext())
        {
            String address = cursor.getString(0);
            String date = cursor.getString(1);
            String type = cursor.getString(2);
            String body = cursor.getString(3);

            string = string + "号码:" + address + "\n";
            string = string + "时间:" + date + "\n";
            string = string + "类型:" + type + "\n";
            string = string + "内容:" + body + "\n";
        }
        cursor.close();
        handler.sendEmptyMessage(READ_MSG);
    }

    /**
     * 插入短信内容
     */
    public void insertMsg()
    {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://sms/");
        ContentValues conValues = new ContentValues();
        conValues.put("address", "123456789");
        conValues.put("type", 1);
        conValues.put("date", System.currentTimeMillis());
        conValues.put("body", "插入短信内容测试");
        resolver.insert(uri, conValues);

        string = "";
        string = string + "号码:" + "123456789" + "\n";
        string = string + "时间:" + System.currentTimeMillis() + "\n";
        string = string + "类型:" + "1" + "\n";
        string = string + "内容:" + "插入短信内容测试" + "\n";

        Log.e("HeHe", "短信插入完毕~");

        handler.sendEmptyMessage(INSERT_MSG);
    }

    /**
     * 读取联系人
     */
    public void readContacts()
    {
        //①查询raw_contacts表获得联系人的id
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //查询联系人数据
        Cursor cursor = resolver.query(uri, null, null, null, null);
        string = "";
        while(cursor.moveToNext())
        {
            //获取联系人姓名,手机号码
            String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            string = string + "姓名:" + cName + "   ";
            string = string + "号码:" + cNum + "\n";
            System.out.println("======================");
        }
        cursor.close();
        handler.sendEmptyMessage(READ_CONTACTS);
    }

    /**
     * 读取联系人信息-程大江
     */
    public void readContactsByID()
    {
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + "13244772075");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"display_name"}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            string = string + "姓名:" + name + "   " ;
            string = string + "电话:" + "13244772075" + "   " +  "\n";
        }
        cursor.close();
        handler.sendEmptyMessage(READ_CONTACTS_BY_ID);
    }

    /**
     * 添加联系人信息-abcdefg
     */
    public void insertContact()  {
        //使用事务添加联系人
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri =  Uri.parse("content://com.android.contacts/data");

        ContentResolver resolver = getContentResolver();
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        ContentProviderOperation op1 = ContentProviderOperation.newInsert(uri)
                .withValue("account_name", null)
                .build();
        operations.add(op1);

        //依次是姓名，号码，邮编
        ContentProviderOperation op2 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/name")
                .withValue("data2", "abcdefg")
                .build();
        operations.add(op2);

        ContentProviderOperation op3 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data1", "13798988888")
                .withValue("data2", "2")
                .build();
        operations.add(op3);

        ContentProviderOperation op4 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/email_v2")
                .withValue("data1", "779878443@qq.com")
                .withValue("data2", "2")
                .build();
        operations.add(op4);
        //将上述内容添加到手机联系人中~
        try{
            resolver.applyBatch("com.android.contacts", operations);
        }catch (Exception e){

        }
        handler.sendEmptyMessage(READ_CONTACTS_BY_ID);
    }
}
