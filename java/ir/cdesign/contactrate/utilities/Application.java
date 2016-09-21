package ir.cdesign.contactrate.utilities;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.caligraphy.CalligraphyConfig;

/**
 * Created by amin pc on 21/09/2016.
 */
public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("seoge.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
