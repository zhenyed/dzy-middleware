package io.zhenye.demo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChangeListener implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        refresh();
    }

    private void refresh() {
        Config config = ConfigService.getAppConfig();
        String oldProperty = config.getProperty("demo.listener.value", "");
        config.addChangeListener(configChangeEvent -> {
            for (String key : configChangeEvent.changedKeys()) {
                if ("demo.listener.value".equals(key)) {
                    ConfigChange change = configChangeEvent.getChange(key);
                    log.info("Update by ChangeListener.[OldProperty={}, NewProperty={}]", oldProperty, change.getNewValue());
                }
            }
        });
    }
}
