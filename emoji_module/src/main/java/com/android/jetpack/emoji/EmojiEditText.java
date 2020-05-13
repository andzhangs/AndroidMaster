package com.android.jetpack.emoji;

import android.content.Context;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.emoji.widget.EmojiEditTextHelper;


public class EmojiEditText extends AppCompatEditText {
    public EmojiEditText(Context context) {
        super(context);
        init(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){
        super.setKeyListener(getEmojiEditTextHelper().getKeyListener(getKeyListener()));
    }

    @Override
    public void setKeyListener(KeyListener input) {
        super.setKeyListener(getEmojiEditTextHelper().getKeyListener(input));
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection inputConnection=super.onCreateInputConnection(outAttrs);
        return getEmojiEditTextHelper().onCreateInputConnection(inputConnection,outAttrs);
    }

    private EmojiEditTextHelper getEmojiEditTextHelper(){
        EmojiEditTextHelper emojiEditTextHelper=new EmojiEditTextHelper(this);
        return emojiEditTextHelper;
    }
}
