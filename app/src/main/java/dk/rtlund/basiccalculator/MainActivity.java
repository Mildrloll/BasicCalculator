package dk.rtlund.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    EditText editText;
    TextView textView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.resultLabel);
        button=findViewById(R.id.calc);
    }

    public void calc(View arg0){
        String dataInput=editText.getText().toString();

        ArrayList<String>data=new ArrayList<>();

        int lastAt=0;

        for(int i=0;i<dataInput.length();i++){
            if(dataInput.charAt(i)=='+'||dataInput.charAt(i)=='-'||dataInput.charAt(i)=='*'||dataInput.charAt(i)=='/'){
                data.add(dataInput.substring(lastAt,i));
                lastAt=i+1;
                data.add(dataInput.substring(i,i+1));
            }
        }

        data.add(dataInput.substring(lastAt));

        for (String string : data){
            Log.d("dataTest",string);
        }

        //handle *
        ArrayList<String> dataMultiplied = new ArrayList<>();
        boolean recentlyMultiplied = false;
        int indexOfLastMultiplication = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).contentEquals("*")){
                dataMultiplied.add(String.valueOf(Double.parseDouble(data.get(i-1))*Double.parseDouble(data.get(i+1))));
                indexOfLastMultiplication=i;
                recentlyMultiplied=true;
            } else if (indexOfLastMultiplication!=0){
                indexOfLastMultiplication=0;
                continue;
            } else if (i<data.size()-1 && !data.get(i+1).contentEquals("*")) {
                dataMultiplied.add(data.get(i));
                recentlyMultiplied=false;
            }
        }

        if (!recentlyMultiplied){
            dataMultiplied.add(data.get(data.size()-1));
        }

        Log.d("dataTest","----------------------");
        for (String string : dataMultiplied){
            Log.d("dataTest",string);
        }

        //handle + and -
        double result=0;

        for(int i=0;i<data.size();i++) {
            if (i!=0){
                if(data.get(i).contentEquals("+")){
                    result+=Double.parseDouble(data.get(i+1));
                }else if(data.get(i).contentEquals("-")){
                    result-=Double.parseDouble(data.get(i+1));
                }
            }else{
                result=Double.parseDouble(data.get(i));
            }
        }

        textView.setText(String.valueOf(result));
    }
}
