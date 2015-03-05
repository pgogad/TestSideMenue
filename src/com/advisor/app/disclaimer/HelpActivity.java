package com.advisor.app.disclaimer;

import com.advisor.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class HelpActivity extends Activity
{
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.help );

        TextView help = (TextView) findViewById(R.id.help_text_box);

        StringBuffer buff = new StringBuffer();
        buff.append("Helpful Hints\n\n")
        .append("In order to place a call, you must have an active PayPal account. We do not store your info. But you will need it to register.\n\n")
        .append("Once registered you can add funds.\n\n")
        .append("In order to place your call, you will need to deposit the minimum rate allowed for the app to connect you. Please see your advisor's per min rate.\n\n")
        .append("You may place your call as soon as you have added funds.\n\n")
        .append("Once your account is again at a 0 balance, the call will drop and you will have to repeat the process. If you chose to end the call and still have funds in your account, the balance will store for you to use for another time.\n\n")
        .append("If your advisor is on a call, you will be placed in priority sequence on hold.\n\n")
        .append("We hope you enjoy your call.\n\n");

        help.setText(buff.toString());
        help.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
        finish();
    }

}
