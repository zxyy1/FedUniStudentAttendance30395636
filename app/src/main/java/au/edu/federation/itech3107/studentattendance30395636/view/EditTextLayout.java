package au.edu.federation.itech3107.studentattendance30395636.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import au.edu.federation.itech3107.studentattendance30395636.R;


/**
 * Created by mnnyang on 17-11-5.
 */

public class EditTextLayout extends LinearLayout {

    private EditText mEtText;
    private ImageView mIvIcon;
    private ImageView mIvClear;
    private String mHint;
    private Boolean mCloseAuto;
    private boolean mCloseShow;

    public EditTextLayout(Context context) {
        super(context);
        init();
    }

    public EditTextLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public EditTextLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextLayout);
        mHint = typedArray.getString(R.styleable.EditTextLayout_hint);
        String text = typedArray.getString(R.styleable.EditTextLayout_text);
        Drawable icon = typedArray.getDrawable(R.styleable.EditTextLayout_icon);
        Boolean inputEnabled = typedArray.getBoolean(R.styleable.EditTextLayout_input_enabled, true);
        mCloseAuto = typedArray.getBoolean(R.styleable.EditTextLayout_close_auto, true);
        mCloseShow = typedArray.getBoolean(R.styleable.EditTextLayout_close_show, false);
        int textColor = typedArray.getColor(R.styleable.EditTextLayout_textColor, Color.BLACK);
        int hintColor = typedArray.getColor(R.styleable.EditTextLayout_hintColor, Color.GRAY);

        typedArray.recycle();

        init();
        setHint(mHint);
        setText(text);
        setIcon(icon);
        setTextColor(textColor);
        setHintColor(hintColor);
        setInputEnabled(inputEnabled);
    }

    private void setHintColor(int color) {
        mEtText.setHintTextColor(color);
    }

    private void setTextColor(int color) {
        mEtText.setTextColor(color);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_edit_text, this);
        mEtText = findViewById(R.id.et_text);
        mIvIcon = findViewById(R.id.iv_icon);
        mIvClear = findViewById(R.id.iv_clear);

        mIvClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCloseListener != null) {
                    mCloseListener.onClose();
                    return;
                }
                mEtText.setText("");
            }
        });

        if (mCloseAuto) {
            mEtText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    mIvClear.setVisibility(hasFocus ? VISIBLE : INVISIBLE);
                    mEtText.setHint(hasFocus ? "" : mHint);
                }
            });
        } else {
            mIvIcon.setVisibility(mCloseShow ? VISIBLE : INVISIBLE);
        }
    }

    /**
     * @param inputType definition of EditorInfo class
     */
    public EditTextLayout setInputType(int inputType) {
        mEtText.setInputType(inputType);
        return this;
    }

    public EditTextLayout setInputEnabled(boolean enabled) {
        mEtText.setFocusable(enabled);
        return this;
    }

    public EditTextLayout setHint(String hint) {
        mHint = hint;
        mEtText.setHint(hint);
        return this;
    }

    public EditTextLayout setText(String text) {
        mEtText.setText(text);
        return this;
    }

    public String getText() {
        return mEtText.getText().toString();
    }

    public EditTextLayout setIcon(Drawable icon) {
        if (icon != null) {
            mIvIcon.setImageDrawable(icon);
        }
        return this;
    }

    @Override
    public void setOnClickListener(@Nullable final OnClickListener l) {
        super.setOnClickListener(l);
        mEtText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l != null) {
                    l.onClick(EditTextLayout.this);
                }
            }
        });
    }

    private CloseListener mCloseListener;

    public interface CloseListener {
        void onClose();
    }

    public void disEditTextEvent() {
        mEtText.setClickable(false);
        mEtText.setFocusable(false);
        this.setClickable(true);
        this.setFocusable(true);
    }

    public EditTextLayout setCloseListener(CloseListener closeListener) {
        mCloseListener = closeListener;
        return this;
    }
}
