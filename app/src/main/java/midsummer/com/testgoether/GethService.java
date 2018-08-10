package midsummer.com.testgoether;



import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;


public class GethService {



/*    private static final int PRIVATE_KEY_RADIX = 16;
    *//**
     * CPU/Memory cost parameter. Must be larger than 1, a power of 2 and less than 2^(128 * r / 8).
     *//*
    private static final int N = 1 << 9;
    *//**
     * Parallelization parameter. Must be a positive integer less than or equal to Integer.MAX_VALUE / (128 * r * 8).
     *//*
    private static final int P = 1;

    private final KeyStore keyStore;

    public GethService(File keyStoreFile){
        keyStore = new KeyStore(keyStoreFile.getAbsolutePath(), Geth.LightScryptN, Geth.LightScryptP);
    }

    public GethService(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public Wallet createAccount(String password) {
        try {
            return new Wallet(keyStore.newAccount(password).getAddress().getHex().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Wallet importKeystore(String store, String password, String newPassword) {
        try {
            Account account = keyStore.importKey(store.getBytes(Charset.forName("UTF-8")), password, newPassword);
            return new Wallet(account.getAddress().getHex().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String exportAccount(Wallet wallet, String password, String newPassword) {
        try {
            Account account = findAccount(wallet.address);
            return new String(keyStore.exportKey(account, password, newPassword));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAccount(String address, String password){
        try {
            Account account = findAccount(address);
            keyStore.deleteAccount(account, password);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public byte[] signTransaction(Wallet signer, String signerPassword, String toAddress, BigInteger amount, BigInteger gasPrice, BigInteger gasLimit, long nonce, byte[] data, long chainId) {
        try {
            BigInt value = new BigInt(0);
            value.setString(amount.toString(), 10);

            BigInt gasPriceBI = new BigInt(0);
            gasPriceBI.setString(gasPrice.toString(), 10);

            BigInt gasLimitBI = new BigInt(0);
            gasLimitBI.setString(gasLimit.toString(), 10);

            Transaction tx = new Transaction(
                    nonce,
                    new Address(toAddress),
                    value,
                    gasLimitBI,
                    gasPriceBI,
                    data);

            BigInt chain = new BigInt(chainId); // Chain identifier of the main net
            Account gethAccount = findAccount(signer.address);
            keyStore.unlock(gethAccount, signerPassword);
            Transaction signed = keyStore.signTx(gethAccount, tx, chain);
            keyStore.lock(gethAccount.getAddress());

            return signed.encodeRLP();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean hasAccount(String address) {
        return keyStore.hasAddress(new Address(address));
    }


    public Wallet[] fetchAccounts() {
        try {
            Accounts accounts = keyStore.getAccounts();
            int len = (int) accounts.size();
            Wallet[] result = new Wallet[len];

            for (int i = 0; i < len; i++) {
                Account gethAccount = accounts.get(i);
                result[i] = new Wallet(gethAccount.getAddress().getHex().toLowerCase());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Account findAccount(String address) throws Exception {
        Accounts accounts = keyStore.getAccounts();
        int len = (int) accounts.size();
        for (int i = 0; i < len; i++) {
            try {
                android.util.Log.d("ACCOUNT_FIND", "Address: " + accounts.get(i).getAddress().getHex());
                if (accounts.get(i).getAddress().getHex().equalsIgnoreCase(address)) {
                    return accounts.get(i);
                }
            } catch (Exception ex) {
                *//* Quietly: interest only result, maybe next is ok. *//*
            }
        }
        throw new Exception("Wallet with address: " + address + " not found");
    }*/
}
