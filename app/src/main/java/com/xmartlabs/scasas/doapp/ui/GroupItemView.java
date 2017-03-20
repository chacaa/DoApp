package com.xmartlabs.scasas.doapp.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xmartlabs.scasas.doapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by scasas on 3/20/17.
 */
public class GroupItemView extends LinearLayout {
  @BindView(R.id.group_title)
  TextView groupTitleView;
  @BindView(R.id.group_subtitle)
  TextView groupSubtitleView;
  @BindView(R.id.group_color)
  View groupColorView;

  public GroupItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    //TODO when possible continue with this
//    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GroupItemView);
//    int title = typedArray.getInt(R.styleable.GroupItemView_group_title_attr, 0);
//    int subtitle = typedArray.getInt(R.styleable.GroupItemView_group_subtitle_attr, 0);
//    int color = typedArray.getInt(R.styleable.GroupItemView_group_color_attr, 0);
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.group_item_fragment, this, true);
    ButterKnife.bind(this, view);
//    setTitle(title);
//    setSubtitle(subtitle);
//    setColor(color);
//    typedArray.recycle();
  }

  public void setTitle(@StringRes int title) {
    groupTitleView.setText(title);
  }

  public void setSubtitle(@StringRes int subtitle) {
    groupSubtitleView.setText(subtitle);
  }

  public void setColor(@ColorRes int color) {
    //noinspection deprecation
    groupColorView.setBackgroundColor(getResources().getColor(color));
  }
}
