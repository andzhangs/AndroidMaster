package com.android.jetpack.emoji;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.emoji.widget.EmojiEditTextHelper;
import androidx.emoji.widget.EmojiTextViewHelper;


public class EmojiTextView extends AppCompatTextView {
    public EmojiTextView(Context context) {
        super(context);
        init(context);
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){
        getEmojiEditTextHelper().updateTransformationMethod();
    }

    @Override
    public void setFilters(InputFilter[] filters) {
        super.setFilters(getEmojiEditTextHelper().getFilters(filters));
    }

    @Override
    public void setAllCaps(boolean allCaps) {
        super.setAllCaps(allCaps);
        getEmojiEditTextHelper().setAllCaps(allCaps);
    }

    private EmojiTextViewHelper getEmojiEditTextHelper(){
        EmojiTextViewHelper emojiTextViewHelper=new EmojiTextViewHelper(this);
        return emojiTextViewHelper;
    }
}
