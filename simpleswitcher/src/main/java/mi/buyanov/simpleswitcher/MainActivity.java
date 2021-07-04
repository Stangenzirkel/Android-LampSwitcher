package mi.buyanov.simpleswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;

    ImageButton btnPref;
    ImageButton btnReload;

    String address = "http://192.168.1.100:5000/";
    HomeServer homeServer = new HomeServer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btnPref = findViewById(R.id.btnReload);
        btnReload = findViewById(R.id.btnPref);
    }

    @Override
    public void onClick(View v) {
        class AsyncOnClick extends AsyncTask<String, String, String>{

            @Override
            protected String doInBackground(String[] objects) {
                try {
                    Log.d("doInBackground", "start of doInBackground");
                    Log.d("doInBackground", Integer.toString(v.getId()));
                    switch (v.getId()){
                        case R.id.btnReload: {
                            updateButtons();
                            break;
                        }
                        case R.id.btn1: {
                            homeServer.switchLamp(1);
                            updateButtons();
                            break;
                        }
                        case R.id.btn2: {
                            homeServer.switchLamp(2);
                            updateButtons();
                            break;
                        }
                        case R.id.btn3: {
                            homeServer.switchLamp(3);
                            updateButtons();
                            break;
                        }
                        case R.id.btn4: {
                            homeServer.switchLamp(4);
                            updateButtons();
                            break;
                        }
                        case R.id.btn5: {
                            homeServer.switchLamp(5);
                            updateButtons();
                            break;
                        }

                    }
                    Log.d("doInBackground", "end of doInBackground");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        new AsyncOnClick().execute();
    }

    private void updateButtons() {
        boolean[] lampsMode = new boolean[5];
        class AsyncUpdateButtons extends AsyncTask<String, String, String>{

            @Override
            protected String doInBackground(String[] objects) {
                try {
                    Log.d("doInBackground", "start of doInBackground");

                    boolean[] data = homeServer.lampMode();

                    lampsMode[0] = data[0];
                    lampsMode[1] = data[1];
                    lampsMode[2] = data[2];
                    lampsMode[3] = data[3];
                    lampsMode[4] = data[4];

                    Log.d("doInBackground", Arrays.toString(lampsMode));
                    Log.d("doInBackground", "end of doInBackground");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Log.d("onPostExecute", Arrays.toString(lampsMode));

                    btn1.setText(lampsMode[0] ? "on" : "off");
                    btn2.setText(lampsMode[1] ? "on" : "off");
                    btn3.setText(lampsMode[2] ? "on" : "off");
                    btn4.setText(lampsMode[3] ? "on" : "off");
                    btn5.setText(lampsMode[4] ? "on" : "off");

                    btn1.setEnabled(true);
                    btn2.setEnabled(true);
                    btn3.setEnabled(true);
                    btn4.setEnabled(true);
                    btn5.setEnabled(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                    btn5.setEnabled(false);
                }
            }
        }

        new AsyncUpdateButtons().execute();
    }
}