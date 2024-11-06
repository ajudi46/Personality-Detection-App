package com.example.pythontest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void operations(View v) throws InterruptedException, ExecutionException {
        final Button se = findViewById(R.id.search_buttoned);
        final TextView ter = findViewById(R.id.bigvr);
        final Button button = findViewById(R.id.button);
        final TextView textView = findViewById(R.id.text1);
        final EditText editText = findViewById(R.id.enter);
        final TextView traitdesc = findViewById(R.id.textDesc);
        final TextView traitexp = findViewById(R.id.textqualities);
        final TextView ieall = findViewById(R.id.IE_desc);
        final TextView nsall = findViewById(R.id.NS_desc);
        final TextView ftall = findViewById(R.id.FT_desc);
        final TextView pjall = findViewById(R.id.PJ_desc);
        final TextView ietext = findViewById(R.id.IE_text);
        final PieChart pieChartie = findViewById(R.id.pie_chart_IE);
        final TextView nstext = findViewById(R.id.NS_text);
        final PieChart pieChartns = findViewById(R.id.pie_chart_NS);
        final TextView fttext = findViewById(R.id.FT_text);
        final PieChart pieChartft = findViewById(R.id.pie_chart_FT);
        final TextView pjtext = findViewById(R.id.PJ_text);
        final PieChart pieChartpj = findViewById(R.id.pie_chart_PJ);
        final CardView cardView  = findViewById(R.id.cardistan);
        final String tempvalued = editText.getText().toString();
        final BarChart bigvbar = findViewById(R.id.bigv_bargraph);
        final TextView bigvexp = findViewById(R.id.bigvexp);
        final TextView bigvper = findViewById(R.id.bigvper);

        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);

        if(!tempvalued.equals("Enter username")){

            new MyAsyncTask(cardView ,bigvexp ,bigvper,bigvbar, ter , MainActivity.this,se,button,textView,editText,traitdesc,traitexp,ieall,nsall,ftall,pjall,ietext,pieChartie,nstext,pieChartns,fttext,pieChartft,pjtext,pieChartpj).execute(tempvalued);

                se.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText.setVisibility(View.VISIBLE);
                        button.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.GONE);
                        ietext.setVisibility(GONE);
                        pieChartie.setVisibility(GONE);
                        nstext.setVisibility(GONE);
                        pieChartns.setVisibility(GONE);
                        fttext.setVisibility(GONE);
                        pieChartft.setVisibility(GONE);
                        pjtext.setVisibility(GONE);
                        pieChartpj.setVisibility(GONE);
                        traitdesc.setVisibility(GONE);
                        traitexp.setVisibility(GONE);
                        se.setVisibility(GONE);

                        pieChartie.clearAnimation();
                        pieChartft.clearAnimation();
                        pieChartns.clearAnimation();
                        pieChartpj.clearAnimation();
                        pieChartie.clear();
                        pieChartft.clear();
                        pieChartns.clear();
                        pieChartpj.clear();
                        button.setEnabled(true);

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });
            }
        }
    }

class MyAsyncTask extends AsyncTask<String,Integer,String>{


    ProgressDialog pd;
    Context context;
    PyObject objectd;
    PyObject bigv;
    Button se;
    Button button;
    TextView textView;
    EditText editText;
    TextView traitdesc;
    TextView traitexp;
    TextView ieall;
    TextView nsall;
    TextView ftall;
    TextView pjall;
    TextView ietext;
    PieChart pieChartie;
    TextView nstext;
    PieChart pieChartns;
    TextView fttext;
    PieChart pieChartft;
    TextView pjtext;
    PieChart pieChartpj;
    CardView cardView;
    TextView ter;
    BarChart bigvbar;
    TextView bigvexp;
    TextView bigvper;



    MyAsyncTask(CardView cardView , TextView bigvexp , TextView bigvper , BarChart bigvbar , TextView ter , Context contexted,Button se , Button button , TextView textView , EditText editText , TextView traitdesc , TextView traitexp , TextView ieall , TextView nsall,TextView ftall , TextView pjall  , TextView ietext , PieChart pieChartie , TextView nstext , PieChart pieChartns ,TextView fttext , PieChart pieChartft ,TextView pjtext , PieChart pieChartpj ) {
        this.context = contexted;
        this.cardView = cardView;
        this.bigvbar = bigvbar;
        this.bigvexp = bigvexp;
        this.bigvper = bigvper;
        //this.texted = textup;
        this.ter = ter;
        this.se = se;
        this.button = button;
        this.textView = textView;
        this.editText = editText;
        this.traitdesc = traitdesc;
        this.traitexp = traitexp;
        this.ieall = ieall;
        this.nsall = nsall;
        this.ftall = ftall;
        this.pjall = pjall;
        this.ietext = ietext;
        this.pieChartie = pieChartie;
        this.nstext = nstext;
        this.pieChartns = pieChartns;
        this.fttext = fttext;
        this.pieChartft = pieChartft;
        this.pjtext = pjtext;
        this.pieChartpj = pieChartpj;


    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setMessage("This May Take Some Time");
        pd.setTitle("Loading Tweet Engine");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.setMax(100);
        pd.show();

    }

    @Override
    protected String doInBackground(String... strings) {
        String og = strings[0];
        PyObject object;
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(context));
            Python py = Python.getInstance();
            PyObject pyf = py.getModule("myscript");
            objectd = pyf.callAttr("get_tweets",og);
            bigv = pyf.callAttr("get_tweets_bigv",og);
        }
        else {
            Python py = Python.getInstance();
            PyObject pyf = py.getModule("myscript");
            objectd = pyf.callAttr("get_tweets",og);
            bigv = pyf.callAttr("get_tweets_bigv",og);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        pd.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        //texted.setText(object.toString());

        String returnlist = objectd.toString();
        String bigvreturn = bigv.toString();

        bigvreturn = bigvreturn.replaceAll(",","");
        bigvreturn = bigvreturn.replaceAll("'","");
        bigvreturn = bigvreturn.replaceAll(":","");
        bigvreturn = bigvreturn.substring(1,(bigvreturn.length()-1));

        String split_bigv[] = new String[11];
        split_bigv = bigvreturn.split(" ");

        for(int i=0 ; i< split_bigv.length ; i++){
            if(i == 1 || i ==3 || i==5 || i==7 || i==9 ){
                Double temp = Double.parseDouble(split_bigv[i]);
                temp = (double) Math.round(temp*100)/100;
                split_bigv[i] = Double.toString(temp);
            }
            System.out.println(split_bigv[i]);
        }

        // Write Test File
        returnlist = returnlist.replaceAll(",","");
        returnlist = returnlist.replaceAll("'","");
        returnlist = returnlist.replaceAll(":","");
        returnlist = returnlist.substring(1,(returnlist.length()-1));

        String split_percentage[] = new String[17];
        split_percentage = returnlist.split(" ");
        String finalresult = new String();
        for (int i=0;i<split_percentage.length;i++)
            if(i%2 == 1)
                System.out.println(split_percentage[i]);

        if (Float.parseFloat(split_percentage[1]) > Float.parseFloat(split_percentage[3]) )
            finalresult += "I";
        else
            finalresult += "E";

        if (Float.parseFloat(split_percentage[9]) > Float.parseFloat(split_percentage[11]) )
            finalresult += "N";
        else
            finalresult += "S";

        if (Float.parseFloat(split_percentage[5]) > Float.parseFloat(split_percentage[7]) )
            finalresult += "F";
        else
            finalresult += "T";

        if (Float.parseFloat(split_percentage[13]) > Float.parseFloat(split_percentage[15]) )
            finalresult += "P";
        else
            finalresult += "J";

        //Traits Qualities and Description
        if(finalresult.equals("ENTJ")){
            traitdesc.setText("The Commander");
            traitexp.setText("Strategic leaders, motivated to organize change");
        }
        if(finalresult.equals("INTJ")){
            traitdesc.setText("The Mastermind");
            traitexp.setText("Analytical problem-solvers, eager to improve systems and processes");
        }
        if(finalresult.equals("ENTP")){
            traitdesc.setText("The Visionary");
            traitexp.setText("Inspired innovators, seeking new solutions to challenging problems");
        }
        if(finalresult.equals("INTP")){
            traitdesc.setText("The Architect");
            traitexp.setText("Philosophical innovators, fascinated by logical analysis");
        }
        if(finalresult.equals("ENFJ")){
            traitdesc.setText("The Teacher");
            traitexp.setText("Idealist organizers, driven to do what is best for humanity");
        }
        if(finalresult.equals("INFJ")){
            traitdesc.setText("The Counselor");
            traitexp.setText("Creative nurturers, driven by a strong sense of personal integrity");
        }
        if(finalresult.equals("ENFP")){
            traitdesc.setText("The Champion");
            traitexp.setText("People-centered creators, motivated by possibilities and potential");
        }
        if(finalresult.equals("INFP")){
            traitdesc.setText("The Healer");
            traitexp.setText("Imaginative idealists, guided by their own values and beliefs");
        }
        if(finalresult.equals("ESTJ")){
            traitdesc.setText("The Supervisor");
            traitexp.setText("Hardworking traditionalists, taking charge to get things done");
        }
        if(finalresult.equals("ISTJ")){
            traitdesc.setText("The Inspector");
            traitexp.setText("Responsible organizers, driven to create order out of chaos");
        }
        if(finalresult.equals("ESFJ")){
            traitdesc.setText("The Provider");
            traitexp.setText("Conscientious helpers, dedicated to their duties to others");
        }
        if(finalresult.equals("ISFJ")){
            traitdesc.setText("The Protector");
            traitexp.setText("Industrious caretakers, loyal to traditions and institutions");
        }
        if(finalresult.equals("ESTP")){
            traitdesc.setText("The Dynamo");
            traitexp.setText("Energetic thrillseekers, ready to push boundaries and dive into action");
        }
        if(finalresult.equals("ISTP")){
            traitdesc.setText("The Craftsperson");
            traitexp.setText("Observant troubleshooters, solving practical problems");
        }
        if(finalresult.equals("ESFP")){
            traitdesc.setText("The Entertainer");
            traitexp.setText("Vivacious entertainers, loving life and charming those around them");
        }
        if(finalresult.equals("ISFP")){
            traitdesc.setText("The Composer");
            traitexp.setText("Gentle caretakers, enjoying the moment with low-key enthusiasm");
        }

        //BAR GRAPH BIGV

        bigvbar.setVisibility(View.VISIBLE);

        ArrayList<BarEntry> bare = new ArrayList<>();
        ArrayList<SampleBarData> barlists = new ArrayList<>();
        ArrayList<String> Labelname = new ArrayList<>();
        barlists.add(new SampleBarData(split_bigv[0],(float) Double.parseDouble(split_bigv[1])));
        barlists.add(new SampleBarData(split_bigv[2],(float) Double.parseDouble(split_bigv[3])));
        barlists.add(new SampleBarData(split_bigv[4],(float) Double.parseDouble(split_bigv[5])));
        barlists.add(new SampleBarData(split_bigv[6],(float) Double.parseDouble(split_bigv[7])));
        barlists.add(new SampleBarData(split_bigv[8],(float) Double.parseDouble(split_bigv[9])));


        for(int i=0;i<barlists.size();i++){
            String trait = barlists.get(i).getTrait();
            float val = barlists.get(i).getTraitval();
            bare.add(new BarEntry(i,val));
            Labelname.add(trait);
        }
        BarDataSet barDataSet = new BarDataSet(bare,"BIG V Trait Points");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData barData = new BarData(barDataSet);
        bigvbar.setData(barData);

        XAxis xAxis = bigvbar.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(Labelname));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(Labelname.size());
        xAxis.setLabelRotationAngle(270);

        bigvbar.animateXY(2000,2000);


        //  I VS E PIE CHART

        ietext.setVisibility(View.VISIBLE);
        pieChartie.setVisibility(View.VISIBLE);
        pieChartie.setUsePercentValues(true);
        Description desc = new Description();
        desc.setText("Introversion Vs Extroversion");
        desc.setTextAlign(Paint.Align.LEFT);
        desc.setTextSize(16f);
        pieChartie.setDescription(desc);
        pieChartie.setHoleColor(25);
        pieChartie.setTransparentCircleRadius(25);
        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry(Float.parseFloat(split_percentage[1]),split_percentage[0]));
        value.add(new PieEntry(Float.parseFloat(split_percentage[3]),split_percentage[2]));

        PieDataSet pieDataSet = new PieDataSet(value,"Energy");
        PieData pieData = new PieData(pieDataSet);
        pieChartie.setData(pieData);
        pieDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        pieChartie.animateXY(1800,1800);
        pieChartie.invalidate();

        //  N Vs S PIE CHART

        nstext.setVisibility(View.VISIBLE);
        pieChartns.setVisibility(View.VISIBLE);
        pieChartns.setUsePercentValues(true);
        Description desc1 = new Description();
        desc.setText("Intuition Vs Sensing");
        desc1.setTextAlign(Paint.Align.LEFT);
        desc1.setTextSize(16f);
        pieChartns.setDescription(desc1);
        pieChartns.setHoleColor(25);
        pieChartns.setTransparentCircleRadius(25);
        List<PieEntry> value1 = new ArrayList<>();
        value1.add(new PieEntry(Float.parseFloat(split_percentage[9]),split_percentage[8]));
        value1.add(new PieEntry(Float.parseFloat(split_percentage[11]),split_percentage[10]));

        PieDataSet pieDataSet1 = new PieDataSet(value1,"Information");
        PieData pieData1 = new PieData(pieDataSet1);
        pieChartns.setData(pieData1);
        pieDataSet1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChartns.animateXY(1800,1800);
        pieChartns.invalidate();

        // F VS T PIE CHART

        fttext.setVisibility(View.VISIBLE);
        pieChartft.setVisibility(View.VISIBLE);
        pieChartft.setUsePercentValues(true);
        Description desc2 = new Description();
        desc2.setText("Feeling Vs Thinking");
        desc2.setTextAlign(Paint.Align.LEFT);
        desc2.setTextSize(16f);
        pieChartft.setDescription(desc2);
        pieChartft.setHoleColor(25);
        pieChartft.setTransparentCircleRadius(25);
        List<PieEntry> value2 = new ArrayList<>();
        value2.add(new PieEntry(Float.parseFloat(split_percentage[5]),split_percentage[4]));
        value2.add(new PieEntry(Float.parseFloat(split_percentage[7]),split_percentage[6]));

        PieDataSet pieDataSet2 = new PieDataSet(value2,"Decisions");
        PieData pieData2 = new PieData(pieDataSet2);
        pieChartft.setData(pieData2);
        pieDataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        pieChartft.animateXY(1800,1800);
        pieChartft.invalidate();

        // P VS J PIE CHART

        pjtext.setVisibility(View.VISIBLE);
        pieChartpj.setVisibility(View.VISIBLE);
        pieChartpj.setUsePercentValues(true);
        Description desc3 = new Description();
        desc3.setText("Percieving Vs Judging");
        desc3.setTextAlign(Paint.Align.LEFT);
        desc3.setTextSize(16f);
        pieChartpj.setDescription(desc3);
        pieChartpj.setHoleColor(25);
        pieChartpj.setTransparentCircleRadius(25);
        List<PieEntry> value3 = new ArrayList<>();
        value3.add(new PieEntry(Float.parseFloat(split_percentage[13]),split_percentage[12]));
        value3.add(new PieEntry(Float.parseFloat(split_percentage[15]),split_percentage[14]));

        PieDataSet pieDataSet3 = new PieDataSet(value3,"Organization");
        PieData pieData3 = new PieData(pieDataSet3);
        pieChartpj.setData(pieData3);
        pieDataSet3.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChartpj.animateXY(1800,1800);
        pieChartpj.invalidate();

        //Writing Test Files
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(returnlist);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        //Setting Views Straight
        bigvexp.setVisibility(View.VISIBLE);
        bigvper.setVisibility(View.VISIBLE);
        editText.setVisibility(GONE);
        button.setVisibility(GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(finalresult);
        traitdesc.setVisibility(View.VISIBLE);
        traitexp.setVisibility(View.VISIBLE);
        ieall.setVisibility(View.VISIBLE);
        nsall.setVisibility(View.VISIBLE);
        ftall.setVisibility(View.VISIBLE);
        pjall.setVisibility(View.VISIBLE);
        ter.setVisibility(View.VISIBLE);
        se.setVisibility(View.VISIBLE);
        cardView.setVisibility(GONE);

        Toast.makeText(context, "CAN RUN ONLY ONE INSTANCE OF CHAQUOPY",Toast.LENGTH_LONG).show();

        pd.dismiss();


    }
}


