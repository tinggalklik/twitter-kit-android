/*
 * Copyright (C) 2015 Twitter, Inc.
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
 *
 */

package com.twitter.sdk.android.tweetui.internal;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import io.fabric.sdk.android.FabricAndroidTestCase;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class TweetImageViewTest extends FabricAndroidTestCase {
    public final int[] TEST_STATE = new int[]{0, 0};
    public final int TEST_HEIGHT = 2;
    public final int TEST_WIDTH = 4;

    public void testOnDraw() {
        final TweetImageView tweetImageView = new TweetImageView(getContext());
        final TweetImageView.Overlay overlay = mock(TweetImageView.Overlay.class);
        final Canvas canvas = new Canvas();
        tweetImageView.overlay = overlay;
        tweetImageView.draw(canvas);

        verify(overlay).draw(canvas);
    }

    public void testDrawableStateChanged() {
        final TweetImageView tweetImageView = new TweetImageView(getContext());
        final TweetImageView.Overlay overlay = mock(TweetImageView.Overlay.class);
        tweetImageView.overlay = overlay;
        tweetImageView.drawableStateChanged();

        verify(overlay).setDrawableState(any(int[].class));
    }

    public void testOnMeasure() {
        final TweetImageView tweetImageView = new TweetImageView(getContext());
        final TweetImageView.Overlay overlay = mock(TweetImageView.Overlay.class);
        tweetImageView.overlay = overlay;
        tweetImageView.measure(0, 0);

        verify(overlay).setDrawableBounds(anyInt(), anyInt());
    }

    public void testOnSizeChanged() {
        final TweetImageView tweetImageView = new TweetImageView(getContext());
        final TweetImageView.Overlay overlay = mock(TweetImageView.Overlay.class);
        tweetImageView.overlay = overlay;
        tweetImageView.onSizeChanged(TEST_WIDTH, TEST_HEIGHT, 0, 0);

        verify(overlay).setDrawableBounds(TEST_WIDTH, TEST_HEIGHT);
    }

    public void testSetOverlayDrawable() {
        final TweetImageView tweetImageView = new TweetImageView(getContext());
        final TweetImageView.Overlay overlay = mock(TweetImageView.Overlay.class);
        tweetImageView.overlay = overlay;
        final Drawable drawable = mock(Drawable.class);
        tweetImageView.setOverlayDrawable(drawable);

        verify(overlay).cleanupDrawable(tweetImageView);
        assertNotNull(tweetImageView.overlay);
        assertEquals(drawable, tweetImageView.overlay.drawable);
    }

    public void testSetOverlayDrawable_nullDrawable() {
        final TweetImageView tweetImageView = new TweetImageView(getContext());
        final TweetImageView.Overlay overlay = mock(TweetImageView.Overlay.class);
        tweetImageView.overlay = overlay;
        tweetImageView.setOverlayDrawable(null);

        verify(overlay).cleanupDrawable(tweetImageView);
        assertNotNull(tweetImageView.overlay);
        assertNull(tweetImageView.overlay.drawable);
    }

    public void testOverlayDraw() {
        final Drawable drawable = mock(Drawable.class);
        final TweetImageView.Overlay overlay = new TweetImageView.Overlay(drawable);
        final Canvas canvas = new Canvas();
        overlay.draw(canvas);

        verify(drawable).draw(canvas);
    }

    public void testOverlaySetDrawableState() {
        final Drawable drawable = mock(Drawable.class);
        when(drawable.isStateful()).thenReturn(true);
        final TweetImageView.Overlay overlay = new TweetImageView.Overlay(drawable);
        overlay.setDrawableState(TEST_STATE);

        verify(drawable).isStateful();
        verify(drawable).setState(TEST_STATE);
    }

    public void testOverlaySetDrawableState_drawableNotStateful() {
        final Drawable drawable = mock(Drawable.class);
        when(drawable.isStateful()).thenReturn(false);
        final TweetImageView.Overlay overlay = new TweetImageView.Overlay(drawable);
        overlay.setDrawableState(TEST_STATE);

        verify(drawable).isStateful();
        verifyNoMoreInteractions(drawable);
    }

    public void testOverlaySetDrawableBounds() {
        final Drawable drawable = mock(Drawable.class);
        final TweetImageView.Overlay overlay = new TweetImageView.Overlay(drawable);
        overlay.setDrawableBounds(TEST_WIDTH, TEST_HEIGHT);

        verify(drawable).setBounds(0, 0, TEST_WIDTH, TEST_HEIGHT);
    }

    public void testCleanupDrawable() {
        final ImageView imageView = mock(ImageView.class);
        final Drawable drawable = mock(Drawable.class);
        final TweetImageView.Overlay overlay = new TweetImageView.Overlay(drawable);
        overlay.cleanupDrawable(imageView);

        verify(imageView).unscheduleDrawable(drawable);
    }
}
