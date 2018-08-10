package midsummer.com.testgoether;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.rx.Web3jRx;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestWeb3JActivity extends AppCompatActivity {
    private static final String TAG = "TestWeb3JActivity";
    Web3j web3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web3_j);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        web3 = Web3jFactory.build(new HttpService("https://ropsten.infura.io/v3/95fa3a86534344ee9d1bf00e2b0d6d06"));

        /*try {
            EthGetBalance ethGetBalance = web3
                    .ethGetBalance("0xB89d25B8378d8E8b2CB11E6e1bF80fBf33386f45", DefaultBlockParameterName.LATEST)
                    .sendAsync()
                    .get();
            BigInteger wei = ethGetBalance.getBalance();
            Log.d(TAG, "onCreate: " + weiToEth(wei,5));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }*/




        /*try {
            String address = "0x22a3140b08c8929af4fd2a80d068f9c82204e943";
            String walletFile = "UTC--2018-08-10T16-34-52.129--22a3140b08c8929af4fd2a80d068f9c82204e943.json";

            File fileDir = new File(getApplicationInfo().dataDir, "keystore");

            if (!fileDir.exists()){
                boolean success = fileDir.mkdirs();
                Log.d(TAG, "onCreate: " + success);
            }
            String password = "123456";

            Credentials credentials = WalletUtils.loadCredentials(password, new File(fileDir, walletFile));
            Log.d(TAG, "onCreate: credential:" + credentials.getAddress());
            Log.d(TAG, "onCreate: credential:" + credentials.getEcKeyPair().getPrivateKey().toString(16));
            BigInteger amount = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send()
                    .getBalance();

            EthGetTransactionCount transactionCount = web3.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();

            BigInteger gas = BigInteger.valueOf(21000);
            BigInteger xth = new BigInteger(weiInEth);
            BigInteger gasprice = web3.ethGasPrice().send().getGasPrice();
            BigInteger gaslimit = BigInteger.valueOf(4800000);
            BigInteger txnFee = gas.multiply(gasprice);
            int compareResult = amount.compareTo(gas.multiply(gasprice));

            if ((compareResult != 0 && compareResult != -1)
                    && address != web3.ethAccounts().send().getAccounts().get(0)) {
                EthGetTransactionCount ethGetTransactionCount = web3
                        .ethGetTransactionCount(web3.ethAccounts().send().getAccounts().get(0),
                                DefaultBlockParameterName.LATEST)
                        .sendAsync().get();
                BigInteger nonce = ethGetTransactionCount.getTransactionCount();
                BigDecimal gasInDecimal = new BigDecimal(gas);
                BigDecimal gaspriceInDecimal = new BigDecimal(gasprice);
                balance = balance.subtract((gasInDecimal.multiply(gaspriceInDecimal)).divide(new BigDecimal(xth)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        String password = "123456";
        String walletFile = "UTC--2018-08-10T16-34-52.129--22a3140b08c8929af4fd2a80d068f9c82204e943.json";
        String addressFrom = "0x22a3140b08c8929af4fd2a80d068f9c82204e943";
        String addressTo = "0x95b52bcd93D4A87a7E98975F2245a57789a2D34d";
        File fileDir = new File(getApplicationInfo().dataDir, "keystore");
        /*Log.d(TAG, "onCreate: start send !");
        try {
            String txhash = transfer(password, new File(fileDir, walletFile), addressFrom, addressTo, BigInteger.valueOf(1000000000000000000L));
            Log.d(TAG, "onCreate: txhash: " + txhash);
        } catch (InterruptedException | ExecutionException | IOException | CipherException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: end send !");*/
        getAllTransactionCount(addressFrom);
    }

    public void getAllTransactionCount(String address){
        try {
            EthGetTransactionCount txCount = web3.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST)
                    .sendAsync().get();
            Log.d(TAG, "getAllTransaction: " + txCount.getTransactionCount().toString(16));



        } catch (InterruptedException |ExecutionException e) {
            e.printStackTrace();
        }
    }

    static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
    public String transfer (String password, File walletFile, String from, String to, BigInteger amount) throws InterruptedException, ExecutionException, IOException, CipherException{
        Credentials credentials1 = WalletUtils.loadCredentials(
                password,
                walletFile);

        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
                from, DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        System.out.println(nonce);

        RawTransaction rawTransaction = RawTransaction.createEtherTransaction (
                nonce, GAS_PRICE, GAS_LIMIT, to, amount);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials1);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = ethSendTransaction.getTransactionHash();
        return transactionHash;
    }



    private static String weiInEth  = "1000000000000000000";

    public static BigDecimal weiToEth(BigInteger wei) {
        return Convert.fromWei(new BigDecimal(wei), Convert.Unit.ETHER);
    }

    public static String weiToEth(BigInteger wei, int sigFig) {
        BigDecimal eth = weiToEth(wei);
        int scale = sigFig - eth.precision() + eth.scale();
        BigDecimal eth_scaled = eth.setScale(scale, RoundingMode.HALF_UP);
        return eth_scaled.toString();
    }
}
