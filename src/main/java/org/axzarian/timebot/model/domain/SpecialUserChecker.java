package org.axzarian.timebot.model.domain;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SpecialUserChecker {

    private static final List<String> SPECIAL_IDS   = List.of("1065966054");

    public boolean isSpecial(String id) {
        return SPECIAL_IDS.contains(id);
    }

}
