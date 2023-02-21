export enum NotificationType {
  ANNOUNCEMENT,
  ERROR, 
  SUCCESS, 
  WARNING
}

export interface Notification {
  message: string,
  time: Date,
  type: NotificationType
}

export type NotificationStore = Notification[];