/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.agreementandrequest.helpers;

import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.agreementandrequest.constants.ViewActionEnum;
import com.silvermoongroup.fsa.web.agreementandrequest.form.AbstractViewForm;
import com.silvermoongroup.fsa.web.dto.ActionHelper;
import com.silvermoongroup.fsa.web.dto.RoleActionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Stack;

/**
 * @author mubeen
 */
public class ViewActionHelper<T extends AbstractViewForm<?>> {

    private Stack<ActionHelper> stack;
    
    public void pushActionHelperDTO(T viewForm, HttpServletRequest request, String path) {
        ActionHelper actionHelperDTO = new ActionHelper();
        actionHelperDTO.setContextObjectReference(viewForm.getContextObjectReference());
        actionHelperDTO.setKindDTO(KindDTO.convertFromString(viewForm.getKind()));

        if (path != null) {
            actionHelperDTO.setPath(path);
        } else {
            actionHelperDTO.setPath(viewForm.getPath());
        }

        pushOnStack(request, actionHelperDTO);
    }

    public void pushRoleActionHelperDTO(ActionHelper actionHelper, ObjectReference rolePlayerObjectReference,
            HttpServletRequest request, String contextPath) {
        RoleActionHelper roleActionHelperDTO = new RoleActionHelper();
        roleActionHelperDTO.setContextObjectReference(actionHelper.getContextObjectReference());
        roleActionHelperDTO.setKindDTO(actionHelper.getKindDTO());
        roleActionHelperDTO.setRolePlayerObjectReference(rolePlayerObjectReference);
        roleActionHelperDTO.setPath(actionHelper.getPath());
        roleActionHelperDTO.setContextPath(contextPath);

        pushOnStack(request, roleActionHelperDTO);
    }

    public ActionHelper popActionHelperDTO(HttpServletRequest request) {
        HttpSession session = request.getSession();
        stack = (Stack<ActionHelper>) session.getAttribute(ViewActionEnum.ACTION_HELPER_DTO.getName());
        ActionHelper actionHelperDTO = stack.pop();
        
        if (stack.isEmpty()) {
            cleanActionHelperDTO(request);
        }

        return actionHelperDTO;
    }

    public ActionHelper peekAtActionHelperDTO(HttpServletRequest request) {
        HttpSession session = request.getSession();
        stack = (Stack<ActionHelper>) session.getAttribute(ViewActionEnum.ACTION_HELPER_DTO.getName());
        return stack.peek();
    }

    public static void cleanActionHelperDTO(HttpServletRequest request) {
        request.getSession().removeAttribute(ViewActionEnum.ACTION_HELPER_DTO.getName());
    }
    
    public static Boolean isEmpty(HttpServletRequest request) {
        return request.getSession().getAttribute(ViewActionEnum.ACTION_HELPER_DTO.getName()) == null;
    }
    
    private void pushOnStack(HttpServletRequest request, ActionHelper actionHelperDTO) {
        if (isEmpty(request)) {
            stack = new Stack<ActionHelper>();
        } else {
            stack =   (Stack<ActionHelper>) request.getSession().getAttribute(ViewActionEnum.ACTION_HELPER_DTO.getName());
        }
        
        stack.push(actionHelperDTO);
        request.getSession().setAttribute(ViewActionEnum.ACTION_HELPER_DTO.getName(), stack);
    }
}
