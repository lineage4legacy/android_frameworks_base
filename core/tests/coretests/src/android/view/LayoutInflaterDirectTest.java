/*
 * Copyright (C) 2026 The Android Open Source Project
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

package android.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class LayoutInflaterDirectTest {
    private final Context mContext = new ContextThemeWrapper(
            InstrumentationRegistry.getInstrumentation().getTargetContext(),
            android.R.style.Theme_Material);

    @Test
    public void frameworkViewUsesSuppliedContext() throws Exception {
        final View view = LayoutInflater.from(mContext).createView(
                mContext, "FrameLayout", "android.widget.", null);
        assertEquals(FrameLayout.class, view.getClass());
        assertSame(mContext, view.getContext());
    }

    @Test
    public void filterStillRejectsFrameworkView() {
        final LayoutInflater inflater = LayoutInflater.from(mContext).cloneInContext(mContext);
        inflater.setFilter(clazz -> false);
        assertThrows(InflateException.class,
                () -> inflater.createView(mContext, "FrameLayout", "android.widget.", null));
    }

    @Test
    public void customViewUsesReflectionFallback() throws Exception {
        final View view = LayoutInflater.from(mContext).createView(
                mContext, CustomView.class.getName(), null, null);
        assertEquals(CustomView.class, view.getClass());
        assertSame(mContext, view.getContext());
    }

    @Test
    public void viewStubRetainsThemedInflater() throws Exception {
        final ViewStub stub = (ViewStub) LayoutInflater.from(mContext).createView(
                mContext, "ViewStub", "android.view.", null);
        assertNotNull(stub.getLayoutInflater());
        assertSame(mContext, stub.getLayoutInflater().getContext());
    }

    public static class CustomView extends View {
        public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }
}
