/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.design.widget;

import static org.junit.Assert.assertEquals;

import android.support.design.testapp.R;
import android.support.design.testapp.SnackbarWithFabActivity;
import android.support.design.testutils.SnackbarUtils;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SnackbarWithFabTest {
  @Rule
  public final ActivityTestRule<SnackbarWithFabActivity> activityTestRule =
      new ActivityTestRule<>(SnackbarWithFabActivity.class);

  private static final String MESSAGE_TEXT = "Test Message";

  private CoordinatorLayout mCoordinatorLayout;

  @Before
  public void setup() {
    mCoordinatorLayout = activityTestRule.getActivity().findViewById(R.id.col);
  }

  @Test
  public void testShortSnackbarDodgesFab() {
    final int[] originalFabPosition = new int[2];
    final View fab = mCoordinatorLayout.findViewById(R.id.fab);
    fab.getLocationOnScreen(originalFabPosition);

    // Show a simple Snackbar and wait for it to be shown
    final Snackbar snackbar =
        Snackbar.make(mCoordinatorLayout, MESSAGE_TEXT, Snackbar.LENGTH_SHORT);
    SnackbarUtils.showTransientBottomBarAndWaitUntilFullyShown(snackbar);

    // Now check that the FAB has shifted up to make space for the Snackbar
    final int[] fabPosition = new int[2];
    fab.getLocationOnScreen(fabPosition);
    assertEquals(originalFabPosition[0], fabPosition[0]);
    assertEquals(originalFabPosition[1] - snackbar.getView().getHeight(), fabPosition[1]);

    // Now wait until the Snackbar has been dismissed
    SnackbarUtils.waitUntilFullyDismissed(snackbar);

    // And check that the FAB is back in its original position
    fab.getLocationOnScreen(fabPosition);
    assertEquals(originalFabPosition[0], fabPosition[0]);
    assertEquals(originalFabPosition[1], fabPosition[1]);
  }

  @Test
  public void testIndefiniteSnackbarDodgesFab() throws Throwable {
    final int[] originalFabPosition = new int[2];
    final View fab = mCoordinatorLayout.findViewById(R.id.fab);
    fab.getLocationOnScreen(originalFabPosition);

    // Show a simple Snackbar and wait for it to be shown
    final Snackbar snackbar =
        Snackbar.make(mCoordinatorLayout, MESSAGE_TEXT, Snackbar.LENGTH_INDEFINITE);
    SnackbarUtils.showTransientBottomBarAndWaitUntilFullyShown(snackbar);

    // Now check that the FAB has shifted up to make space for the Snackbar
    final int[] fabPosition = new int[2];
    fab.getLocationOnScreen(fabPosition);
    assertEquals(originalFabPosition[0], fabPosition[0]);
    assertEquals(originalFabPosition[1] - snackbar.getView().getHeight(), fabPosition[1]);

    // Now dismiss the Snackbar and wait for it to be dismissed
    SnackbarUtils.dismissTransientBottomBarAndWaitUntilFullyDismissed(snackbar);

    // And check that the FAB is back in its original position
    fab.getLocationOnScreen(fabPosition);
    assertEquals(originalFabPosition[0], fabPosition[0]);
    assertEquals(originalFabPosition[1], fabPosition[1]);
  }
}
