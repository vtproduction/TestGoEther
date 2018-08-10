package midsummer.com.testgoether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.io.File;
import java.security.SecureRandom;



public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "TESTGETHER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        create();
    }


    public String generatePassword() {
        byte bytes[] = new byte[256];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return new String(bytes);
    }

    public void create(){
        try {
            String password = "123456";
            Log.d(TAG, "create: password: " + password);
            File file = new File(getFilesDir(), "keystore/keystore");

            //GethService service = new GethService(file);
            /*Wallet wallet = service.createAccount(password);
            Log.d(TAG, "create: wallet: " + wallet.toString());


            String x = service.exportAccount(wallet, password, password);
            Log.d(TAG, "create: x: " + x);

            Wallet wallet1 = service.importKeystore(x, password, password);
            Log.d(TAG, "create: wallet1: " + wallet1.toString());*/

            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[16]; // 128 bits are converted to 16 bytes;
            random.nextBytes(bytes);



            /*String wallet2 = WalletUtils.generateLightNewWalletFile("123456", file);
            Log.d(TAG, "create: " + wallet2);*/

            /*Credentials credentials
                = WalletUtils.loadCredentials("123456", new File(file,"UTC--2018-08-07T14-41-47.069--60891332796f17d51e65649b3c9206506114c5c7.json"));
            Log.d(TAG, "create: cr: " + credentials.getAddress());
            Log.d(TAG, "create: cr: " + credentials.getEcKeyPair().getPrivateKey().toString(16));
            Log.d(TAG, "create: cr: " + credentials.getEcKeyPair().getPublicKey().toString(16));*/


            /*String wallet2 = WalletUtils.generateLightNewWalletFile("123456", file);
            Log.d(TAG, "create: " + wallet2);


            String mnemonicStr = MnemonicUtils.generateMnemonic(bytes);
            Log.d(TAG, "create: mnemonic: " + mnemonicStr);*/

            /*Bip39Wallet bip39Wallet
                    = WalletUtils.generateBip39Wallet(password, file);
            Log.d(TAG, "create: bip39 " + bip39Wallet.toString());


            Credentials credentials
                    = WalletUtils.loadCredentials(password, new File(file,bip39Wallet.getFilename()));
            Log.d(TAG, "create: cr: " + credentials.getAddress());
            Log.d(TAG, "create: cr: " + credentials.getEcKeyPair().getPrivateKey().toString(16));
            Log.d(TAG, "create: cr: " + credentials.getEcKeyPair().getPublicKey().toString(16));

            byte[] seed = MnemonicUtils.generateSeed(bip39Wallet.getMnemonic(), password);
            ECKeyPair key = ECKeyPair.create(Hash.sha256(seed));
            Log.d(TAG, "create: new privKey: " + key.getPrivateKey().toString(16));*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
