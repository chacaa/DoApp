package com.xmartlabs.doapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.doapp.DoAppApplication;
import com.xmartlabs.doapp.helper.ui.MetricsHelper;
import com.xmartlabs.template.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by scasas on 3/13/17.
 */
@HensonNavigable
public class OnBoardingActivity extends AhoyOnboarderActivity {
  private List<AhoyOnboarderCard> pages = new ArrayList<>();
//  @BindView(R.id.vp_pager)
//  ViewPager cardViewPager;

  private ViewPager cardViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    ButterKnife.bind(this);
    cardViewPager = (ViewPager) findViewById(R.id.vp_pager);
    createOnboardingCards("", "Create groups for different purpouses", R.drawable.tutorial_1);
    createOnboardingCards("", "Add & Manage Tasks", R.drawable.tutorial_2);
    createOnboardingCards("", "View how you are doing!", R.drawable.tutorial_3);
    createOnboardingCards("", "Go to dashboard", R.drawable.tutorial_4);
    setImageBackground(R.drawable.bg_walkthrough);
    showNavigationControls(false);
    setOnboardPages(pages);
    cardViewPager.setPadding(100, 376, 100, 391);
  }

  @Override
  public void onFinishButtonPressed() {
    //TODO go to dashboard
  }

  private void createOnboardingCards(String title, String description, @DrawableRes int iconId) {
    AhoyOnboarderCard ahoyOnboarderCard = new AhoyOnboarderCard(title, description, iconId);
    ahoyOnboarderCard.setBackgroundColor(R.color.white);
    ahoyOnboarderCard.setDescriptionColor(R.color.dark);
    //ahoyOnboarderCard.setDescriptionTextSize(MetricsHelper.spToPx(15));
    ahoyOnboarderCard.setIconLayoutParams(MetricsHelper.dpToPxInt(130), MetricsHelper.dpToPxInt(130), MetricsHelper.dpToPxInt(60), MetricsHelper.dpToPxInt(85), MetricsHelper.dpToPxInt(85), MetricsHelper.dpToPxInt(110));
    pages.add(ahoyOnboarderCard);
  }
}
