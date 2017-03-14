package com.xmartlabs.doapp.ui;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewPager;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.template.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scasas on 3/13/17.
 */
@HensonNavigable
public class OnBoardingActivity extends AhoyOnboarderActivity {
  private List<AhoyOnboarderCard> pages = new ArrayList<>();
  private ViewPager cardViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    cardViewPager = (ViewPager) findViewById(R.id.vp_pager);
    createOnboardingCards(R.string.create_groups, R.drawable.tutorial_1);
    createOnboardingCards(R.string.add_manage_tasks, R.drawable.tutorial_2);
    createOnboardingCards(R.string.how_you_are_doing, R.drawable.tutorial_3);
    createOnboardingCards(R.string.to_dashboard, R.drawable.tutorial_4);
    setImageBackground(R.drawable.bg_walkthrough);
    showNavigationControls(false);
    setOnboardPages(pages);
    setFinishButtonTitle(R.string.lets_do_it);
    cardViewPager.setPadding(75, 276, 75, 281);
  }

  @Override
  public void onFinishButtonPressed() {
    //TODO go to dashboard
  }

  private void createOnboardingCards(@StringRes int description, @DrawableRes int iconId) {
    AhoyOnboarderCard ahoyOnboarderCard = new AhoyOnboarderCard("", getString(description), iconId);
    ahoyOnboarderCard.setBackgroundColor(R.color.white);
    ahoyOnboarderCard.setDescriptionColor(R.color.color_dark);
    pages.add(ahoyOnboarderCard);
  }
}
