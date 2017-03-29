package com.xmartlabs.scasas.doapp.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewPager;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.scasas.doapp.R;
import com.xmartlabs.scasas.doapp.model.User;
import com.xmartlabs.scasas.doapp.ui.Henson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scasas on 3/13/17.
 */
public class OnBoardingActivity extends AhoyOnboarderActivity {
  public static final int BOTTOM = 91;
  public static final int LEFT = 75;
  public static final int RIGHT = 75;
  public static final int TOP = 86;

  @InjectExtra
  User user;

  private ViewPager cardViewPager;
  private List<AhoyOnboarderCard> pages = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Dart.inject(this);
    cardViewPager = (ViewPager) findViewById(R.id.vp_pager);
    createAllCards();
    setupAhoyOnboarding();
  }

  private void setupAhoyOnboarding() {
    setImageBackground(R.drawable.bg_walkthrough);
    showNavigationControls(false);
    setOnboardPages(pages);
    setFinishButtonTitle(R.string.lets_do_it);
    cardViewPager.setPadding(LEFT, TOP, RIGHT, BOTTOM);
  }

  private void createAllCards() {
    createOnboardingCard(R.string.create_groups, R.drawable.tutorial_1);
    createOnboardingCard(R.string.add_manage_tasks, R.drawable.tutorial_2);
    createOnboardingCard(R.string.how_you_are_doing, R.drawable.tutorial_3);
    createOnboardingCard(R.string.to_dashboard, R.drawable.tutorial_4);
  }

  @Override
  public void onFinishButtonPressed() {
    Intent intent = Henson.with(getApplicationContext())
        .gotoGroupsListActivity()
        .user(user)
        .build()
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }

  private void createOnboardingCard(@StringRes int description, @DrawableRes int iconId) {
    AhoyOnboarderCard ahoyOnboarderCard = new AhoyOnboarderCard("", getString(description), iconId);
    ahoyOnboarderCard.setBackgroundColor(R.color.white);
    ahoyOnboarderCard.setDescriptionColor(R.color.dark_grey);
    pages.add(ahoyOnboarderCard);
  }
}
