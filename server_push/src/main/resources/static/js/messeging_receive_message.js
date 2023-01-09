// import { getMessaging, onMessage } from "@firebase/messaging";
import { getMessaging, onMessage } from "https://www.gstatic.com/firebasejs/9.15.0/firebase-messaging.js"

const messaging = getMessaging();
onMessage(messaging, (payload) => {
    console.log('Message received. ', payload);
    // ...
});