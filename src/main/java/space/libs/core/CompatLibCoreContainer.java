package space.libs.core;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;

import static space.libs.util.ForgeUtils.*;

@SuppressWarnings("all")
public class CompatLibCoreContainer extends DummyModContainer {

    public CompatLibCoreContainer() {
        super(createModData("compatlibcore", "CompatLibCore", "1.7.10", "HowardZHY"));
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        return true;
    }
}
