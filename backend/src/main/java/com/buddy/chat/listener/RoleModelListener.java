package com.buddy.chat.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.buddy.chat.model.Role;
import com.buddy.chat.model.User;
import com.buddy.chat.service.SequenceGeneratorService;

@Component
public class RoleModelListener extends AbstractMongoEventListener<Role> {

    private final SequenceGeneratorService sequenceGeneratorService;

    public RoleModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Role> event) {
        if (event.getSource().getRoleId() == null) {
            event.getSource().setRoleId((int)sequenceGeneratorService.generateSequence(Role.SEQUENCE_NAME));
            
    }
    }
}
