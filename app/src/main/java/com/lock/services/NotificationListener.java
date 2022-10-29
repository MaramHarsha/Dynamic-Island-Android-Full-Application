package com.lock.services;

import com.lock.entity.Notification;

public interface NotificationListener {
    void onItemClicked(Notification notification);

    void onItemClicked(Notification notification, int i);
}
