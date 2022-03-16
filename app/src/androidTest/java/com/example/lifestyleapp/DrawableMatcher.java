package com.example.lifestyleapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher class to check for images during testing
 * Credit: Daniele Bottillo
 * https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f
 */
public class DrawableMatcher extends TypeSafeMatcher {

    private final int expectedImageId;
    private String resourceName;
    static final int EMPTY = -1;
    static final int ANY = -2;


    public DrawableMatcher(int expectedImageId){
        super(View.class);
        this.expectedImageId = expectedImageId;
    }
    @Override
    protected boolean matchesSafely(Object item) {
        if(!(item instanceof ImageView))
            return false;
        ImageView image = (ImageView) item;
        if(expectedImageId == EMPTY){
            return image.getDrawable() == null;
        }
        if(expectedImageId == ANY){
            return image.getDrawable() != null;
        }

        Resources resources = ((ImageView) item).getContext().getResources();
        Drawable expectedDrawable = resources.getDrawable(expectedImageId);
        resourceName = resources.getResourceEntryName(expectedImageId);

        if(expectedDrawable == null){
            return false;
        }
        Bitmap bitmap = getBitmap(image.getDrawable());
        Bitmap expectedBitmap = getBitmap(expectedDrawable);
        return bitmap.sameAs(expectedBitmap);

     }

     private Bitmap getBitmap(Drawable drawable){
         Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
         Canvas canvas = new Canvas(bitmap);
         drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
         drawable.draw(canvas);
         return bitmap;
     }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(expectedImageId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }
}
