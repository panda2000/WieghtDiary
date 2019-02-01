package ru.pandaprg.wieghtdiary.Measurement;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ru.pandaprg.wieghtdiary.Base.Data.Data;
import ru.pandaprg.wieghtdiary.R;

public class MeasurementActivity extends AppCompatActivity {

    private int step = 0;
    private TextView tvQuestion;
    private TextView tvUnit;
    private EditText etValue;
    private String [] quest;
    private String girth_unit;
    private String weight_unit;

    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageView imageView = (ImageView) findViewById(R.id.ivMeassurement);

        data = new Data(this);

        Resources res = getResources();
        quest = res.getStringArray(R.array.questions);
        final double []  values = new double[quest.length];
        girth_unit = res.getString(R.string.girth_unit);
        weight_unit = res.getString(R.string.weight_unit);
        step = 0;

        etValue = (EditText) findViewById(R.id.etValue);
        etValue.setText("");

        tvQuestion = (TextView) findViewById(R.id.tvWeight);
        tvUnit = (TextView) findViewById(R.id.tvUnit);

        tvQuestion.setText(quest[step]);
        tvUnit.setText(girth_unit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etValue.getText().length() != 0){
                    Log.d("LOG Mess","step  = " + step);
                    switch (step) {
                        case 0:
                            imageView.setImageResource(R.drawable.messurement2);
                            break;
                        case 1 :
                            imageView.setImageResource(R.drawable.messurement3);
                           break;
                        case 2:
                            imageView.setImageResource(R.drawable.messurement4);
                            break;
                        case 3 :
                            imageView.setImageResource(R.drawable.messurement5);
                            break;
                        case 4:
                            imageView.setImageResource(R.drawable.messurement6);
                            break;
                        case 5 :
                            imageView.setImageResource(R.drawable.messurement7);
                            break;
                    }



                values[step] = Double.parseDouble(etValue.getText().toString());
                etValue.setText("");
                //Если это последний вопрос
                if (step == quest.length-2){
                    tvUnit.setText(weight_unit);
                }
                if(step < quest.length-1) {
                    //добавляем в базу

                    // задаём следующий вопрос
                    step++;

                    tvQuestion.setText(quest[step]);



                } else {
                    // обнуляем для следующего раза
                    step = 0;

                    // добавляем в базу
                    // // TODO: 29.12.18 сделать добавление в базу
                    data.addRec(values);

                    // возвращаемся в предыдущее окно
                    Intent intent = new Intent();
                    intent.putExtra("Breast", values[0]);
                    intent.putExtra("UBreast",values[1]);
                    intent.putExtra("Waist", values[2]);
                    intent.putExtra("Belly", values[3]);
                    intent.putExtra("Thight", values[4]);
                    intent.putExtra("Leg", values[5]);
                    intent.putExtra("Weight", values[6]);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введи результат измерения...", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
