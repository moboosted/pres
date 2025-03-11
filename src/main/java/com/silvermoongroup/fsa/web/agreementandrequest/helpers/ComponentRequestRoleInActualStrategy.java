package com.silvermoongroup.fsa.web.agreementandrequest.helpers;

import org.springframework.stereotype.Component;

@Component("ComponentRequestStrategy")
public class ComponentRequestRoleInActualStrategy extends RequestRoleInActualStrategy {

    @Override
    public String getCallBackPath(String context) {
        return "/secure/crequest.do";
    }
}
