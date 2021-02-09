package com.example.robofutebol;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView lvPartidas1;
	private ArrayAdapter<String> adppartidas;
	private ArrayAdapter mAdapter;

	@SuppressWarnings("deprecation")
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		PartidasDAO part = new PartidasDAO();
		ArrayList<String> lista = part.listar();
		lvPartidas1 = (ListView) findViewById(R.id.lvPartidas);
		
		mAdapter = new ArrayAdapter<String>(this, R.layout.lista_customizada,
				lista) {
			public View getView(int position, View convertView, ViewGroup parent) {
				// Cast the list view each item as text view
				TextView item = (TextView) super.getView(position, convertView,
						parent);

				String text = item.getText().toString();
				String match = " min  ";
				int posicaotexto = text.indexOf(match);

				final SpannableStringBuilder sb = new SpannableStringBuilder(
						item.getText().toString());
				Bitmap emotion = BitmapFactory.decodeResource(getResources(),
						R.drawable.cronometro);
				ImageSpan spanImg = new ImageSpan(emotion,
						ImageSpan.ALIGN_BASELINE);
				sb.setSpan(spanImg, posicaotexto + 5, posicaotexto + 6,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

				match = "\n";
				posicaotexto = text.indexOf(match);

				emotion = BitmapFactory.decodeResource(getResources(),
						R.drawable.corner);
				spanImg = new ImageSpan(emotion, ImageSpan.ALIGN_BASELINE);
				sb.setSpan(spanImg, posicaotexto + 1, posicaotexto + 2,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

				emotion = BitmapFactory.decodeResource(getResources(),
						R.drawable.shot);
				spanImg = new ImageSpan(emotion, ImageSpan.ALIGN_BASELINE);
				sb.setSpan(spanImg, posicaotexto + 5, posicaotexto + 6,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				
				emotion = BitmapFactory.decodeResource(getResources(),
						R.drawable.defesas);
				spanImg = new ImageSpan(emotion, ImageSpan.ALIGN_BASELINE);
				sb.setSpan(spanImg, posicaotexto + 9, posicaotexto + 10,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

				item.setText(sb);

				// Set the list view item's text color
				item.setTextColor(Color.parseColor("#FF3E80F1"));
				// return the view
				return item;
			}
		};

		lvPartidas1.setAdapter(mAdapter);

		new CountDownTimer(99999999, 240000) {
			public void onTick(long millisUntilFinished) {
				updateData();
			}

			public void onFinish() {
				updateData();
			}
		}.start();
	}

	@SuppressLint("NewApi")
	private void updateData() {
		PartidasDAO part = new PartidasDAO();
		ArrayList<String> lista = part.listar();

		mAdapter.clear();
		mAdapter.addAll(lista);
		mAdapter.notifyDataSetChanged();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
