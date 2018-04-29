package service.impl;

import dao.NotificationDao;
import dao.impl.NotificationDaoImpl;
import model.Notification;
import service.NotificationService;

public class NotificaServiceImpl implements NotificationService {

    private NotificationDao notificationDao = new NotificationDaoImpl();

    @Override
    public Notification[] getRead(String username) {
        return notificationDao.getByUsername(username, true);
    }

    @Override
    public Notification[] getUnread(String username) {
        return notificationDao.getByUsername(username, false);
    }

    @Override
    public void read(int ID) {
        notificationDao.read(ID);
    }

    @Override
    public boolean add(String username, String header, String message) {
        return notificationDao.notify(username, header, message);
    }

    @Override
    public void delete(int ID) {
        notificationDao.delete(ID);
    }

    @Override
    public void clearRead(String username) {
        notificationDao.clearRead(username);
    }
}
