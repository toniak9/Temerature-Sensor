package com.example.tonia.Temperature;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;


public class Temperature extends Activity implements SensorEventListener {
    public boolean isCelcius = true;
    TextView temperature = null;
    TextView ambientTemp = null;
    private SensorManager sensor;
    TableLayout table = null;
    TableRow row = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Temperature Convertor");
        setContentView(R.layout.activity_temperature);
        table = (TableLayout) findViewById(R.id.tableLayout);
        Random rand = new Random();

        Sensor ambientTemperature;
        sensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        ambientTemperature = sensor.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        ambientTemp = (TextView) findViewById(R.id.ambientTemp);

        if(ambientTemperature == null)
            ambientTemp.setText("Ambient Temperature Not supported");

        for (int i = 1; i < table.getChildCount(); i++) {
            row = (TableRow) table.getChildAt(i);
            temperature = (TextView) row.getChildAt(1);
            double temp = rand.nextDouble() * 100;
            temperature.setText(Double.toString(temp));
        }
    }
        public void onClick(View v){
            TextView temp;
            TextView extension;
            TextView headerTemp;
            double changedTemp = 0.0;

            Button button = (Button) v;
            TableRow tableRow = (TableRow) v.getParent();
            temp = (TextView) tableRow.getChildAt(1);
            extension = (TextView) tableRow.getChildAt(2);

            //Covert to Fahrenhiet
            if(button.getText().toString().equalsIgnoreCase("To °F")) {
                isCelcius = false;
                button.setText("To °C");
                double temperature = Double.parseDouble(temp.getText().toString().trim());
                changedTemp = (temperature * 1.8) + 32;
                temp.setText(Double.toString(changedTemp));
                extension.setText("°F");

            }

            //Convert to Celcius
            else if(button.getText().toString().equalsIgnoreCase("To °C") ) {
                isCelcius = false;
                button.setText("To °F");
                double temperature = Double.parseDouble(temp.getText().toString().trim());
                changedTemp = (temperature - 32) / (1.8);
                temp.setText(Double.toString(changedTemp));
                extension.setText("°C");
            }
        }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float ambientTemperature = event.values[0];
        ambientTemp = (TextView) findViewById(R.id.ambientTemp);
        ambientTemp.setText(Float.toString(ambientTemperature));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_temperature, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
