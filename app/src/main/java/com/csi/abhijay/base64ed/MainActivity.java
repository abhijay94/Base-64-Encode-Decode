/*Base 64 Encode Decode
Copyright 2015 Abhijay
Author: Abhijay Kumar*/
package com.csi.abhijay.base64ed;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends ActionBarActivity {
	Button btnEncode, btnDecode, btnCopy, btnPaste, share, clr;
	EditText Code;
	TextView Result;

	String input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnEncode = (Button) findViewById(R.id.button1);
		btnDecode = (Button) findViewById(R.id.button2);
		Code = (EditText) findViewById(R.id.editText1);
		Result = (TextView) findViewById(R.id.editText2);
		btnCopy = (Button) findViewById(R.id.copytoclip);
		btnPaste = (Button) findViewById(R.id.pastefromclip);
		share = (Button) findViewById(R.id.share);
		clr = (Button) findViewById(R.id.buttonclr);

		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Shares the coverted result via other apps
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = Result.getText().toString();
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Base64 Conversion Result:");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			}
		});

		btnCopy.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// Copies the text to clipboard
				String text = Result.getText().toString().trim();
				if (text.length() > 0) {
					if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
						android.text.ClipboardManager clipboardMgr = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
						clipboardMgr.setText(text);
					} else {
						// this api requires SDK version 11 and above, so
						// suppress warning for now
						android.content.ClipboardManager clipboardMgr = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
						ClipData clip = ClipData.newPlainText("Copied text",
								text);
						clipboardMgr.setPrimaryClip(clip);
						Toast.makeText(getApplicationContext(), "Text Copied",
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});

		btnPaste.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Pastes the content of the clipboard
				final ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

				Code.setText(clipBoard.getText());

				Toast.makeText(getApplicationContext(), "Text Pasted",
						Toast.LENGTH_SHORT).show();
			}
		});

		btnEncode.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Encode the Input
				input = Code.getText().toString();

				byte[] data;
				try {
					data = input.getBytes("UTF-8");
					String base64 = Base64.encodeToString(data, Base64.DEFAULT);
					Result.setText(base64);
				} catch (UnsupportedEncodingException e) {
					// Illegal Padding Exception in case of Base64
					Toast.makeText(getApplicationContext(), "Unsupported Text",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			}
		});

		btnDecode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Decodes the input
				input = Code.getText().toString();

				try {
					byte[] data = Base64.decode(input, Base64.DEFAULT);
					String text = new String(data, "UTF-8");
					Result.setText(text);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException ar) {
					ar.printStackTrace();
					// Illegal Padding Exception in case of Base64
					Toast.makeText(getApplicationContext(), "Unsupported Text",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		clr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Resets or Clears the EditText
				Code.setText("");

			}
		});

	}

}

