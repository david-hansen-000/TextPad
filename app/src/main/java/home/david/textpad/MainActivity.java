package home.david.textpad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements SaveDialog.Listener {

    public static final int OPENFILECODE = 555;
    private static final int SAVEFILECODE = 123;

    private FragmentManager manager;
    private DocumentFile openedFile;
    private EditText main_text;
    private byte[] bytes; //for use in encryption/decryption and easily setting the text in main_text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this::setupNavigationMenuSelection);
        main_text =findViewById(R.id.main_text);
        manager=getSupportFragmentManager();
        processViewShare();
    }

    private boolean setupNavigationMenuSelection(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                return true;
            case R.id.navigation_save:
                showSaveDialog();
                return true;
            case R.id.navigation_open: {
                showOpenDialog();
                return true;
            }
            case R.id.navigation_decrypt: {
                showPasswordDialog();
                return true;
            }

        }
        return false;
    }


    private void showPasswordDialog() {
        //TODO: convert Encrypt/Decrypt to PasswordDialog
    }

    private void showOpenDialog() {
        //TODO: create an OpenDialog fragment instead of a regular Fragment
    }

    private void processViewShare() {
        Intent other=getIntent();
        Uri data=other.getData();
        if (data==null) {
            data=other.getParcelableExtra(Intent.EXTRA_STREAM);
        }
        if (data!=null) {
            DocumentFile file=DocumentFile.fromSingleUri(this,data);
            openedFile=file;
            //TODO: set main_text to contents of file
        } else {
            Log.i("PROBLEM","no file shared or opened");
        }
    }

    private void showSaveDialog() {
        SaveDialog saveDialog=new SaveDialog();
        if (openedFile!=null) {
            saveDialog.setOpenedFile(openedFile);
        }
        saveDialog.show(manager, "Save Dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case OPENFILECODE: {
                    openFile(data.getData());
                    break;
                }
                case SAVEFILECODE: {

                }
            }
        }
    }

    private void openFile(Uri data) {

    }

    @Override
    public void onDialogSaveAction(SaveDialog saveDialog) {
        if (openedFile!=null) {
            if (saveDialog.getSaveFileName().equals(openedFile.getName())) {
                //TODO: save the contents of main_text to openedFile
            } else {
                Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                //TODO: start the intent action and process the results
            }
        }
    }

    @Override
    public void onDialogEncrypt(SaveDialog saveDialog) {
        //TODO convert the Encrypt/Decrypt Fragments into a dialog
        //pass to openPasswordDialog
    }
}
