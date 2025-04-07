package space.libs.interfaces;

import java.util.Map;

@SuppressWarnings("unused")
public interface INetworkDispatcher {

    void setModList(Map<String, String> modList);

    Map<String, String> getModList();

}
