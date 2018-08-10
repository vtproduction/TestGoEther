package com.midsummer.web3jlight.service;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import java.io.File;
import java.io.IOException;

/**
 * Created by NienLe on 10,August,2018
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
public interface W3JLRepository {

    Bip39Wallet createBip39Wallet (String password, File file) throws IOException;
    String createMnemonics (byte[] entropy);
    String mnemonicsToPrivateKey(String mnemonics, String password);
    String mnemonicsToPublicKey(String mnemonics, String password);
    ECKeyPair mnemonicsToKeyPair(String mnemonics, String password);
    ECKeyPair walletToKeyPair(Bip39Wallet wallet, String password);
    Credentials loadCredential (String password, File file) throws IOException;

}
