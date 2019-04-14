package home.david.textpad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class OpenActivity extends AppCompatActivity {

    private static final int OPENFILECODE = 123;
    private AutoCompleteTextView file_list;
    private Button open_btn;
    private DocumentFile dir;
    private DocumentFile selected_file;
    private byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_open);
        file_list=findViewById(R.id.file_list_actv);
        open_btn=findViewById(R.id.open_btn);
        open_btn.setEnabled(false);
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, OPENFILECODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==OPENFILECODE && resultCode== Activity.RESULT_OK) {
            dir= DocumentFile.fromTreeUri(this, data.getData());
            DocumentFile[] filesA=dir.listFiles();
            ArrayList<FileName> fileList=new ArrayList<>();
            for (DocumentFile file:filesA) {
                if (!file.isDirectory()) {
                    fileList.add(new FileName(file));
                }
            }
            ArrayAdapter<FileName> file_adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            file_adapter.addAll(fileList);
            file_list.setAdapter(file_adapter);
            file_list.setCustomInsertionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
            file_list.setOnLongClickListener(v -> {
                file_list.showDropDown();
                return false;
            });
            file_list.setOnItemClickListener((parent, view, position, id) -> {
                FileName selectedItem=(FileName)parent.getAdapter().getItem(position);
                selected_file= selectedItem.getFile();
                file_list.setText(selected_file.getName());
            });

            open_btn.setEnabled(true);
            open_btn.setOnClickListener(v->openFile());
        }
    }

    private void openFile() {
        // DocumentFile file=dir.findFile(file_list.getText().toString());
        if (selected_file!=null) {
            bytes=openFile(selected_file.getUri(), this);
        }
        Intent result=new Intent("OpenActivity Result");
        result.putExtra("file_bytes", bytes);
        result.setData(selected_file.getUri());
        setResult(RESULT_OK, result);
        finish();
    }

    public static byte[] openFile(Uri uri, Context context) {
        byte[] bytes=null;
        try {
            BufferedInputStream reader=new BufferedInputStream(context.getContentResolver().openInputStream(uri));
            bytes=new byte[reader.available()];
            reader.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
