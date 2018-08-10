package com.midsummer.web3jlight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midsummer.web3jlight.entity.Wallet;

import org.ethereum.geth.Account;
import org.ethereum.geth.Accounts;
import org.ethereum.geth.Address;
import org.ethereum.geth.BigInt;
import org.ethereum.geth.Geth;
import org.ethereum.geth.KeyStore;
import org.ethereum.geth.Transaction;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;

import static org.web3j.crypto.Wallet.create;

/**
 * Created by NienLe on 10,August,2018
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
public class W3JLGethKeystoreAccountService implements W3JLAccountKeystoreService {
    private static final int PRIVATE_KEY_RADIX = 16;
    private static final int N = 1 << 9;
    private static final int P = 1;

    private final KeyStore keyStore;
    public W3JLGethKeystoreAccountService(File keyStoreFile) {
        keyStore = new KeyStore(keyStoreFile.getAbsolutePath(), Geth.LightScryptN, Geth.LightScryptP);
    }

    public W3JLGethKeystoreAccountService(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    @Override
    public Wallet createAccount(String password) throws Exception{
        return new Wallet(
                keyStore.newAccount(password).getAddress().getHex().toLowerCase());
    }

    @Override
    public Wallet importKeystore(String store, String password, String newPassword)  throws Exception {
        Account account = keyStore
                .importKey(store.getBytes(Charset.forName("UTF-8")), password, newPassword);
        return new Wallet(account.getAddress().getHex().toLowerCase());
    }

    @Override
    public Wallet importPrivateKey(String privateKey, String newPassword)   throws Exception{
        BigInteger key = new BigInteger(privateKey, PRIVATE_KEY_RADIX);
        ECKeyPair keypair = ECKeyPair.create(key);
        WalletFile walletFile = create(newPassword, keypair, N, P);
        String tmp = new ObjectMapper().writeValueAsString(walletFile);
        return importKeystore(tmp, newPassword, newPassword);
    }

    @Override
    public String exportAccount(Wallet wallet, String password, String newPassword) throws Exception{
        Account account = findAccount(wallet.address);
        if (account == null) return null;
        return new String(keyStore.exportKey(account, password, newPassword));
    }

    @Override
    public void deleteAccount(String address, String password) throws Exception{
        Account account = findAccount(address);
        if (account == null) return ;
        keyStore.deleteAccount(account, password);
    }

    @Override
    public byte[] signTransaction(Wallet signer, String signerPassword, String toAddress, BigInteger amount, BigInteger gasPrice, BigInteger gasLimit, long nonce, byte[] data, long chainId)
            throws Exception{
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
        org.ethereum.geth.Account gethAccount = findAccount(signer.address);
        keyStore.unlock(gethAccount, signerPassword);
        Transaction signed = keyStore.signTx(gethAccount, tx, chain);
        keyStore.lock(gethAccount.getAddress());

        return signed.encodeRLP();
    }

    @Override
    public boolean hasAccount(String address) {
        return keyStore.hasAddress(new Address(address));
    }

    @Override
    public Wallet[] fetchAccounts() throws Exception{
        Accounts accounts = keyStore.getAccounts();
        int len = (int) accounts.size();
        Wallet[] result = new Wallet[len];

        for (int i = 0; i < len; i++) {
            org.ethereum.geth.Account gethAccount = accounts.get(i);
            result[i] = new Wallet(gethAccount.getAddress().getHex().toLowerCase());
        }
        return result;
    }



    private org.ethereum.geth.Account findAccount(String address) throws Exception {
        Accounts accounts = keyStore.getAccounts();
        int len = (int) accounts.size();
        for (int i = 0; i < len; i++) {
            try {
                android.util.Log.d("ACCOUNT_FIND", "Address: " + accounts.get(i).getAddress().getHex());
                if (accounts.get(i).getAddress().getHex().equalsIgnoreCase(address)) {
                    return accounts.get(i);
                }
            } catch (Exception ex) {
                /* Quietly: interest only result, maybe next is ok. */
            }
        }
        throw new Exception("Wallet with address: " + address + " not found");
    }
}
