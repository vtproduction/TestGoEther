package com.midsummer.web3jlight.service;

import com.midsummer.web3jlight.entity.Wallet;

import java.math.BigInteger;

/**
 * Created by NienLe on 10,August,2018
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
public interface W3JLAccountKeystoreService {
    /**
     * Create account in keystore
     * @param password account password
     * @return new {@link Wallet}
     */
    Wallet createAccount(String password) throws Exception;

    /**
     * Include new existing keystore
     * @param store store to include
     * @param password store password
     * @return included {@link Wallet} if success
     */
    Wallet importKeystore(String store, String password, String newPassword)  throws Exception;
    Wallet importPrivateKey(String privateKey, String newPassword)  throws Exception;

    /**
     * Export wallet to keystore
     * @param wallet wallet to export
     * @param password password from wallet
     * @param newPassword new password to store
     * @return store data
     */
    String exportAccount(Wallet wallet, String password, String newPassword) throws Exception;

    /**
     * Delete account from keystore
     * @param address account address
     * @param password account password
     */
    void deleteAccount(String address, String password) throws Exception;

    /**
     * Sign transaction
     * @param signer {@link Wallet}
     * @param signerPassword password from {@link Wallet}
     * @param toAddress transaction destination address
     * @param nonce
     * @return sign data
     */
    byte[] signTransaction(
            Wallet signer,
            String signerPassword,
            String toAddress,
            BigInteger amount,
            BigInteger gasPrice,
            BigInteger gasLimit,
            long nonce,
            byte[] data,
            long chainId) throws Exception;

    /**
     * Check if there is an address in the keystore
     * @param address {@link Wallet} address
     */
    boolean hasAccount(String address);

    /**
     * Return all {@link Wallet} from keystore
     * @return wallets
     */
    Wallet[] fetchAccounts() throws Exception;
}
