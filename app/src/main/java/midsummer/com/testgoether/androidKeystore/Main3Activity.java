package midsummer.com.testgoether.androidKeystore;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import midsummer.com.testgoether.R;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener{
    private EnCryptor encryptor;
    private DeCryptor decryptor;
    private static final String TAG = "Main3Activity";
    private static final String SAMPLE_ALIAS = "MYALIAS";
    private AppCompatButton button1, button2, button3;
    private EditText editText1, editText2;

    String text = "pleaseencrypthisahihihi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button1 = findViewById(R.id.btn_1);
        button2 = findViewById(R.id.btn_2);
        button3 = findViewById(R.id.btn_3);
        editText1 = findViewById(R.id.edt1);
        editText2 = findViewById(R.id.edt2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);


        try {
            decryptor = new DeCryptor();
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException |
                IOException e) {
            e.printStackTrace();
        }
        //decryptText();




    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_1:
                encryptor = new EnCryptor(this);
                try {
                    Log.d(TAG, "onClick: privatekey: " + encryptor.getSecretKey(SAMPLE_ALIAS).toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_2:
                encryptText(text);
                break;
            case R.id.btn_3:
                decryptText();
                break;
        }
    }

    private void decryptText() {
        try {
            EncryptedInfo info = loadFromPref("info1");
            Log.d(TAG, "decryptText: " + decryptor
                    .decryptData(SAMPLE_ALIAS, info.data, info.iv));
        } catch (UnrecoverableEntryException | NoSuchAlgorithmException |
                KeyStoreException | NoSuchPaddingException | NoSuchProviderException |
                IOException | InvalidKeyException e) {
            Log.e(TAG, "decryptData() called with: " + e.getMessage(), e);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }




    private void encryptText(String text) {

        try {
            final byte[] encryptedText = encryptor
                    .encryptText(SAMPLE_ALIAS, text);
            Log.d(TAG, "encryptText: " + Base64.encodeToString(encryptedText, Base64.DEFAULT));
            Log.d(TAG, "encryptText: iv: " + Base64.encodeToString(encryptor.getIv(), Base64.DEFAULT));

            EncryptedInfo info = new EncryptedInfo(encryptor.getEncryption(), encryptor.getIv());
            saveToPref(info, "info1");


        } catch (UnrecoverableEntryException | NoSuchAlgorithmException | NoSuchProviderException |
                KeyStoreException | IOException | NoSuchPaddingException | InvalidKeyException e) {
            Log.e(TAG, "onClick() called with: " + e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }




    public class EncryptedInfo{
        byte[] data;
        byte[] iv;

        public EncryptedInfo() {
        }

        public EncryptedInfo(byte[] data, byte[] iv) {
            this.data = data;
            this.iv = iv;
        }
    }


    private void saveToPref(EncryptedInfo encryptedInfo, String alias){
        SharedPreferences p = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putString(alias, Base64.encodeToString(encryptedInfo.data, Base64.DEFAULT));
        e.putString(alias+"_iv",Base64.encodeToString(encryptedInfo.iv, Base64.DEFAULT));
        e.apply();
    }


    private EncryptedInfo loadFromPref(String alias){
        SharedPreferences p = getSharedPreferences("data", MODE_PRIVATE);
        String data = p.getString(alias,"");
        String iv = p.getString(alias+"_iv","");
        EncryptedInfo info = new EncryptedInfo();
        info.data = Base64.decode(data, Base64.DEFAULT);
        info.iv = Base64.decode(iv, Base64.DEFAULT);
        return info;
    }
}
