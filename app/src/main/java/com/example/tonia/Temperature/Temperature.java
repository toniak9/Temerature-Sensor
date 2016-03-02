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

/**
 * This app converts the temperature froom °C to °F and vice-versa.
 * This app displays the temperatures of fvr days in a week (Monday to Friday)
 * It also displays the ambient temperature for SDK versions from 19
 */

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

        //setting the layout for the activity
        setContentView(R.layout.activity_temperature);
        table = (TableLayout) findViewById(R.id.tableLayout);

        /**
         * rand() function is used to generate random temperatures for five days in a week
         * Temperatures are displayed from Monday to Friday in °C when the activity starts
         */
        Random rand = new Random();

        // Sensors are used to display the ambient temperature
        Sensor ambientTemperature;
        sensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        ambientTemperature = sensor.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        ambientTemp = (TextView) findViewById(R.id.ambientTemp);

        //check if ambient temperature feature is supported by the phone
        if(ambientTemperature == null)
            ambientTemp.setText("Ambient Temperature Not supported");

        /**
         * Here, a Table Layout is used to display the days along with corresponding temperatures
         * first row, row0 of the table is a header
         * So, the table is iterated to get each row and generate a random temperature
         * for that day and populate the TextView value with it
         */
        for (int i = 1; i < table.getChildCount(); i++) {
            row = (TableRow) table.getChildAt(i);
            //child 1 is the 2nd column, the temperature field
            temperature = (TextView) row.getChildAt(1);
            double temp = rand.nextDouble() * 100;
            temperature.setText(Double.toString(temp));
        }
    }

    /**
     *
     * @param v
     * onButtonClick, some even should happen.
     * Here, the teperature is converted from °C to °F or vice-vera
     */
        public void onClick(View v){
            TextView temp;
            TextView extension;
            double changedTemp = 0.0;

            Button button = (Button) v;
            //get the corresponding row of button click
            TableRow tableRow = (TableRow) v.getParent();
            temp = (TextView) tableRow.getChildAt(1);
            extension = (TextView) tableRow.getChildAt(2);

            //Covert to Fahrenhiet
            if(button.getText().toString().equalsIgnoreCase("To °F")) {
                isCelcius = false;
                button.setText("To °C");
                double temperature = Double.parseDouble(temp.getText().toString().trim());
                changedTemp = (temperature * 1.8) + 32;
                //update the temperature value (from °C to °F)
                temp.setText(Double.toString(changedTemp));
                extension.setText("°F");

            }

            //Convert to Celcius
            else if(button.getText().toString().equalsIgnoreCase("To °C") ) {
                isCelcius = false;
                button.setText("To °F");
                double temperature = Double.parseDouble(temp.getText().toString().trim());
                changedTemp = (temperature - 32) / (1.8);
                //update the temperature value (from °F to °C)
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
