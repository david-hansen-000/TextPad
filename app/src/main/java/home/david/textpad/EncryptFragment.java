package home.david.textpad;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EncryptFragment extends Fragment {

    private TextFragment textFragment;
    private FragmentManager manager;
    private Button crypt_btn;
    private TextInputEditText password;
    private TextInputEditText buffer;
    private TextView preview;
    private boolean crypted;

    public EncryptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crypt, container, false);
        crypted=false;
        crypt_btn=view.findViewById(R.id.crypt_btn);
        crypt_btn.setText("Encrypt");
        crypt_btn.setOnClickListener(v->submit());
        password=view.findViewById(R.id.password_text);
        buffer=view.findViewById(R.id.buffer_text);
        preview=view.findViewById(R.id.preview_text);
        preview.setOnClickListener(v->crypt());
        return view;
    }

    private void submit() {
        if (!crypted) {
            crypt();
        }
        //manager.beginTransaction().replace(R.id.fragment_holder, textFragment).commit();
    }

    public void setTextFragment(TextFragment textFragment) {
        this.textFragment = textFragment;
    }

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    private void crypt() {
        Crypt crypt=new Crypt();
        try {
            crypt.setPassword(password.getText().toString());
            crypt.setBuffer(buffer.getText().toString().charAt(0));
            byte[] encrypted=crypt.encrypt(new String(textFragment.getBytes()));
            textFragment.setBytes(encrypted);
            preview.setText(new String(encrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
