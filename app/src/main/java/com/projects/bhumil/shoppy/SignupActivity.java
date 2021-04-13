package com.projects.bhumil.shoppy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.bhumil.shoppy.UtilityClasses.Utility;
import com.projects.bhumil.shoppy.UtilityClasses.Validations;

public class SignupActivity extends AppCompatActivity {

    TextView tvLogin;
    private  long mBackPressedTime;
    private  int counter = 0;
    private  Intent mIntent;
    TextView tvSignup;
    EditText etMobile, etPassword, etUserName, etEmail;
    public SharedPreferences mSharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tvSignup = (TextView)findViewById(R.id.tv_sup);
        tvLogin = (TextView)findViewById(R.id.tv_have_account);
        etMobile = (EditText)findViewById(R.id.et_mobphone);
        etPassword = (EditText)findViewById(R.id.et_pswrdd);
        etUserName = (EditText)findViewById(R.id.et_usrname);
        etEmail = (EditText)findViewById(R.id.et_email);
        mSharedPreferences1 = Utility.getPreference(SignupActivity.this);

        tvLogin.setOnClickListener(new View.OnClickListener()
        {
            //Already have an account-Login clicked
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(it);
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String Username = etUserName.getText().toString();
                String Email = etEmail.getText().toString();
                String Mobile = etMobile.getText().toString();
                String Password = etPassword.getText().toString();
//                if(Email.equals("root") || Password.equals("root"))
//                {
//                    SharedPreferences.Editor editor = mSharedPreferences1.edit();
//                    editor.putString(Utility.USEREMAIL, "root");
//                    editor.putString(Utility.USERNAME, "root");
//                    editor.putString(Utility.USERPASSWORD, "root");
//                    editor.commit();
//
//                    Intent homeActivity = new Intent(SignupActivity.this, MainActivity.class);
//                    startActivity(homeActivity);
//                }
//                else
//                {
                    Validations userData = new Validations();
                    int validated = userData.ValidateSignUp(Username.trim(), Email.trim(), Mobile.trim(), Password.trim());
                    if(validated == Validations.VALID_DATA)
                    {
                        SharedPreferences.Editor editor = mSharedPreferences1.edit();
                        editor.putString(Utility.USEREMAIL, Email.trim());
                        editor.putString(Utility.USERNAME, Username.trim());
                        editor.putString(Utility.USERPASSWORD, Password.trim());
                        editor.commit();
                        Toast.makeText(SignupActivity.this,getString(R.string.st_signup_success), Toast.LENGTH_SHORT).show();
                        Intent homeActivity = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(homeActivity);
                    }
                    else if(validated == Validations.INVALID_USERNAME)
                    {
                        etUserName.setError(getString(R.string.st_blank_text));
                    }
                    else if(validated == Validations.INVALID_EMAIL)
                    {
                        etEmail.setError(getString(R.string.st_invalid_email));
                    }
                    else if(validated == Validations.INVALID_MOBILE)
                    {
                        etMobile.setError(getString(R.string.st_invalid_mobile));
                    }
                    else if(validated == Validations.INVALID_PASSWORD)
                    {
                        etPassword.setError(getString(R.string.st_invalid_password));
                    }
                //}
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        mBackPressedTime = System.currentTimeMillis();
        if(counter == 0)
        {
            Toast.makeText(SignupActivity.this, getString(R.string.st_back_pressed), Toast.LENGTH_SHORT).show();
            counter = 1;
        }
        else
        {
            if (mBackPressedTime + 5000 > System.currentTimeMillis())
            {
                mIntent = new Intent(Intent.ACTION_MAIN);
                mIntent.addCategory(Intent.CATEGORY_HOME);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                finish();
                super.onBackPressed();

            } else
            {
                Toast.makeText(SignupActivity.this, getString(R.string.st_back_pressed), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
