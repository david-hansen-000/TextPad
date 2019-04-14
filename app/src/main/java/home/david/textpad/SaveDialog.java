package home.david.textpad;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveDialog extends DialogFragment {

    private String filename;
    private EditText filename_et;
    private FragmentManager manager;
    private TextFragment textFragment;
    private ImageButton keyButton;
    private Listener listener;
    private DocumentFile openedFile;

    public SaveDialog() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view=getActivity().getLayoutInflater().inflate(R.layout.fragment_save, null);
        view.findViewById(R.id.submit_btn).setOnClickListener(this::submitAction);
        keyButton=view.findViewById(R.id.key_btn);
        keyButton.setOnClickListener(this::showEncryptDialog);
        filename_et=view.findViewById(R.id.filename_et);
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener= (Listener) getActivity();
    }

    public void setOpenedFile(DocumentFile openedFile) {
        this.openedFile = openedFile;
    }

    public String getSaveFileName() {
        return filename_et.getText().toString();
    }

    public interface Listener {
        public void onDialogSaveAction(SaveDialog saveDialog);
        public void onDialogEncrypt(SaveDialog saveDialog);
    }

    private void showEncryptDialog(View v) {
        listener.onDialogEncrypt(this);
    }

    private void submitAction(View view) {
        listener.onDialogSaveAction(this);
    }


    private void saveAction() {
//        if (saveFile.canWrite()) {
//            try (PrintStream writer = new PrintStream(getContext().getContentResolver().openOutputStream(saveFile.getUri()))) {
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
