package space.libs;

@SuppressWarnings("unused")
public class CompatEventHandler {

    public static CompatEventHandler INSTANCE;

    public CompatEventHandler() {
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }
}
