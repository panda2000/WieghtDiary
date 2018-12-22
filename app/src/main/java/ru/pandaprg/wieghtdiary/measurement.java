package ru.pandaprg.wieghtdiary;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class measurement extends AppCompatActivity {

    private int step = 0;
    private TextView tvQuestion;
    private TextView tvUnit;
    private EditText etValue;
    private String [] quest;
    private String girth_unit;
    private String weight_unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Resources res = getResources();
        quest = res.getStringArray(R.array.questions);
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

                    // возвращаемся в главное окно
                    Intent intent = new Intent(measurement.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
