package com.qbw.annotation.preference;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.qbw.log.XLog;
import com.qbw.preference.Preference;
import com.test.Test;

public class MainActivity extends Activity {

    protected EditText mEt;
    protected Button mBtn;
    protected EditText mEtAge;
    protected EditText mEtId;
    protected EditText mEtWeight;
    protected CheckBox mCb;

    /**
     * 使用限制：
     * 1.有时候需要手动rebuild一下才会生成文件（一般都是自动的不需要，而且运行的时候也会去自动生成）
     * 2.没有列出了的变量类型不支持
     * 3.不可以在类名相同的类中使用，这样导致Preference.java中会生成函数名一样的函数
     */

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

    public static class TestPreference {
        @SharedPreference
        String test1;

        public static class TestPreference1 {
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
                Preference.MainActivity().restore(MainActivity.this);
                mEt.setText(name);
                mEtAge.setText(age + "");
                mEtId.setText(id + "");
                mEtWeight.setText(weight + "");
                mCb.setChecked(man);

                Preference.MainActivityTestPreference().restore(mTestPreference);
                XLog.v("mTestPreference.test1 = %s", mTestPreference.test1);

                Preference.MainActivityTestPreferenceTestPreference1().restore(mTestPreference1);
                XLog.v("mTestPreference1.test2 = %d", mTestPreference1.test2);

                Preference.Test().restore(mTest);
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

        Preference.MainActivity().save(this);

        mTestPreference.test1 = name;
        Preference.MainActivityTestPreference().save(mTestPreference);

        mTestPreference1.test2 = age;
        Preference.MainActivityTestPreferenceTestPreference1().save(mTestPreference1);

        mTest.setSex(mCb.isChecked() ? "boy" : "girl");
        Preference.Test().save(mTest);

        //Preference.clear(mTestPreference);
        //Preference.clear(this);
        Preference.MainActivity().remove(this);
        //Preference.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
