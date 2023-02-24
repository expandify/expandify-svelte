import { NotificationType, type Notification, type NotificationStore } from "$lib/types/notification";
import { writable } from "svelte/store";

const TIMEOUT_MS = 3 * 1000;

function createStore() {
  const data: NotificationStore = [];
  const { subscribe, set, update } = writable(data);

  const addNotification = (message: string, type: NotificationType) => {
    const notification: Notification = { message: message, type: type, time: new Date(Date.now()) };

    setTimeout(() => {update(n => {n.shift(); return n})}, TIMEOUT_MS);

    update(n => { n.push(notification); return n; });
  }

	return {
		subscribe,    
        addAnnouncement: (message: string) => { addNotification(message, NotificationType.ANNOUNCEMENT) },
        addError: (message: string) => { addNotification(message, NotificationType.ERROR) },
        addSuccess: (message: string) => { addNotification(message, NotificationType.SUCCESS) },
        addWarning: (message: string) => { addNotification(message, NotificationType.WARNING) }
	};
}

export const notifications = createStore();