package com.qbw.annotation.preference;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.qbw.annotation.Preference;
import com.qbw.annotation.preference.core.IHost;
import com.qbw.log.XLog;
import com.test.Test;

public class MainActivity extends Activity {

    protected EditText mEt;
    protected Button mBtn;
    protected EditText mEtAge;
    protected EditText mEtId;
    protected EditText mEtWeight;
    protected CheckBox mCb;

    @SharedPreference
    String name;
    @SharedPreference
    int age;
    @SharedPreference
    long id;
    @SharedPreference
    float weight;
    @SharedPreference
    boolean man;

    private TestPreference mTestPreference = new TestPreference();

    private TestPreference.TestPreference1 mTestPreference1 = new TestPreference.TestPreference1();

    private Test mTest = new Test();

    public static class TestPreference implements IHost {
        @SharedPreference
        String test1;

        public static class TestPreference1 implements IHost {
            @SharedPreference
            int test2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEt = (EditText) findViewById(R.id.et);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.restore(MainActivity.this);//此处千万不要传入'this'
                mEt.setText(name);
                mEtAge.setText(age + "");
                mEtId.setText(id + "");
                mEtWeight.setText(weight + "");
                mCb.setChecked(man);

                Preference.restore(mTestPreference);
                XLog.v("mTestPreference.test1 = %s", mTestPreference.test1);

                Preference.restore(mTestPreference1);
                XLog.v("mTestPreference1.test2 = %d", mTestPreference1.test2);

                Preference.restore(mTest);
                XLog.v("mTest.sex = %s", mTest.getSex());
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
        name = mEt.getText().toString();
        String strAge = mEtAge.getText().toString();
        if (!TextUtils.isEmpty(strAge)) {
            age = Integer.parseInt(strAge);
        }
        String strId = mEtId.getText().toString();
        if (!TextUtils.isEmpty(strId)) {
            id = Long.parseLong(strId);
        }
        String strWeight = mEtWeight.getText().toString();
        if (!TextUtils.isEmpty(strWeight)) {
            weight = Float.parseFloat(strWeight);
        }

        Preference.save(this);

        mTestPreference.test1 = name;
        Preference.save(mTestPreference);

        mTestPreference1.test2 = age;
        Preference.save(mTestPreference1);

        mTest.setSex(mCb.isChecked() ? "boy" : "girl");
        Preference.save(mTest);

        Preference.clear(mTestPreference);
        //Preference.clear(this);
        //Preference.clearAll();
    }
}
