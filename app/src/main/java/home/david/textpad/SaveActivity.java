package home.david.textpad;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.PrintStream;

public class SaveActivity extends AppCompatActivity {

    private DocumentFile saveFile;
    private String filename;
    private EditText filename_et;
    private ImageButton keyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_save);
        filename_et=findViewById(R.id.filename_et);

        Intent intent=getIntent();
        if (intent.getData()!=null && intent.getAction().equals("MainActivity SaveAction")) {
            saveFile=DocumentFile.fromSingleUri(this, intent.getData());
            filename_et.setText(saveFile.getName());
        }
        keyButton=findViewById(R.id.key_btn);
        keyButton.setOnClickListener(this::showEncryptDialog);

    }

    private void showEncryptDialog(View v) {
        keyButton.setEnabled(false);
        keyButton.setVisibility(View.INVISIBLE);
        //TODO: EncryptedAction open here
    }

    private void submitAction(View view) {
        Intent file_intent=new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        filename=filename_et.getText().toString();
        if (!filename.equals(saveFile.getName())) {
            startActivityForResult(file_intent, MainActivity.OPENFILECODE);
        } else {
            saveAction();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==MainActivity.OPENFILECODE && resultCode==RESULT_OK) {
            if (data!=null) {
                //Log.i("FILENAME",data.getStringExtra("FILENAME"));
                if (filename==null) {
                    filename="text.txt";
                }
                Uri uri = data.getData();
                //setText("we'll be saving at:"+uri.toString());
                DocumentFile dir=DocumentFile.fromTreeUri(this, uri);
                saveFile=dir.findFile(filename);
                if (saveFile==null) {
                    saveFile = dir.createFile("text/plain", filename);
                }
                saveAction();
            }
        }
    }

    private void saveAction() {
//        if (saveFile.canWrite()) {
//            try (PrintStream writer = new PrintStream(getContentResolver().openOutputStream(saveFile.getUri()))) {
//                byte[] bytes=textFragment.getBytes();
//                writer.write(bytes);
//                writer.flush();
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Log.i("PROBLEM","could not write to saveFile");
//        }
    }

}
