package ru.mirea.vorobevavi.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public	class	MyLoader	extends AsyncTaskLoader<String> {
    private String	text;
    private String key;
    public	static	final	String	ARG_WORD	=	"word";
    public	MyLoader(@NonNull Context context, String text, String key)	{
        super(context);
        this.text = text;
        this.key=key;
    }
    @Override
    protected	void	onStartLoading()	{
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public	String	loadInBackground()	{
        //	emulate	long-running	operation
        return	decryptMsg(text, key);
    }
    private String decryptMsg(String encryptedPhrase, String base64Key) {
        try {
            byte[] decodedKey = Base64.decode(base64Key, Base64.DEFAULT);

            // Инициализация Cipher для дешифрования
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Дешифрование и возврат результата
            byte[] decryptedBytes = cipher.doFinal(Base64.decode(encryptedPhrase, Base64.DEFAULT));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}