package com.xmartlabs.doapp.ui;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
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
    createOnboardingCards("", "Create groups for different purpouses", R.drawable.tutorial_1);
    createOnboardingCards("", "Add & Manage Tasks", R.drawable.tutorial_2);
    createOnboardingCards("", "View how you are doing!", R.drawable.tutorial_3);
    createOnboardingCards("", "Go to dashboard", R.drawable.tutorial_4);
    setImageBackground(R.drawable.bg_walkthrough);
    showNavigationControls(false);
    setOnboardPages(pages);
    setFinishButtonTitle("Let's Do it!");
    cardViewPager.setPadding(75, 276, 75, 281);
  }

  @Override
  public void onFinishButtonPressed() {
    //TODO go to dashboard
  }

  private void createOnboardingCards(String title, String description, @DrawableRes int iconId) {
    AhoyOnboarderCard ahoyOnboarderCard = new AhoyOnboarderCard(title, description, iconId);
    ahoyOnboarderCard.setBackgroundColor(R.color.white);
    ahoyOnboarderCard.setDescriptionColor(R.color.dark);
    pages.add(ahoyOnboarderCard);
  }
}
