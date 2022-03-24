package io.zhenye.demo;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChangeListenerAnnotation {

    private String value = "test";

    @ApolloConfigChangeListener(interestedKeyPrefixes = "demo.listenerAnnotation.")
    private void onChange(ConfigChangeEvent changeEvent) {
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            if ("demo.listenerAnnotation.value1".equals(change.getPropertyName())) {
                log.info("Update by ChangeListenerAnnotation.[OldProperty={}, NewProperty={}]", value, change.getNewValue());
                value = change.getNewValue();
            } else if ("demo.listenerAnnotation.value2".equals(change.getPropertyName())) {
                // do something ...
            }
        }
    }

}
