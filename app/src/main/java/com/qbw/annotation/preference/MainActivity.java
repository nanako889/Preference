package com.qbw.annotation.preference;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.qbw.annotation.core.preference.reference.Preference;
import com.qbw.log.XLog;
import com.test.Test;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    protected EditText mEt;
    protected Button mBtn;
    protected EditText mEtAge;
    protected EditText mEtId;
    protected EditText mEtWeight;
    protected CheckBox mCb;

    //@SharedPreference
    String name;
    @SharedPreference
    int age;
    //@SharedPreference
    long id;
    //@SharedPreference
    float weight;
    //@SharedPreference
    boolean man;
    //@SharedPreference
    String[] data;

    private TestPreference mTestPreference = new TestPreference();

    private TestPreference.TestPreference1 mTestPreference1 = new TestPreference.TestPreference1();

    private Test mTest = new Test();

    public static class TestPreference {
        //@SharedPreference
        String testName;

        public static class TestPreference1 {
           // @SharedPreference
            int age;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        Map map = new HashMap();
        map.put("0", 1);
    }

    private void initView() {
        mEt = (EditText) findViewById(R.id.et);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.restore(MainActivity.this);//此处千万不要传入'this'
                //mEt.setText(name);
                mEtAge.setText(age + "");
                //mEtId.setText(id + "");
                //mEtWeight.setText(weight + "");
                //mCb.setChecked(man);

//                Preference.restore(mTestPreference);
//
//                Preference.restore(mTestPreference1);
//                XLog.v("mTestPreference1.age = " + mTestPreference1.age);
//
//                Preference.restore(mTest);
//                XLog.v("mTest.sex = %s", mTest.getSex());
            }
        });
        mEtAge = (EditText) findViewById(R.id.et_age);
        mEtId = (EditText) findViewById(R.id.et_id);
        mEtWeight = (EditText) findViewById(R.id.et_weight);
        mCb = (CheckBox) findViewById(R.id.cb);
        mCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                man = isChecked;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            name = mEt.getText().toString();
            age = Integer.parseInt(mEtAge.getText().toString());
//            id = Long.parseLong(mEtId.getText().toString());
//            weight = Float.parseFloat(mEtWeight.getText().toString());
//            data = new String[4];
//            data[0] = name;
//            data[1] = age + "";
//            data[2] = id + "";
//            data[3] = weight + "";
            Preference.save(this);

//            mTestPreference.testName = name;
//            Preference.save(mTestPreference);
//
//            mTestPreference1.age = age;
//            Preference.save(mTestPreference1);
//
//            mTest.setSex(mCb.isChecked() ? "boy" : "girl");
//            Preference.save(mTest);
        } catch (Exception e) {
            XLog.e(e);
        }

    }
}
