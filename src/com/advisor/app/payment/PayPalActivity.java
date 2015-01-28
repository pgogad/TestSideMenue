package com.advisor.app.payment;

import java.math.BigDecimal;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.advisor.app.R;
import com.advisor.app.db.AdvisorDB;
import com.advisor.app.phone.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class PayPalActivity extends Activity
{
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
	private static final String CONFIG_CLIENT_ID = "AboZuBA-iaS7l3ii-ZyDTdkEdO5Eas9BCycr_HTiZ1-uTjICrUVs4mWARVG7";
	private static final int REQUEST_CODE_PAYMENT = 1;
	private String[] mins = { "", "", "" };

	private AdvisorDB dataBase;

	private SharedPreferences sharedpreferences;

	private RadioGroup amountRadio;
	private EditText amountText;

	private AsyncHttpClient client = new AsyncHttpClient();
	private static PayPalConfiguration config = new PayPalConfiguration().environment( CONFIG_ENVIRONMENT ).clientId( CONFIG_CLIENT_ID );

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.pay_pal );

		sharedpreferences = getSharedPreferences( "TheAdvisorApp_PP", Context.MODE_PRIVATE );

		amountRadio = (RadioGroup) findViewById( R.id.amount_options );

		TextView intro = (TextView) findViewById( R.id.textView_paypal );
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics( metrics );
		intro.setWidth( (int) metrics.widthPixels / 2 );
		intro.setHeight( 100 );
		intro.setText( "Other Amount : " );
		intro.setGravity( Gravity.LEFT );

		amountText = (EditText) findViewById( R.id.custom_amount_text );
		amountText.setGravity( Gravity.LEFT );
		amountText.setText( "0.00" );
		amountText.setWidth( (int) metrics.widthPixels / 2 );

		Intent intent = new Intent( PayPalActivity.this, PayPalService.class );
		intent.putExtra( PayPalService.EXTRA_PAYPAL_CONFIGURATION, config );
		startService( intent );

		dataBase = new AdvisorDB( this );
	}

	public void onBuyPressed( View pressed )
	{
		int id = pressed.getId();
		id = amountRadio.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton) findViewById( id );

		String temp = (String) radioButton.getText();
		String amount = null;
		int i = temp.indexOf( "$" );
		if( i == 0 )
		{
			amount = temp.substring( i + 1 );
		}
		String customAmt = String.valueOf( amountText.getText() );

		if( null != customAmt && customAmt.length() > 0 )
		{
			if( !customAmt.equalsIgnoreCase( "0.00" ) )
				amount = customAmt;
		}

		BigDecimal total = new BigDecimal( amount.trim() ).setScale( 5, BigDecimal.ROUND_FLOOR );

		mins[0] = total.toString();
		mins[1] = "0";
		mins[2] = "0";

		PayPalPayment thingToBuy = new PayPalPayment( total, "USD", "Adviser Fees", PayPalPayment.PAYMENT_INTENT_SALE );
		Intent intent = new Intent( PayPalActivity.this, PaymentActivity.class );
		intent.putExtra( PaymentActivity.EXTRA_PAYMENT, thingToBuy );
		startActivityForResult( intent, REQUEST_CODE_PAYMENT );
		overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
		amountText.setText( "0.00" );
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		if( requestCode == REQUEST_CODE_PAYMENT )
		{
			if( resultCode == Activity.RESULT_OK )
			{
				PaymentConfirmation confirm = data.getParcelableExtra( PaymentActivity.EXTRA_RESULT_CONFIRMATION );
				if( confirm != null )
				{
					try
					{
						Log.i( "Approval", confirm.toJSONObject().toString() );

						Editor editor = sharedpreferences.edit();
						editor.putString( Constants.SHARED_PREF_APP_NAME, confirm.toJSONObject().toString() );
						editor.commit();

						BigDecimal bd = dataBase.getAvailableMinutes();
						bd = bd.add( new BigDecimal( mins[0] ).setScale( 5, BigDecimal.ROUND_FLOOR ) );
						dataBase.insertRecord( bd.setScale( 5, BigDecimal.ROUND_FLOOR ).toString(), Long.valueOf( "0" ).longValue(), Long.valueOf( "0" )
								.longValue() );

//						client.addHeader("content-type", "application/json");
//						String url = "http://dry-dusk-8611.herokuapp.com/paypalapproval/" + URLEncoder.encode( confirm.toJSONObject().toString(), "UTF-8" );
//						client.get( this.getApplicationContext(), url, new AsyncHttpResponseHandler()
//						{
//							@Override
//							public void onSuccess( String response )
//							{
//								Log.d( "HTTP", "onSuccess: " + response );
//							}
//						} );

						Toast.makeText( getApplicationContext(), "Payment processed Successfully!!", Toast.LENGTH_LONG ).show();
						overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
						finish();
					}
					catch( Exception e )
					{
						Log.e( "PayPalActivity", "an extremely unlikely failure occurred: ", e );
						Toast.makeText( getApplicationContext(), "Problem occured while processing payment!!", Toast.LENGTH_LONG ).show();
					}
				}
			}
			else if( resultCode == Activity.RESULT_CANCELED )
			{
				Log.i( "paymentExample", "The user canceled." );
			}
			else if( resultCode == PaymentActivity.RESULT_EXTRAS_INVALID )
			{
				Log.i( "paymentExample", "An invalid Payment was submitted. Please see the docs." );
			}
		}
		else if( resultCode == Activity.RESULT_CANCELED )
		{
			Log.i( "FuturePaymentExample", "The user canceled." );
		}
		else if( resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID )
		{
			Log.i( "FuturePaymentExample",
					"Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs." );
		}
	}

	@Override
	public void onDestroy()
	{
		stopService( new Intent( this, PayPalService.class ) );
		super.onDestroy();
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
		finish();
	}
}