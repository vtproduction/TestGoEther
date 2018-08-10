package com.midsummer.web3jlight.service;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by NienLe on 10,August,2018
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
public class W3JL implements W3JLRepository {
    private final int RADIX = 16;
    @Override
    public Bip39Wallet createBip39Wallet(String password, File file) throws IOException{
        try {
            return WalletUtils.generateBip39Wallet(password, file);
        } catch (CipherException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String createMnemonics(byte[] entropy) {
        return MnemonicUtils.generateMnemonic(entropy);
    }

    @Override
    public String mnemonicsToPrivateKey(String mnemonics, String password) {
        return mnemonicsToKeyPair(mnemonics, password).getPrivateKey().toString(RADIX);
    }

    @Override
    public String mnemonicsToPublicKey(String mnemonics, String password) {
        return mnemonicsToKeyPair(mnemonics, password).getPublicKey().toString(RADIX);
    }

    @Override
    public ECKeyPair mnemonicsToKeyPair(String mnemonics, String password) {
        byte[] seeds = MnemonicUtils.generateSeed(mnemonics, password);
        ECKeyPair key = ECKeyPair.create(Hash.sha256(seeds));
        return key;
    }

    @Override
    public ECKeyPair walletToKeyPair(Bip39Wallet wallet, String password) {
        return mnemonicsToKeyPair(wallet.getMnemonic(), password);
    }

    @Override
    public Credentials loadCredential(String password, File file) throws IOException{
        try {
            Credentials credentials
                    = WalletUtils.loadCredentials(password, file);
            return credentials;
        } catch (CipherException e) {
            e.printStackTrace();
            return null;
        }
    }
}
