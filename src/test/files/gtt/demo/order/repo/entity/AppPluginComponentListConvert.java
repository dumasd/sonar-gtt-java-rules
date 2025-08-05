package gtt.appbff.appupgrade.repo.entity.convert;

import com.google.gson.reflect.TypeToken;
import gtt.appbff.appupgrade.core.bo.AppPluginComponentBO;
import java.util.List;

public class AppPluginComponentListConvert extends GsonConvert<List<AppPluginComponentBO>> { // Noncompliant {{Entity class must end with 'Entity'}}

    public AppPluginComponentListConvert() {
        super(new TypeToken<List<AppPluginComponentBO>>(){});
    }
}
