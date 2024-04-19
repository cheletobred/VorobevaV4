package ru.mirea.vorobevavi.cryptoloader;

import static ru.mirea.vorobevavi.cryptoloader.MyLoader.ARG_WORD;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.vorobevavi.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    public	final	String	TAG	=	this.getClass().getSimpleName();
    private	final	int	LoaderID	=	1234;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    public void	onClickButton(View view)	{
        String stroka = binding.editText.getText().toString();
        byte[] key = generateKey();
        String base64Key = Base64.encodeToString(key, Base64.DEFAULT);
        String cryptText = encryptMsg(stroka, key);
        Bundle	bundle	=	new	Bundle();
        bundle.putString("cryptText",cryptText);
        bundle.putString("key",	base64Key);
        LoaderManager.getInstance(MainActivity.this).restartLoader(LoaderID,	bundle,	this);
    }
    private byte[] generateKey() {
        byte[] key = new byte[16];
        new SecureRandom().nextBytes(key);
        return key;
    }
    public	static	String	encryptMsg(String	message,	byte[]	key)	{
        Cipher cipher	=	null;
        try	{
            cipher	=	Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE,	secretKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        }	catch	(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                       BadPaddingException | IllegalBlockSizeException e)	{
            throw	new	RuntimeException(e);
        }
    }
    @NonNull
    @Override
    public	Loader<String>	onCreateLoader(int	i,	@Nullable	Bundle	bundle)	{
        String cryptText = bundle.getString("cryptText");
        String key = bundle.getString("key");
        return new MyLoader(this, cryptText, key);
    }
    @Override
    public	void	onLoadFinished(@NonNull	Loader<String>	loader,	String	s)	{
        if	(loader.getId()	==	LoaderID)	{
            Toast.makeText(this,	"Дешифрованная фраза "	+	s,	Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public	void	onLoaderReset(@NonNull	Loader<String>	loader)	{
        Log.d(TAG,	"onLoaderReset");
    }
}