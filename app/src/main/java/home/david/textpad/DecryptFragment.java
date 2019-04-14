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
public class DecryptFragment extends Fragment {

    private TextFragment textFragment;
    private FragmentManager manager;
    private Button crypt_btn;
    private TextInputEditText password;
    private TextInputEditText buffer;
    private TextView preview;
    private boolean crypted;

    public DecryptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crypt, container, false);
        crypt_btn=view.findViewById(R.id.crypt_btn);
        crypt_btn.setText("Decrypt");
        crypt_btn.setOnClickListener(v->submit());
        password=view.findViewById(R.id.password_text);
        buffer=view.findViewById(R.id.buffer_text);
        preview=view.findViewById(R.id.preview_text);
        preview.setOnClickListener(v->crypt());
        return view;
    }

    private void submit() {
        //manager.beginTransaction().replace(R.id.fragment_holder, textFragment).commit();
        if (!crypted) {
            crypt();
        }
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
            byte[] decrypted=crypt.decryptToBytes(textFragment.getBytes());
            textFragment.setBytes(decrypted);
            //textFragment.setText(new String(decrypted));
            //textFragment.setString(new String(decrypted));
            preview.setText(new String(decrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
        crypted=true;
    }

}
