package simple.blog.backend.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import simple.blog.backend.model.Role;
import simple.blog.backend.model.User;
import simple.blog.backend.service.SequenceGeneratorService;

@Component
public class UserModelListener extends AbstractMongoEventListener<User> {

    private final SequenceGeneratorService sequenceGeneratorService;

    public UserModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        if (event.getSource().getUserId() == null) {
            event.getSource().setUserId((int)sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
            
    }
    }
}
