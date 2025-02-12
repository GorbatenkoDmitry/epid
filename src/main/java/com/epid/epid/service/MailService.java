package com.epid.epid.service;

import com.epid.epid.domain.MailType;
import com.epid.epid.domain.user.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(
            User user,
            MailType type,
            Properties params
    );

}