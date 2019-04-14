package home.david.textpad;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {

    public static final String MY_BYTES = "my_bytes";
    private EditText text;
    private FragmentManager manager;
    private byte[] bytes;


    public TextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        if (text == null) {
            text = view.findViewById(R.id.message);
            log("text was null");
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(MY_BYTES)) {
            bytes = savedInstanceState.getByteArray(MY_BYTES);
            log("bytes restored from saved instance");
        }
        if (bytes != null) {
            text.setText(new String(bytes));
        }
        log("created view");
        return view;
    }


    public void setBytesFromText() {
        bytes = text.getText().toString().getBytes();
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        if (bytes.length > 0) {
            this.bytes = bytes;
            if (text != null) {
                text.setText(new String(bytes));
            }
        }
        log("bytes set");
    }

    public String getText() {
        String result = null;
        if (text != null) {
            result = text.getText().toString();
        } else {
            result = "nothing yet";
        }
        return result;
    }

    private void log(String message) {
        if (message == null) {
            message = "";
        }
        Log.i("TextFragment Message", message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("fragment destroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("fragment destroy view");
        bytes = text.getText().toString().getBytes();
        //persistedData.setData(text.getText().toString());
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bytes==null) {
            bytes=text.getText().toString().getBytes();
        }
        if (bytes.length > 0) {
            outState.putByteArray(MY_BYTES, bytes);
        }
        log("save instance");
    }
}
