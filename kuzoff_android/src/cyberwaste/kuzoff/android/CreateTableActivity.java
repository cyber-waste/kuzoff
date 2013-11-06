package cyberwaste.kuzoff.android;

import com.example.quickstart.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;

public class CreateTableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table, menu);
        return true;
    }
    
    public void onRadioClick(View v){
        switch (v.getId()) {
            case R.id.radioUnion:
                ((EditText)findViewById(R.id.table2)).setEnabled(true);
                break;
            case R.id.radioDifference:
                ((EditText)findViewById(R.id.table2)).setEnabled(true);
                break;
            case R.id.radioUnique:
                ((EditText)findViewById(R.id.table2)).setEnabled(false);
                break;
            default:
                break;
        }
    }
    
    public void onOkClick(View v){
        Intent answerInent = new Intent();
        
        String table1 = ((EditText)findViewById(R.id.table1)).getText().toString();
        String resultTable = ((EditText)findViewById(R.id.result_table)).getText().toString();
        answerInent.putExtra(MainActivity.TABLE1, table1);
        answerInent.putExtra(MainActivity.RESULT_TABLE, resultTable);
        
        RadioButton rUnion,rDif,rUnique;
        rUnion = (RadioButton) findViewById(R.id.radioUnion);
        rDif = (RadioButton) findViewById(R.id.radioDifference);
        rUnique = (RadioButton) findViewById(R.id.radioUnique);
        if(rUnion.isChecked()){
            answerInent.putExtra(MainActivity.OPERATION, "union");
            String table2 = ((EditText)findViewById(R.id.table2)).getText().toString();
            answerInent.putExtra(MainActivity.TABLE2, table2);
        }
        else if(rDif.isChecked()){
            answerInent.putExtra(MainActivity.OPERATION, "difference");
            String table2 = ((EditText)findViewById(R.id.table2)).getText().toString();
            answerInent.putExtra(MainActivity.TABLE2, table2);
        }
        else{
            answerInent.putExtra(MainActivity.OPERATION, "unique");
            answerInent.putExtra(MainActivity.TABLE2, "");
        }
        
        setResult(RESULT_OK, answerInent);
        finish();
    }

}
