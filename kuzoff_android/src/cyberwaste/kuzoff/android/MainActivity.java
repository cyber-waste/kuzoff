package cyberwaste.kuzoff.android;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.example.quickstart.R;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.impl.DatabaseManagerImpl;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    public final static String TABLE1 = "cyberwaste.kuzoff.android.TABLE1";
    public final static String TABLE2 = "cyberwaste.kuzoff.android.TABLE2";
    public final static String OPERATION = "cyberwaste.kuzoff.android.OPERATION";
    public final static String RESULT_TABLE = "cyberwaste.kuzoff.android.RESULT_TABLE";
    private final static int CREATE_TABLE_CODE = 0;
    
    private AlertDialog.Builder builder;
    private Context context;
    private DatabaseManager dbmanager;
    private String currentTableName = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_table);
        context = MainActivity.this;
        dbmanager = new DatabaseManagerImpl();
        dbmanager.setDatabaseHome(getFilesDir());
        dbmanager.forDatabaseFolder("db");
    }
    
    private void createSimpleDatabase() throws Exception{
        List<String> types = new ArrayList<String>();
        types.add("int"); types.add("char");
        dbmanager.createTable("table1",types);
        List<String> row = new ArrayList<String>();
        row.add("10"); row.add("a");
        dbmanager.addRow("table1",row);
        row = new ArrayList<String>();
        row.add("20"); row.add("b");
        dbmanager.addRow("table1", row);
        
        dbmanager.createTable("table2", types);
        row.clear();
        row.add("20"); row.add("b");
        dbmanager.addRow("table2", row);
        row.clear();
        row.add("30"); row.add("c");
        dbmanager.addRow("table2", row);
        row.clear();
        row.add("30"); row.add("c");
        dbmanager.addRow("table2", row);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void addRow(View view){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new row to table");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(currentTableName == "") return;
                String[] data = input.getText().toString().split("\\s+");;
                List<String> columnData = new ArrayList<String>(Arrays.asList(data));
                try {
                    dbmanager.addRow(currentTableName, columnData);
                    addRow(data);
                } catch (Exception e) {
                    return;
                } 
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show(); 
    }
    
    public void addRow(String[] data){
        TableLayout table = (TableLayout) findViewById(R.id.spreadsheet);
        
        TableRow row = new TableRow(this);
        row.setGravity(Gravity.CENTER);
        
        for(int i=0;i<data.length;i++){
            TextView text = new TextView(this);
            text.setText(data[i]);
            text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36);  
            text.setGravity(Gravity.CENTER);  
            text.setTypeface(Typeface.SERIF, Typeface.BOLD);
            text.setTextColor(Color.WHITE);
            row.addView(text);
        }
        
        table.addView(row);
    }
    
    public void listTables(View v) throws IOException{
        final ArrayList<Table> tables = (ArrayList<Table>)dbmanager.listTables();
        builder = new AlertDialog.Builder(context);
        String[] tableNames = new String[tables.size()];
        int i=0;
        for(Table table : tables){
            tableNames[i] = table.getName();        
            i++;
        }
        builder.setTitle("Choose table");
        builder.setItems(tableNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = tables.get(which).getName();
                currentTableName = name;
                Button addButton = (Button) findViewById(R.id.add_row_button);
                addButton.setEnabled(true);
                showTable(name);
            }
        });
        builder.show();
    }
    
    public void showTable(String tableName){
        try {
            List<Row> rows = dbmanager.loadTableData(tableName);
            TableLayout table = (TableLayout) findViewById(R.id.spreadsheet);
            table.removeAllViews();
            for(Row row : rows){
                String[] data = new String[row.length()];
                for(int i=0;i<row.length();i++){
                    data[i] = row.getElement(i).getData();
                }
                addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void createTable(View v){
        Intent intent = new Intent(this, CreateTableActivity.class);
        startActivityForResult(intent, CREATE_TABLE_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_TABLE_CODE && resultCode == RESULT_OK){
            String Table1 = data.getStringExtra(TABLE1);
            String Table2 = data.getStringExtra(TABLE2);
            String ResultTable = data.getStringExtra(RESULT_TABLE);
            String Operation = data.getStringExtra(OPERATION);
            try {
                if(Operation.equals("union")){
                    dbmanager.unionTable(Table1, Table2, ResultTable);
                }
                else if(Operation.equals("difference")){
                    dbmanager.differenceTable(Table1, Table2, ResultTable);
                }
                else{
                    dbmanager.uniqueTable(Table1, ResultTable);
                }
                Button addButton = (Button) findViewById(R.id.add_row_button);
                addButton.setEnabled(true);
                showTable(ResultTable);
            } catch (Exception e) {
                return;
            }
        }
    }
    
    public void showAlert(View v){
        File[] files = getFilesDir().listFiles();
        builder = new AlertDialog.Builder(context);
        if(files.length <= 0){
            builder.setMessage("Zero files");
        }
        else{
            builder.setMessage(files[0].getAbsolutePath());
        }
        builder.show();
    }
    
}
